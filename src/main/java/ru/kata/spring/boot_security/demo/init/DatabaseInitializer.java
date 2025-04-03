package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class DatabaseInitializer {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseInitializer(UserService userService,
                               RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @PostConstruct
    public void init() {
        createRolesIfNotExists();
        createAdminIfNotExists();
        createUserIfNotExists();
    }

    private void createRolesIfNotExists() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }

    private void createAdminIfNotExists() {
        if (userService.getName("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setName("Admin");
            admin.setSecondname("Adminov");
            admin.setAge(30);

            List<Long> adminRoleIds = List.of(
                    roleRepository.findByName("ROLE_ADMIN").getId(),
                    roleRepository.findByName("ROLE_USER").getId()
            );

            userService.addUser(admin, adminRoleIds);
        }
    }

    private void createUserIfNotExists() {
        if (userService.getName("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user");
            user.setName("User");
            user.setSecondname("Userov");
            user.setAge(25);

            List<Long> userRoleIds = Collections.singletonList(
                    roleRepository.findByName("ROLE_USER").getId()
            );

            userService.addUser(user, userRoleIds);
        }
    }
}