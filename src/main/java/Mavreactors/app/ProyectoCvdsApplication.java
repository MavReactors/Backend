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
import java.util.Arrays;

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
			userRepository.save(new User("admin@site.org", "admin", Arrays.asList(UserRole.ADMINISTRATOR, UserRole.CUSTOMER)));
		};
	}

}
