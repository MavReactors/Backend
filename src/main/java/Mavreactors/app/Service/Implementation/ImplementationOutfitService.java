package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Mapper.OutfitMapper;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.PrendaRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.Service.PrendasService;
import Mavreactors.app.dto.OutfitDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class  ImplementationOutfitService implements OutfitService {

    private final OutfitRepository outfitRepository;
    private final PrendaRepository prendaRepository;

    @Override
    public OutfitDto createOutfit(OutfitDto outfitDto) {
        Outfit outfit = OutfitMapper.mapToOutfit(outfitDto);
        Outfit savedOutfit = outfitRepository.save(outfit);
        return OutfitMapper.mapToOutfitDto(savedOutfit);
    }

    @Override
    @Transactional
    public List<OutfitDto> getAllOutfitsByUser(User user) {
        List<Outfit> outfits = outfitRepository.findByUser(user);
        return outfits.stream().map(outfit -> OutfitMapper.mapToOutfitDto(outfit))
                .collect(Collectors.toList());
    }

    @Override
    public OutfitDto getOutfitById(UUID outfitId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));

        return OutfitMapper.mapToOutfitDto(outfit);
    }

    @Override
    public OutfitDto updateOutfit(UUID outfitId, OutfitDto updateOutfit) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));
        outfit.setUser(updateOutfit.getUser());
        outfit.setPrendas(updateOutfit.getPrendas());
        outfit.setIsPublic(updateOutfit.getIsPublic());
        Outfit updateOutfitOBJ = outfitRepository.save(outfit);
        return  OutfitMapper.mapToOutfitDto(updateOutfitOBJ);
    }

    @Override
    public void deleteOutfit(UUID outfitId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + outfitId));
        outfitRepository.deleteById(outfitId);
    }

    @Override
    public void deletePrendaFromOutfit(UUID outfitId, Long prendaId) {
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit does not exist with given id: " + outfitId));

        Prendas prenda = prendaRepository.findById(prendaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prenda does not exist with given id: " + prendaId));

        if (!outfit.getPrendas().contains(prenda)) {
            throw new ResourceNotFoundException("Prenda is not part of the outfit with id: " + outfitId);
        }

        outfit.getPrendas().remove(prenda);
        outfitRepository.save(outfit);
    }

    @Override
    public List<OutfitDto> getAllPublicOutfits() {
        List<Outfit> outfits = outfitRepository.findByIsPublicTrue();
        return outfits.stream().map(OutfitMapper::mapToOutfitDto)
                .collect(Collectors.toList());
    }
}
