package Mavreactors.app.Controller;

import Mavreactors.app.Exceptions.EmailAlreadyExistsException;
import Mavreactors.app.Exceptions.UserNameAlreadyExistsException;
import Mavreactors.app.Model.User;
import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class SignInController {
    private final UserService userService;

    @Autowired
    public SignInController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/SignIn")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        // Verificar si la contraseña no está vacía
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            return new ResponseEntity<>("Password cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            // Intentar crear el nuevo usuario
            User createdUser = userService.createUser(userDto);
            return ResponseEntity.ok(createdUser);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (UserNameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /*
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }*/

}
