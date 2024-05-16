package Mavreactors.app.Service;

import Mavreactors.app.Model.User;
import Mavreactors.app.dto.OutfitDto;
import Mavreactors.app.dto.PrendasDto;

import java.util.List;
import java.util.UUID;

public interface OutfitService {
    OutfitDto createOutfit(OutfitDto outfitDto);
    List<OutfitDto> getAllOutfitsByUser(User user);
    OutfitDto getOutfitById(UUID outfitId);
    OutfitDto updateOutfit(UUID outfitId, OutfitDto updateOutfit);
    void deleteOutfit(UUID outfitId);
    void deletePrendaFromOutfit(UUID outfitId, Long prendaId);
}
