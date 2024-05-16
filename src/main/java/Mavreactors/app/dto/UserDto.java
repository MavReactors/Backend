package Mavreactors.app.dto;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private String userName;
    private String profilePhoto;
    private List<UserRole> userRoles;
}
