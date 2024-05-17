package Mavreactors.app.Mapper;

import Mavreactors.app.Model.User;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.dto.UserDto;
import Mavreactors.app.dto.VoteDto;

public class VoteMaper {
    public static VoteDto mapToVoteDto (Vote vote) {
        return new VoteDto(
                vote.getVoteId(),
                vote.getUser(),
                (vote.getUser()).getEmail(),
                vote.getOutfit(),
                (vote.getOutfit()).getOutfitId()
        );
    }

    public static Vote mapToVote(VoteDto voteDto){
        return new Vote(
                voteDto.getVoteId(),
                voteDto.getUser(),
                voteDto.getOutfit()
        );
    }
}
