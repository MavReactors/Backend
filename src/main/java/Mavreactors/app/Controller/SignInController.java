package Mavreactors.app.Controller;

import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/api")
public class SignInController {
    private final UserService userService;

    @Autowired
    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/SignIn")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        // Verificar si ya existe un usuario con el mismo correo
        if (userService.existsByEmail(userDto.getEmail()) || userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Si no existe, crear el nuevo usuario
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
