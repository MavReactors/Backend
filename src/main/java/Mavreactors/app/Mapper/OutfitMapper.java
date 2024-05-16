package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.dto.OutfitDto;

public class OutfitMapper {
    public static OutfitDto mapToOutfitDto (Outfit outfit) {
        return new OutfitDto(
                outfit.getOutfitId(),
                outfit.getUser(),
                (outfit.getUser()).getEmail(),
                outfit.getPrendas(),
                outfit.getIsPublic()
        );
    }

    public static Outfit mapToOutfit (OutfitDto outfitDto) {
        return new Outfit(
                outfitDto.getOutfitId(),
                outfitDto.getUser(),
                outfitDto.getPrendas(),
                outfitDto.getIsPublic()
        );
    }
}
