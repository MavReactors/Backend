package Mavreactors.app.dto;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private String userEmail;
    private UUID outfitId;
}
