package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Mapper.OutfitMapper;
import Mavreactors.app.Mapper.PrendasMapper;
import Mavreactors.app.Mapper.VoteMaper;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.PrendaRepository;
import Mavreactors.app.Repository.VoteRepository;
import Mavreactors.app.Service.VoteService;
import Mavreactors.app.dto.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImplementationVoteService implements VoteService {
    private final VoteRepository voteRepository;
    private final OutfitRepository outfitRepository;

    @Override
    public Vote createVote(VoteDto voteDto, String email) {
        if (voteRepository.findByUserIdAndOutfitId(email, voteDto.getOutfitId()) != null) {
            return null;
        }
        Vote vote = VoteMaper.mapToVote(voteDto, email);
        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllVotesByOutfits() {
        return voteRepository.findAll();
    }
}
