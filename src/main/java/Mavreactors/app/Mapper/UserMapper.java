package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import Mavreactors.app.dto.PrendasDto;
import Mavreactors.app.dto.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto (User user) {
        return new UserDto(
                user.getEmail(),
                user.getPassword(),
                user.getUserRoles()
        );
    }

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getUserRoles()
        );
    }
}
