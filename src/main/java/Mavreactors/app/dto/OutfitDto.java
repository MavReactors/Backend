package Mavreactors.app.dto;

import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutfitDto {
    private UUID outfitId;
    private User user;
    private String userEmail;
    private List<Prendas> prendas;
    private Boolean isPublic;
}
