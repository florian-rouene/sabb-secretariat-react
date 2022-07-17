package fr.sabb.controller;

import fr.sabb.data.object.Association;
import fr.sabb.service.assocation.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AssociationController {

    @Autowired
    private AssociationService associationService;

    @GetMapping("/associations")
    public List<Association> getAssociations(@RequestParam(value = "name", defaultValue = "World") String name) {
        return associationService.getAllActive();
    }
}
