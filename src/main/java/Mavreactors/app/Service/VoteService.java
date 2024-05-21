package Mavreactors.app.Service;

import Mavreactors.app.Model.Vote;
import Mavreactors.app.dto.VoteDto;

import java.util.List;
import java.util.UUID;

public interface VoteService {
    Vote createVote(VoteDto voteDto, String email);
    void deleteVote(UUID voteId);
    List<Vote> getAllVotesByOutfits();
}
