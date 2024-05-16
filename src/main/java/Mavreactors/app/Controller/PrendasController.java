package Mavreactors.app.Controller;

import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import Mavreactors.app.Repository.SessionRepository;
import Mavreactors.app.Repository.UserRepository;
import Mavreactors.app.Service.PrendasService;
import Mavreactors.app.dto.PrendasDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class PrendasController {

    private final PrendasService prendasService;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @PostMapping("/prenda")
    public ResponseEntity<PrendasDto> createPrenda(@RequestBody PrendasDto prendasDto, @CookieValue("authToken") UUID authToken){
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        prendasDto.setUserEmail(user.getEmail());
        prendasDto.setUser(user);
        PrendasDto savePrenda = prendasService.createPrenda(prendasDto);
        return new ResponseEntity<>(savePrenda, HttpStatus.CREATED);
    }

    @GetMapping("/prenda")
    public ResponseEntity<List<PrendasDto>> getAllPrendas(@CookieValue("authToken") UUID authToken){
        // Obtiene el usuario a partir del correo electrónico
        Session session = sessionRepository.findByToken(authToken);
        User user = session.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Devuelve un error si el usuario no se encuentra
        }
        List<PrendasDto> prendas = prendasService.getAllPrendasByUser(user);
        return ResponseEntity.ok(prendas);
    }

    @GetMapping("/prenda/{id}")
    public ResponseEntity<PrendasDto> getPrendaById(@PathVariable("id") long prendaId) {
        PrendasDto prendasDto = prendasService.getPrendaById(prendaId);
        return ResponseEntity.ok(prendasDto);

    }

    @PutMapping("/prenda/{id}")
    public ResponseEntity<PrendasDto> udaptePrenda(@PathVariable("id") Long prendaId,
                                                      @RequestBody PrendasDto updatedPrenda){
        updatedPrenda.setUser(userRepository.findByEmail(updatedPrenda.getUserEmail()));
        PrendasDto prendasDto = prendasService.updatePrenda(prendaId, updatedPrenda);
        return ResponseEntity.ok(prendasDto);
    }

    @DeleteMapping("/prenda/{id}")
    public ResponseEntity<String>  deletePrenda(@PathVariable("id") Long prendaId){
        prendasService.deletePrenda(prendaId);
        return ResponseEntity.ok("Clothing deleted successfully! ");
    }

}
