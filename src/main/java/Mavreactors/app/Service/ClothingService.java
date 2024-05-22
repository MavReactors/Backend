package Mavreactors.app.Service;

import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.Type;
import Mavreactors.app.Model.User;
import Mavreactors.app.dto.ClothingDto;

import java.util.List;
import java.util.UUID;

public interface ClothingService {
    Clothing createClothing(ClothingDto clothingDto, String email);
    List<Clothing> getAllClothingByUser(User user);
    Clothing getClothingById(UUID prendasId);
    Clothing updateClothing(UUID prendasId, ClothingDto updatePrenda);
    void deleteClothing(UUID prendasId);
    List<Clothing> getAllFavoriteClothing(User user);
    List<Clothing> getClothingByType(Type type);
}
