package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.dto.PrendasDto;

public class PrendasMapper {

    public static PrendasDto mapToPrendasDto (Prendas prendas) {
        return new PrendasDto(
                prendas.getPrendaId(),
                prendas.getFoto(),
                prendas.getSePlancha(),
                prendas.getUltimoLavado(),
                prendas.getTipo(),
                prendas.getUltimoUso(),
                prendas.getUser(),
                (prendas.getUser()).getEmail()
        );
    }

    public static Prendas mapToPrendas(PrendasDto prendasDto){
        return new Prendas(
                prendasDto.getPrendaId(),
                prendasDto.getFoto(),
                prendasDto.getSePlancha(),
                prendasDto.getUltimoLavado(),
                prendasDto.getTipo(),
                prendasDto.getUltimoUso(),
                prendasDto.getUser()
        );
    }
}
