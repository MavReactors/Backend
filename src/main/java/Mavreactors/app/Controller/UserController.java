package Mavreactors.app.Controller;

import Mavreactors.app.Exceptions.UserNameAlreadyExistsException;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.OutfitDto;
import Mavreactors.app.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
    private final SessionRepository sessionRepository;
    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);

    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@CookieValue("authToken") UUID authToken,
                                        @RequestBody UserDto updatedUser) {
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        try {
            user = userService.updateUser(user.getEmail(), updatedUser);
            return ResponseEntity.ok(user);
        } catch (UserNameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String>  deleteUser(@CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        userService.deleteUser(user.getEmail());
        return ResponseEntity.ok("User deleted successfully! ");
    }

}
