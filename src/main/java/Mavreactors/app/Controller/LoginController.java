package Mavreactors.app.Controller;


import Mavreactors.app.Model.LoginRequest;
import Mavreactors.app.Model.LoginResponse;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class LoginController {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoginController(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginSubmit(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        System.out.println("user" + user);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else if (!user.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            UUID token = UUID.randomUUID();
            Session session = new Session(token, Instant.now(), user);
            sessionRepository.save(session);
            return new ResponseEntity<>(new LoginResponse(user, token), HttpStatus.OK);
        }
    }
}
