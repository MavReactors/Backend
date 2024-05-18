package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Mapper.OutfitMapper;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.ClothingRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.dto.OutfitDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class  ImplementationOutfitService implements OutfitService {

    private final OutfitRepository outfitRepository;
    private final ClothingRepository clothingRepository;

    @Override
    public Outfit createOutfit(OutfitDto outfitDto, String email) {
        Outfit outfit = OutfitMapper.mapToOutfit(outfitDto, email);
        return outfitRepository.save(outfit);
    }

    @Override
    @Transactional
    public List<Outfit> getAllOutfitsByUser(User user) {
        return outfitRepository.findByUser(user);
    }

    @Override
    public Outfit getOutfitById(UUID outfitId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));

        return outfit;
    }

    @Override
    public Outfit updateOutfit(UUID outfitId, OutfitDto updateOutfit) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));
        outfit.setClothingIds(updateOutfit.getClothingIds());
        outfit.setIsPublic(updateOutfit.getIsPublic());
        return  outfitRepository.save(outfit);
    }

    @Override
    public void deleteOutfit(UUID outfitId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));
        outfitRepository.deleteById(outfitId);
    }

    @Override
    public void deletePrendaFromOutfit(UUID outfitId, UUID clothingId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));

        Clothing clouthing = clothingRepository.findById(clothingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clouthing does not exist with given id: " + clothingId));

        if (!outfit.getClothingList().contains(clouthing)) {
            throw new ResourceNotFoundException("Clouthing is not part of the outfit with id: " + outfitId);
        }

        outfit.getClothingList().remove(clouthing);
        outfitRepository.save(outfit);
    }

    @Override
    public List<Outfit> getAllPublicOutfits() {
        return outfitRepository.findByIsPublicTrue();
    }
}
