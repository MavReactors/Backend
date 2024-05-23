package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Exceptions.EmailAlreadyExistsException;
import Mavreactors.app.Exceptions.UserNameAlreadyExistsException;
import Mavreactors.app.Mapper.UserMapper;
import Mavreactors.app.Model.*;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Repository.*;
import Mavreactors.app.Service.EmailService;
import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;
import java.util.UUID;

import static Mavreactors.app.Model.UserRole.CUSTOMER;

@Slf4j
@Service
@AllArgsConstructor
public class ImplementationUserService implements UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final ClothingRepository clothingRepository;
    private final OutfitRepository outfitRepository;
    private final VoteRepository voteRepository;

    @Override
    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }
        if (userRepository.existsByUserName(userDto.getUserName())) {
            throw new UserNameAlreadyExistsException("Username already in use");
        }
        // Encriptar la contraseña
        String encryptedPassword = encryptPassword(userDto.getPassword());
        userDto.setPassword(encryptedPassword);

        List<UserRole> roles = new ArrayList<>();
        roles.add(CUSTOMER);
        userDto.setUserRoles(roles);

        userDto.setProfilePhoto("");
        User user = UserMapper.mapToUser(userDto);

        return userRepository.save(user);
    }

    /*
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
    }*/

    @Override
    public User getUserById(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User is not exists with given id: " + email));

        return user;
    }

    @Override
    public User updateUser(String email, UserDto updateUser) {
        User user = userRepository.findById(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User is not exists with given id: " + email));
        if (!user.getUserName().equals(updateUser.getUserName()) && userRepository.existsByUserName(updateUser.getUserName())) {
            throw new UserNameAlreadyExistsException("Username already in use");
        }
        if (updateUser.getPassword() != null){
            user.setPassword(encryptPassword(updateUser.getPassword()));
        }
        if (updateUser.getUserName() != null){
            user.setUserName(updateUser.getUserName());
        }
        if (updateUser.getProfilePhoto() != null){
            user.setProfilePhoto(updateUser.getProfilePhoto());
        }
        return  userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findById(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User is not exists with given id: " + email));
        // Eliminar todas las sesiones relacionadas
        List<Session> sessions = sessionRepository.findByUser(user);
        sessionRepository.deleteAll(sessions);

        // Eliminar todos los votos relacionados
        List<Vote> votes = voteRepository.findByUser(user);
        voteRepository.deleteAll(votes);

        // Eliminar todos los outfits relacionados
        List<Outfit> outfits = outfitRepository.findByUser(user);
        outfitRepository.deleteAll(outfits);

        // Eliminar todas las prendas relacionadas
        List<Clothing> clothingList = clothingRepository.findByUser(user);
        clothingRepository.deleteAll(clothingList);
        userRepository.deleteById(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}
