package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @GetMapping()
    public String showAllUsers(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("newuser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("activePage", "allUsers");
        return "allUsers";
    }

    @PostMapping(value = "/createUser")
    public String createUser( @Valid @ModelAttribute("newuser") User user, @RequestParam ArrayList<String> roles) {

        Set<Role> userRole = new HashSet<>();
        for (String roleId : roles) {
            Role role = roleService.getById(Long.parseLong(roleId));
            userRole.add(role);
        }
        user.setRoles(userRole);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id, Model model, @ModelAttribute("user") User user) {
        User user_fromDB = userService.getUser(id);
        model.addAttribute("user", user_fromDB);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}