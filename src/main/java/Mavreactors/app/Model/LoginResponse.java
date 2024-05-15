package Mavreactors.app.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    User user;
    UUID token;
}
