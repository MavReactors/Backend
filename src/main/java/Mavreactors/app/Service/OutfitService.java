package Mavreactors.app.Service;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import Mavreactors.app.dto.OutfitDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OutfitService {
    Outfit createOutfit(OutfitDto outfitDto,String email);
    List<Outfit> getAllOutfitsByUser(User user);
    Outfit getOutfitById(UUID outfitId);
    Outfit updateOutfit(UUID outfitId, OutfitDto updateOutfit);
    void deleteOutfit(UUID outfitId);
    void deletePrendaFromOutfit(UUID outfitId, UUID clothingId);
    List<Outfit> getAllPublicOutfits();
    Outfit createSpecialDate(UUID outfitId, Date date);
    List<Date> getSpecialDate(UUID outfitId);
    List<Date> updateSpecialDate(UUID outfitId, Date oldDate,Date newDate);
    void deleteSpecialDate(UUID outfitId, Date date);
}
