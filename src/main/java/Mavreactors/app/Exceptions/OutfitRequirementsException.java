package Mavreactors.app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OutfitRequirementsException extends RuntimeException {
    public OutfitRequirementsException(String message) {super(message);}
}
