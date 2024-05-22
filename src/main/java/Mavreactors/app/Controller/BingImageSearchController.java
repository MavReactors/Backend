package Mavreactors.app.Controller;
import Mavreactors.app.Service.BingVisualSearchService;
import Mavreactors.app.dto.ClothingDto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class BingImageSearchController {

    @Autowired
    private BingVisualSearchService bingVisualSearchService;

    @PostMapping("/api/visual-search")
    public ResponseEntity<?> newVisualSearch(@RequestBody ClothingDto clothingDto) {
        try {
            String image = clothingDto.getPhoto();

            String result = bingVisualSearchService.searchByImage(image);
            // Devuelve la respuesta de la API de Bing Visual Search
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
