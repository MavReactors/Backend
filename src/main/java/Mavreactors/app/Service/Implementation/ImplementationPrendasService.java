package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Mapper.PrendasMapper;
import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.PrendaRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.PrendasService;
import Mavreactors.app.dto.PrendasDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImplementationPrendasService implements PrendasService {
    private final PrendaRepository prendaRepository;
    private final UserRepository userRepository;

    @Override
    public PrendasDto createPrenda(PrendasDto prendasDto) {
        Prendas prenda = PrendasMapper.mapToPrendas(prendasDto);
        Prendas savedPrenda = prendaRepository.save(prenda);
        return PrendasMapper.mapToPrendasDto(savedPrenda);
    }

    @Override
    public List<PrendasDto> getAllPrendasByUser(User user) {
        List<Prendas> prendas = prendaRepository.findByUser(user);
        return prendas.stream().map(prenda -> PrendasMapper.mapToPrendasDto(prenda))
                .collect(Collectors.toList());
    }

    @Override
    public PrendasDto getPrendaById(Long prendasId) {
        Prendas prendas = prendaRepository.findById(prendasId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee is not exists with given id: " + prendasId));

        return PrendasMapper.mapToPrendasDto(prendas);
    }

    @Override
    public PrendasDto updatePrenda(Long prendasId, PrendasDto updatePrenda) {
        Prendas prendas = prendaRepository.findById(prendasId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee is not exists with given id: " + prendasId));
        prendas.setFoto(updatePrenda.getFoto());
        prendas.setSePlancha(updatePrenda.getSePlancha());
        prendas.setUltimoLavado(updatePrenda.getUltimoLavado());
        prendas.setTipo(updatePrenda.getTipo());
        prendas.setUltimoUso(updatePrenda.getUltimoUso());
        prendas.setUser(updatePrenda.getUser());

        Prendas updatePrendaOBJ = prendaRepository.save(prendas);

        return PrendasMapper.mapToPrendasDto(updatePrendaOBJ);
    }

    @Override
    public void deletePrenda(Long prendasId) {
        Prendas prendas = prendaRepository.findById(prendasId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee is not exists with given id: " + prendasId));
        prendaRepository.deleteById(prendasId);
    }
}
