package Mavreactors.app.dto;

import Mavreactors.app.Model.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class ClothingDto {
    private String photo;
    private Boolean isIroned;
    private Type type;
    private Date lastWear;
}
