package ru.mai.information_system.service;

import ru.mai.information_system.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public void saveUser(User user);

    public void deleteUserById(int id);
}
