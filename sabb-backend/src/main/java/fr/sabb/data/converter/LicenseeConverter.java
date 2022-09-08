package fr.sabb.data.converter;

import fr.sabb.data.dto.LicenseeDto;
import fr.sabb.data.object.Licensee;
import org.springframework.stereotype.Component;

@Component
public class LicenseeConverter {

    public LicenseeDto convertToDto (Licensee licensee) {
        LicenseeDto licenseeDto = new LicenseeDto(licensee);

        return licenseeDto;
    }
}
