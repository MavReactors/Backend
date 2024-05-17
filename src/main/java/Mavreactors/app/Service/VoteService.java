package Mavreactors.app.Service;

import Mavreactors.app.dto.VoteDto;

import java.util.List;

public interface VoteService {
    VoteDto createVote(VoteDto voteDto);
    List<VoteDto> getAllVotesByOutfits();
}
