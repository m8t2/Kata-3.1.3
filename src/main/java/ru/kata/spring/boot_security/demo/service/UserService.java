package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User findUser(Long id);

    User getName(String username);

    void addUser(User user, List<Long> roleIds);

    void deleteUser(Long id);

    void updateUser(User user, List<Long> roleIds);

    boolean existsUser(Long id);
}
