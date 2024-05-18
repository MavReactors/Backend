package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Clothing;
import Mavreactors.app.dto.ClothingDto;

public class ClothingMapper {
    public static Clothing mapToClothing(ClothingDto clothingDto, String emal){
        return new Clothing(
                clothingDto.getPhoto(),
                clothingDto.getIsIroned(),
                clothingDto.getType(),
                clothingDto.getLastWear(),
                emal
        );
    }
}
