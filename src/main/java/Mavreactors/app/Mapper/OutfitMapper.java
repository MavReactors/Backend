package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.dto.OutfitDto;

public class OutfitMapper {
    public static Outfit mapToOutfit (OutfitDto outfitDto, String email) {
        return new Outfit(
                email,
                outfitDto.getClothingIds(),
                outfitDto.getIsPublic()
        );
    }
}
