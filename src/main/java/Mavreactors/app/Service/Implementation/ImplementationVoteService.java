package Mavreactors.app.Service.Implementation;

import Mavreactors.app.Mapper.VoteMaper;
import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.Repository.OutfitRepository;
import Mavreactors.app.Repository.VoteRepository;
import Mavreactors.app.Service.VoteService;
import Mavreactors.app.dto.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Vote vote = VoteMaper.mapToVote(voteDto, email);// Manejar el Optional<Outfit>
        Outfit outfit = outfitRepository.findById(voteDto.getOutfitId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Outfit is not exists with given id: " + voteDto.getOutfitId()));
        outfit.setVoteCount(outfit.getVoteCount() + 1);
        outfitRepository.save(outfit);

        return voteRepository.save(vote);
    }

    @Override
    public void deleteVote(UUID voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id: " + voteId));
        User user = vote.getUser();
        // Decrementar el contador de votos del outfit
        Outfit outfit = vote.getOutfit();
        outfit.setVoteCount(outfit.getVoteCount() - 1);
        outfitRepository.save(outfit);
        voteRepository.delete(vote);
    }

    @Override
    public List<Vote> getAllVotesByOutfits() {
        return voteRepository.findAll();
    }
}
