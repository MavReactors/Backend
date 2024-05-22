package Mavreactors.app.Controller;

import Mavreactors.app.Model.Session;
import Mavreactors.app.Exceptions.ResourceNotFoundException;
import Mavreactors.app.Model.User;
import Mavreactors.app.Model.Vote;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Repository.VoteRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.Service.VoteService;
import Mavreactors.app.dto.VoteDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class VoteController {

    private final OutfitService outfitService;
    private final SessionRepository sessionRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<Vote> createVote(@RequestBody VoteDto voteDto, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        Vote saveVote = voteService.createVote(voteDto, user.getEmail());
        return new ResponseEntity<>(saveVote, HttpStatus.CREATED);
    }

    @GetMapping("/vote")
    public ResponseEntity<List<Vote>> getAllVotesByOutfits(){
        List<Vote> votes = voteService.getAllVotesByOutfits();
        return ResponseEntity.ok(votes);
    }

    @DeleteMapping("/vote/{id}")
    public ResponseEntity<?> deleteVote(@PathVariable UUID id, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id: " + id));
        if (!vote.getUserId().equals(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Devuelve un error si el usuario no es el propietario del voto
        }
        voteService.deleteVote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
