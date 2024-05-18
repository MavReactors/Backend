package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Mapper.ClothingMapper;
import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.ClothingRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.ClothingService;
import Mavreactors.app.dto.ClothingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImplementationClothingService implements ClothingService {
    private final ClothingRepository clothingRepository;
    private final UserRepository userRepository;

    @Override
    public Clothing createClothing(ClothingDto clothingDto, String email) {
        Clothing clothing = ClothingMapper.mapToClothing(clothingDto, email);
        return clothingRepository.save(clothing);
    }

    @Override
    public List<Clothing> getAllClothingByUser(User user) {
        return clothingRepository.findByUser(user);
    }

    @Override
    public Clothing getClothingById(UUID clothingId) {
        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clothing is not exists with given id: " + clothingId));

        return clothing;
    }

    @Override
    public Clothing updateClothing(UUID clothingId, ClothingDto updatedClothing) {
        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clothing is not exists with given id: " + clothingId));
        clothing.setPhoto(updatedClothing.getPhoto());
        clothing.setIsIroned(updatedClothing.getIsIroned());
        clothing.setType(updatedClothing.getType());
        clothing.setLastWear(updatedClothing.getLastWear());

        return clothingRepository.save(clothing);
    }

    @Override
    public void deleteClothing(UUID clothingId) {
        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee is not exists with given id: " + clothingId));
        clothingRepository.deleteById(clothingId);
    }
}
