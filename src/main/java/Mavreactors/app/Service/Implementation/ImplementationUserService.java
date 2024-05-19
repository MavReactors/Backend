package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Mapper.UserMapper;
import Mavreactors.app.Model.ConfirmationToken;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.ConfirmationTokenRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.EmailService;
import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ImplementationUserService implements UserService {

    @Value("${is.prod}")
    private boolean isProd;

    private final UserRepository userRepository;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailService emailService;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return null;
        }

        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(UUID.randomUUID().toString(), user.getEmail());
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"https://whizbackend.azurewebsites.net/api/confirm-account?token="+confirmationToken.getConfirmationToken());
        //mailMessage.setText("To confirm your account, please click here : "
        //        +"http://localhost:8080/api/confirm-account?token="+confirmationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmail(token.getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
