package fr.sabb.controller;

import fr.sabb.data.converter.TeamConverter;
import fr.sabb.data.dto.TeamDto;
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

    @Autowired
    private TeamConverter teamConverter;

    @GetMapping
    public List<TeamDto> getTeams() {
        return teamService.getAllDto();
    }

    @GetMapping("/{id}")
    public TeamDto getTeam(@PathVariable int id) {
        return teamService.getById(id).map(teamConverter::convertToTeamDto).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTeam(@PathVariable int id, @RequestBody TeamDto team) throws ValidationException {
        Team currentTeam = teamService.getById(id).orElseThrow(RuntimeException::new);
       // teamService.updateOrInsert(team);

        return ResponseEntity.ok(currentTeam);
    }

    @PostMapping
    public ResponseEntity createTeam(@RequestBody TeamDto teamDto) throws URISyntaxException, ValidationException {
        teamService.updateOrInsert(this.teamConverter.convertToTeam(teamDto));
        return ResponseEntity.created(new URI("/equipes/" + teamDto.getId())).body(teamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable int id) {
        Team teamToDelete = new Team();
        teamToDelete.setId(id);
        teamService.delete(teamToDelete);
        return ResponseEntity.ok().build();
    }
}
