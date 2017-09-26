package ua.crud.service;

import ua.crud.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getUserByFio(String fio);
    void deleteUser(Long id);
    void updateUser(User user);
    void createUser(User user);
    List<User> getAll();
}
