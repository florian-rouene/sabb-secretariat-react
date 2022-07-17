package fr.sabb.controller;

import fr.sabb.data.object.Association;
import fr.sabb.service.assocation.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
