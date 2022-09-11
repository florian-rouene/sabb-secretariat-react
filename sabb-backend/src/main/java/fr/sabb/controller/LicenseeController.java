package fr.sabb.controller;

import fr.sabb.data.converter.LicenseeConverter;
import fr.sabb.data.dto.LicenseeDto;
import fr.sabb.data.object.Association;
import fr.sabb.service.assocation.AssociationService;
import fr.sabb.service.licensee.OfficialLicenseeConverter;
import fr.sabb.service.licensee.LicenseeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/licensees")
public class LicenseeController {

    @Autowired
    private LicenseeService licenseeService;

    @Autowired
    private LicenseeConverter licenseeConverter;

    @Autowired
    private AssociationService associationService;


    @GetMapping
    public List<LicenseeDto> getLicensee() {

        return licenseeService.getAll().stream().map(this.licenseeConverter::convertToDto).toList();

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            throw new RuntimeException("You must select the a file for uploading");
        }

        licenseeService.fillDBWithCsvFile(associationService.getAllActive().stream().filter(Association::isMain).findFirst().orElseThrow(),file);
        String originalName = file.getOriginalFilename();
        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }
}
