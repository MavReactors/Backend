package Mavreactors.app.Service;

import Mavreactors.app.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    boolean existsByEmail(String email);
    ResponseEntity<?> confirmEmail(String confirmationToken);
}
