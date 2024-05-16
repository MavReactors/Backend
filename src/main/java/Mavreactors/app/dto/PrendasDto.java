package Mavreactors.app.dto;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.Type;
import Mavreactors.app.Model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrendasDto {
    private Long prendaId;
    private String foto;
    private Boolean sePlancha;
    private Date ultimoLavado;
    private Type tipo;
    private Date ultimoUso;
    private User user;
    private String userEmail;
}
