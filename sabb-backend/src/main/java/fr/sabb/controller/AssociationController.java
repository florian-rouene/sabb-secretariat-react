package fr.sabb.controller;

import fr.sabb.data.object.Association;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.assocation.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/associations")
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    @GetMapping
    public List<Association> getAssociations() {
        return associationService.getAllActive();
    }

    @GetMapping("/{id}")
    public Association getAssociation(@PathVariable int id) {
        return associationService.getById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAssociation(@PathVariable int id, @RequestBody Association association) throws ValidationException {
        Association currentAssociation = associationService.getById(id).orElseThrow(RuntimeException::new);
        currentAssociation.setName(association.getName());
        currentAssociation.setMain(association.isMain());
        currentAssociation.setFfbbLocation(association.getFfbbLocation());
        currentAssociation.setNameFfbb(association.getNameFfbb());
        currentAssociation.setNameFfbbCtc(association.getNameFfbbCtc());
        currentAssociation.setFfbbLocationBis(association.getFfbbLocationBis());
        associationService.updateOrInsert(association);

        return ResponseEntity.ok(currentAssociation);
    }

    @PostMapping
    public ResponseEntity createAssociation(@RequestBody Association association) throws URISyntaxException, ValidationException {
        associationService.updateOrInsert(association);
        return ResponseEntity.created(new URI("/associations/" + association.getId())).body(association);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAssociation(@PathVariable int id) {
        Association associationToDelete = new Association();
        associationToDelete.setId(id);
        associationService.delete(associationToDelete);
        return ResponseEntity.ok().build();
    }
}
