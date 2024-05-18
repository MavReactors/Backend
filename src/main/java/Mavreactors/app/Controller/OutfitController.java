package Mavreactors.app.Controller;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.OutfitService;
import Mavreactors.app.dto.OutfitDto;
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
    public ResponseEntity<Outfit> createOutfit(@RequestBody OutfitDto outfitDto, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        Outfit saveOutfit = outfitService.createOutfit(outfitDto, user.getEmail());
        return new ResponseEntity<>(saveOutfit, HttpStatus.CREATED);
    }

    @GetMapping("/outfit")
    public ResponseEntity<List<Outfit>> getOutfits(@CookieValue("authToken") UUID authToken){
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        List<Outfit> outfitDtos = outfitService.getAllOutfitsByUser(user);
        return ResponseEntity.ok(outfitDtos);
    }

    @GetMapping("/outfit/{id}")
    public ResponseEntity<Outfit> getOutfitById(@PathVariable("id") UUID outfotId) {
        Outfit outfit = outfitService.getOutfitById(outfotId);
        return ResponseEntity.ok(outfit);

    }

    @PutMapping("/outfit/{id}")
    public ResponseEntity<Outfit> udapteOutfit(@PathVariable("id") UUID outfotId,
                                                   @RequestBody OutfitDto updatedOutfit){
        Outfit outfit = outfitService.updateOutfit(outfotId, updatedOutfit);
        return ResponseEntity.ok(outfit);
    }

    @DeleteMapping("/outfit/{id}")
    public ResponseEntity<String>  deleteOutfit(@PathVariable("id") UUID outfotId){
        outfitService.deleteOutfit(outfotId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

    @DeleteMapping("/outfit/{uuid}/prenda/{id}")
    public ResponseEntity<String>  deleteOutfit(@PathVariable("uuid") UUID outfitId ,@PathVariable("id") UUID clothingId){
        outfitService.deletePrendaFromOutfit(outfitId, clothingId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

    @GetMapping("/outfit/public")
    public ResponseEntity<List<Outfit>> getPublicOutfits() {
        List<Outfit> outfits = outfitService.getAllPublicOutfits();
        return ResponseEntity.ok(outfits);
    }

}
