package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/admin/delete")
    public String deleteUserPost(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/index";
    }

    @PostMapping("/admin/update")
    public String updateUser(
            @ModelAttribute("user") @Valid User user,
            BindingResult result,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAllRoles());
            model.addAttribute("selectedRoleIds", roleIds != null ? roleIds : List.of());
            return "index";
        }

        userService.updateUser(user, roleIds);
        return "redirect:/index";
    }

    @PostMapping("/admin/add")
    public String addUser(
            @ModelAttribute("user") @Valid User user,
            BindingResult result,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAllRoles());
            return "index";
        }

        userService.addUser(user, roleIds);
        return "redirect:/index";
    }
}
