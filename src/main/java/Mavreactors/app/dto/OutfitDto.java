package Mavreactors.app.dto;

import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.User;
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
    private List<UUID> clothingIds;
    private Boolean isPublic;
}
