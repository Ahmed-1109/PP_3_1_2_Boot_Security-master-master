package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("user", us.getUserById(id));
        return "profile";
    }
}
