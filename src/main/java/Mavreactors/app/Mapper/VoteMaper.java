package Mavreactors.app.Mapper;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.dto.UserDto;
import Mavreactors.app.dto.VoteDto;

import java.util.UUID;

public class VoteMaper {
    public static Vote mapToVote(VoteDto voteDto, String email){
        UUID token = UUID.randomUUID();
        return new Vote(
                token,
                email,
                voteDto.getOutfitId()
        );
    }
}
