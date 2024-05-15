package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Mapper.UserMapper;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.UserService;
import Mavreactors.app.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImplementationUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
