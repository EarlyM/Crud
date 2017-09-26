package ua.crud.dao;

import ua.crud.model.User;

import java.util.List;

public interface UserDAO {
    User findUserById(Long id);
    List<User> findUserByFio(String fio);
    void deleteUser(Long id);
    void updateUser(User user);
    void createUser(User user);
    List<User> findAll();
}
