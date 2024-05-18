package Mavreactors.app.Service;

import Mavreactors.app.Model.Vote;
import Mavreactors.app.dto.VoteDto;

import java.util.List;

public interface VoteService {
    Vote createVote(VoteDto voteDto, String email);
    List<Vote> getAllVotesByOutfits();
}
