package Mavreactors.app.dto;

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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrendasDto {
    private Long prendaId;
    private String foto;
    private Boolean sePlancha;
    private Date ultimoLavado;
    private Character tipo;
    private Date ultimoUso;
    private User user;
    private String userEmail;

}
