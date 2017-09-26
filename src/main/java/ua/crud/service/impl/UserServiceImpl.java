package ua.crud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.crud.dao.UserDAO;
import ua.crud.model.User;
import ua.crud.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUserById(Long id) {
        return userDAO.findUserById(id);
    }

    @Override
    public List<User> getUserByFio(String fio) {
        return userDAO.findUserByFio(fio);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }
}
