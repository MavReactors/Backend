package Mavreactors.app.Controller;

import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.Type;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.ClothingService;
import Mavreactors.app.dto.ClothingDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "https://icy-ocean-085971c1e.5.azurestaticapps.net/")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class ClothingController {

    private final ClothingService clothingService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @PostMapping("/prenda")
    public ResponseEntity<Clothing> createClothing(@RequestBody ClothingDto clothingDto, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        Clothing savePrenda = clothingService.createClothing(clothingDto, user.getEmail());
        return new ResponseEntity<>(savePrenda, HttpStatus.CREATED);
    }

    @GetMapping("/prenda")
    public ResponseEntity<List<Clothing>> getAllClothing(@CookieValue("authToken") UUID authToken){
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        List<Clothing> clothing = clothingService.getAllClothingByUser(user);
        return ResponseEntity.ok(clothing);
    }

    @GetMapping("/prenda/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable("id") UUID clothingId) {
        Clothing clothing = clothingService.getClothingById(clothingId);
        return ResponseEntity.ok(clothing);

    }

    @GetMapping("/prendas/{type}")
    public ResponseEntity<?> getClothingByType(@PathVariable Type type, @CookieValue("authToken") UUID authToken) {
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        try {
            List<Clothing> clothingList = clothingService.getClothingByType(type);
            return ResponseEntity.ok(clothingList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid clothing type: " + type);
        }
    }

    @GetMapping("/prendas-favoritas")
    public ResponseEntity<List<Clothing>> getAllFavoriteClothing(@CookieValue("authToken") UUID authToken){
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        List<Clothing> clothing = clothingService.getAllFavoriteClothing(user);
        return ResponseEntity.ok(clothing);
    }

    @PutMapping("/prenda/{id}")
    public ResponseEntity<Clothing> udapteClothing(@PathVariable("id") UUID clothingId,
                                                    @RequestBody ClothingDto updatedClothing){
        Clothing clothing = clothingService.updateClothing(clothingId, updatedClothing);
        return ResponseEntity.ok(clothing);
    }

    @DeleteMapping("/prenda/{id}")
    public ResponseEntity<String>  deleteClothing(@PathVariable("id") UUID clothingId){
        clothingService.deleteClothing(clothingId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

}
