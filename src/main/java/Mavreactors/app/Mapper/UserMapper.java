package Mavreactors.app.Mapper;

import Mavreactors.app.Model.User;
import Mavreactors.app.dto.UserDto;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getUserName(),
                userDto.getProfilePhoto(),
                userDto.getUserRoles(),
                false
        );
    }
}
