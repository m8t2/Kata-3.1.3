package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;


@Controller
public class MainController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public MainController(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String test(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userDetails);
        User user = userService.getName(userDetails.getUsername());
        model.addAttribute("userroles", user.getRolesWithoutPrefix());
        model.addAttribute("userrolesAsString", user.getRolesAsString());
        List<User> users = userService.findAllUsers();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("secondname", user.getSecondname());
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("id", user.getId());
        model.addAttribute("age", user.getAge());
        model.addAttribute("users", users);
        return "index";
    }

}
