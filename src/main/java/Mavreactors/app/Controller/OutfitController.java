package Mavreactors.app.Controller;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.dto.OutfitDto;
import Mavreactors.app.dto.PrendasDto;
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
public class OutfitController {
    private final OutfitService outfitService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @PostMapping("/outfit")
    public ResponseEntity<OutfitDto> createOutfit(@RequestBody OutfitDto outfitDto, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        outfitDto.setUserEmail(user.getEmail());
        outfitDto.setUser(user);
        UUID token = UUID.randomUUID();
        outfitDto.setOutfitId(token);
        OutfitDto saveOutfit = outfitService.createOutfit(outfitDto);
        return new ResponseEntity<>(saveOutfit, HttpStatus.CREATED);
    }

    @GetMapping("/outfit")
    public ResponseEntity<List<OutfitDto>> getOutfits(@CookieValue("authToken") UUID authToken){
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        List<OutfitDto> outfitDtos = outfitService.getAllOutfitsByUser(user);
        return ResponseEntity.ok(outfitDtos);
    }

    @GetMapping("/outfit/{id}")
    public ResponseEntity<OutfitDto> getOutfitById(@PathVariable("id") UUID outfotId) {
        OutfitDto outfitDto = outfitService.getOutfitById(outfotId);
        return ResponseEntity.ok(outfitDto);

    }

    @PutMapping("/outfit/{id}")
    public ResponseEntity<OutfitDto> udapteOutfit(@PathVariable("id") UUID outfotId,
                                                   @RequestBody OutfitDto updatedOutfit){
        updatedOutfit.setUser(userRepository.findByEmail(updatedOutfit.getUserEmail()));
        OutfitDto outfitDto = outfitService.updateOutfit(outfotId, updatedOutfit);
        return ResponseEntity.ok(outfitDto);
    }

    @DeleteMapping("/outfit/{id}")
    public ResponseEntity<String>  deleteOutfit(@PathVariable("id") UUID outfotId){
        outfitService.deleteOutfit(outfotId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

    @DeleteMapping("/outfit/{uuid}/prenda/{id}")
    public ResponseEntity<String>  deleteOutfit(@PathVariable("uuid") UUID outfitId ,@PathVariable("id") Long prendaId){
        outfitService.deletePrendaFromOutfit(outfitId, prendaId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

    @GetMapping("/outfit/public")
    public ResponseEntity<List<OutfitDto>> getPublicOutfits() {
        List<OutfitDto> outfitDtos = outfitService.getAllPublicOutfits();
        return ResponseEntity.ok(outfitDtos);
    }

}
