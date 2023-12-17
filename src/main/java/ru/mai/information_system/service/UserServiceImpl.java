package ru.mai.information_system.service;

import ru.mai.information_system.dao.UserDAO;
import ru.mai.information_system.dao.UserDAOImpl;
import ru.mai.information_system.entity.User;

import javax.transaction.Transactional;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
//    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
//    @Transactional
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
//    @Transactional
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
//    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    @Override
//    @Transactional
    public void deleteUserById(int id) {
        userDAO.deleteUserById(id);
    }
}
