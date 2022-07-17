package fr.sabb.controller;

import fr.sabb.data.object.Team;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/equipes")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getTeams() {
        return teamService.getAllActiveForCurrentSeason();
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable int id) {
        return teamService.getById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTeam(@PathVariable int id, @RequestBody Team team) throws ValidationException {
        Team currentTeam = teamService.getById(id).orElseThrow(RuntimeException::new);
        currentTeam.setName(team.getName());
        teamService.updateOrInsert(team);

        return ResponseEntity.ok(currentTeam);
    }

    @PostMapping
    public ResponseEntity createTeam(@RequestBody Team team) throws URISyntaxException, ValidationException {
        team.setActive(true);
        teamService.updateOrInsert(team);
        return ResponseEntity.created(new URI("/equipes/" + team.getId())).body(team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable int id) {
        Team teamToDelete = new Team();
        teamToDelete.setId(id);
        teamService.delete(teamToDelete);
        return ResponseEntity.ok().build();
    }
}
