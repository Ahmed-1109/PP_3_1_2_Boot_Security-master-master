package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/user")
    public String getAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        Optional<User> user = userService.findByUserName(principal.getName());
        model.addAttribute("user", user.get());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "allUsers";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam("roles") Set<Role> checked, Model model) {

        if (userService.checkUsername(bindingResult, user).hasErrors()) {
            return "allUsers";
        }

        Set<String> roleNames = checked.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        user.setRoles(roleService.getRoleByNames(roleNames));
        userService.addUser(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("editUser") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam("roles") Set<Role> checked) {

        if (userService.checkUsername(bindingResult, user).hasErrors()) {
            return "allUsers";
        }

        Set<String> roleNames = checked.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        user.setRoles(roleService.getRoleByNames(roleNames));
        userService.updateUser(user, bindingResult);
        return "redirect:/admin/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin/user";
    }
}
