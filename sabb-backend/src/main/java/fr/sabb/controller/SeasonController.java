package fr.sabb.controller;

import fr.sabb.data.object.Season;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.season.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/saisons")
public class SeasonController {

    @Autowired
    private SeasonService seasonService;

    @GetMapping
    public List<Season> getSeasons() {
        return seasonService.getAll();
    }

    @GetMapping("/{id}")
    public Season getSeason(@PathVariable int id) {
        return seasonService.getById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSeason(@PathVariable int id, @RequestBody Season season) throws ValidationException {
        Season currentSeason = seasonService.getById(id).orElseThrow(RuntimeException::new);
        currentSeason.setName(season.getName());
        currentSeason.setReferenceYear(season.getReferenceYear());
        seasonService.updateOrInsert(season);

        return ResponseEntity.ok(currentSeason);
    }

    @PostMapping
    public ResponseEntity createSeason(@RequestBody Season season) throws URISyntaxException, ValidationException {
        season.setActive(true);
        seasonService.updateOrInsert(season);
        return ResponseEntity.created(new URI("/saisons/" + season.getId())).body(season);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSeason(@PathVariable int id) {
        Season seasonToDelete = new Season();
        seasonToDelete.setId(id);
        seasonService.delete(seasonToDelete);
        return ResponseEntity.ok().build();
    }
}
