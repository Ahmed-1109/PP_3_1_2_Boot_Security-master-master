package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
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

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "addUser";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam("roles") Set<Role> checked, Model model) {
        model.addAttribute("roles", roleService.getRoles());

        if (userService.findByUserName(user.getUsername()).isPresent()) {
            bindingResult.rejectValue("email", "", "Пользователь с таким логином уже существует");
        }
        if (bindingResult.hasErrors()) {
            return "addUser";

        }
        Set<Role> set = checked.stream()
                .map(Role::getName)
                .flatMap(name -> roleService.getRoleByName(name).stream())
                .collect(Collectors.toSet());
        user.setRoles(set);
        userService.addUser(user);
        return "redirect:/admin/user";
    }


    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model) {
        Optional<User> optUser = userService.getUserById(id);
        optUser.ifPresent(user -> model.addAttribute("editUser", user));

        model.addAttribute("roles", roleService.getRoles());
        return "editUser";
    }


    @PostMapping("/edit")
    public String update(@ModelAttribute("editUser") @Valid User user, BindingResult bindingResult,
                         @RequestParam("roles") Set<Role> checked, Model model) {

        model.addAttribute("roles", roleService.getRoles());
        Optional<User> optUser = userService.getUserById(user.getId());

        if (optUser.isPresent() && (!user.getUsername().equals(optUser.get().getUsername()))) {
            if (userService.findByUserName(user.getUsername()).isPresent()) {
                bindingResult.rejectValue("email", "", "Пользователь с таким логином уже существует");
            }
        }
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        Set<Role> set = checked.stream()
                .map(Role::getName)
                .flatMap(name -> roleService.getRoleByName(name).stream())
                .collect(Collectors.toSet());
        user.setRoles(set);
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin/user";
    }

}
