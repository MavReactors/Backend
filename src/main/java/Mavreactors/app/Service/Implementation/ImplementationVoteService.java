package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Mapper.VoteMaper;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.VoteRepository;
import Mavreactors.app.Service.VoteService;
import Mavreactors.app.dto.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImplementationVoteService implements VoteService {
    private final VoteRepository voteRepository;
    private final OutfitRepository outfitRepository;

    @Override
    public Vote createVote(VoteDto voteDto, String email) {
        Vote existingVote = voteRepository.findByUserIdAndOutfitId(email, voteDto.getOutfitId());
        if (existingVote != null) {
            return existingVote;
        }
        Vote vote = VoteMaper.mapToVote(voteDto, email);
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllVotesByOutfits() {
        return voteRepository.findAll();
    }
}
