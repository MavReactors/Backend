package Mavreactors.app;

import Mavreactors.app.Model.User;
import Mavreactors.app.Model.UserRole;
import Mavreactors.app.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.boot.CommandLineRunner;

@Slf4j
@SpringBootApplication
public class ProyectoCvdsApplication {

	private final UserRepository userRepository;

	@Autowired
    public ProyectoCvdsApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) throws IOException {
		SpringApplication.run(ProyectoCvdsApplication.class, args);

	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			userRepository.save(new User("admin@site.org", encryptPassword("admin"), "Admin :D", "",Arrays.asList(UserRole.ADMINISTRATOR, UserRole.CUSTOMER)));
		};
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
