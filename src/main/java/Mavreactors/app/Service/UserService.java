package Mavreactors.app.Service;

import Mavreactors.app.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    boolean existsByEmail(String email);
}
