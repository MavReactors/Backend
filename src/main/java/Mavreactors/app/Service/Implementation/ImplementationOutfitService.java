package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Exceptions.OutfitRequirementsException;
import Mavreactors.app.Mapper.OutfitMapper;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.Type;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.ClothingRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.dto.OutfitDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class  ImplementationOutfitService implements OutfitService {

    private final OutfitRepository outfitRepository;
    private final ClothingRepository clothingRepository;

    @Override
    public Outfit createOutfit(OutfitDto outfitDto, String email) {
        Outfit outfit = OutfitMapper.mapToOutfit(outfitDto, email);
        // Verificar si el outfit cumple con los requisitos mínimos
        if (!outfitMeetsRequirements(outfit)) {
            throw new OutfitRequirementsException("The outfit must have at least one 'TRUNK', 'PANTS' and 'SHOES' type garment");
        }
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
        if(updateOutfit.getClothingIds() != null){
            // Obten la lista actual de ids de prendas del outfit
            List<UUID> currentClothingIds = outfit.getClothingIds();
            // Obten la lista de ids de prendas que se están intentando añadir
            List<UUID> newClothingIds = updateOutfit.getClothingIds();
            // Itera sobre la lista de ids de prendas que se están intentando añadir
            for (UUID clothingId : newClothingIds) {
                // Si el id de la prenda no está en la lista actual, agrégalo
                if (!currentClothingIds.contains(clothingId)) {
                    currentClothingIds.add(clothingId);
                }
            }
            // Actualiza la lista de ids de prendas del outfit
            outfit.setClothingIds(currentClothingIds);
        }
        if(updateOutfit.getIsPublic() != null){
            outfit.setIsPublic(updateOutfit.getIsPublic());
        }
        // Verificar si el outfit cumple con los requisitos mínimos
        if (!outfitMeetsRequirements(outfit)) {
            throw new OutfitRequirementsException("The outfit must have at least one 'TRUNK', 'PANTS' and 'SHOES' type garment");
        }
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

        if (!outfit.getClothingIds().contains(clothingId)) {
            throw new ResourceNotFoundException("Clouthing is not part of the outfit with id: " + outfitId);
        }

        outfit.getClothingIds().remove(clothingId);
        if (!outfitMeetsRequirements(outfit)) {
            throw new OutfitRequirementsException("The outfit must have at least one 'TRUNK', 'PANTS' and 'SHOES' type garment");
        }
        outfitRepository.save(outfit);
    }

    @Override
    public List<Outfit> getAllPublicOutfits() {
        return outfitRepository.findByIsPublicTrue();
    }

    @Override
    public Outfit createSpecialDate(UUID outfitId, Date date) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));
        if (outfit.getSpecialDate() == null) {
            outfit.setSpecialDate(new ArrayList<>());
        }

        if (outfit.getSpecialDate().contains(date)) {
            throw new ResourceNotFoundException("This date is part of the outfit with id: " + date);
        }

        outfit.getSpecialDate().add(date);
        return outfitRepository.save(outfit);
    }

    @Override
    public List<Date> getSpecialDate(UUID outfitId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));
        return outfit.getSpecialDate();
    }

    @Override
    public List<Date> updateSpecialDate(UUID outfitId, Date oldDate, Date newDate) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));

        if (!outfit.getSpecialDate().contains(oldDate)) {
            throw new ResourceNotFoundException("This date is not part of the outfit with id: " + oldDate);
        }

        List<Date> specialDates = outfit.getSpecialDate();
        int i = 0;
        while (i < specialDates.size() && !(specialDates.get(i).equals(oldDate))) {
            i++;
        }
        specialDates.set(i, newDate);
        outfit.setSpecialDate(specialDates);
        outfitRepository.save(outfit);
        return specialDates;
    }

    @Override
    public void deleteSpecialDate(UUID outfitId, Date date) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));

        if (!outfit.getSpecialDate().contains(date)) {
            throw new ResourceNotFoundException("This date is not part of the outfit with id: " + date);
        }
        outfit.getSpecialDate().remove(date);
        outfitRepository.save(outfit);
    }

    @Override
    public List<Outfit> getOutfitsOrderByVotes() {
        return outfitRepository.findPublicOutfitsOrderedByVoteCount();
    }

    private boolean outfitMeetsRequirements(Outfit outfit) {
        // Obtener los IDs de las prendas del outfit
        List<UUID> clothingIds = outfit.getClothingIds();
        // Lista para guardar los tipos de prendas del outfit
        List<Type> clothingTypes = new ArrayList<>();

        // Obtener los tipos de cada prenda en el outfit
        for (UUID clothingId : clothingIds) {
            Clothing clothing = clothingRepository.findById(clothingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Clothing not found with ID: " + clothingId));

            // Agregar el tipo de la prenda a la lista
            clothingTypes.add(clothing.getType());
        }
        // Verificar si el outfit cumple con los requisitos
        boolean hasTrunk = clothingTypes.contains(Type.TRUNK);
        boolean hasPants = clothingTypes.contains(Type.PANTS);
        boolean hasShoes = clothingTypes.contains(Type.SHOES);

        return hasTrunk && hasPants && hasShoes;
    }
}
