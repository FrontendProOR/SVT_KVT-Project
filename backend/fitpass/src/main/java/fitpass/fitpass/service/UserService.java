package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.UserDTO;
import fitpass.fitpass.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmail(String email);
    User updateUser(UserDTO userDTO, String email);

    Optional<User> findById(Long id);
    User createUser(UserDTO userDTO);

    List<User> findAll();
    boolean changePassword(String email, String oldPassword, String newPassword);
}
