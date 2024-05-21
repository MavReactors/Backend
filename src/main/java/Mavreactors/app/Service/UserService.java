package Mavreactors.app.Service;

import Mavreactors.app.Model.User;
import Mavreactors.app.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User createUser(UserDto userDto);
    boolean existsByEmail(String email);
    ResponseEntity<?> confirmEmail(String confirmationToken);
    User getUserById(String email);
    User updateUser(String email, UserDto updateUser);
    void deleteUser(String email);
}
