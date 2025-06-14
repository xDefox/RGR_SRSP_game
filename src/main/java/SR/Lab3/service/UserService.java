package SR.Lab3.service;

import SR.Lab3.entity.User;
import java.util.List;

public interface UserService {
    User createUser(String username, String password, String authority);
    User updateUser(Long id, String username, String password, String authority);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean userExists(String username);
}