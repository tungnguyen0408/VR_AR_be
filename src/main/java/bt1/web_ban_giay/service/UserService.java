package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.UserDTO;
import bt1.web_ban_giay.dto.UserProfileDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    void updateLastLogin(Long id);

    void changePassword(Long id, String oldPassword, String newPassword);

    void updateUserRole(Long id, String role);

    List<UserProfileDTO> getAllUserProfiles();
}
