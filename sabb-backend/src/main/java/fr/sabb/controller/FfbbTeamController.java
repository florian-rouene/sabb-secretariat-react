package fr.sabb.controller;

import fr.sabb.business.TeamFillerBusiness;
import fr.sabb.data.dto.FfbbTeamDto;
import fr.sabb.data.object.Team;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ffbb_equipes")
public class FfbbTeamController {

    @Autowired
    private TeamFillerBusiness teamFillerBusiness;

    @GetMapping("/{ffbbName}")
    public List<FfbbTeamDto> getFfbbTeams(@PathVariable String ffbbName) throws ValidationException, IOException {
        return this.teamFillerBusiness.getFFBBTeamForAssociation(ffbbName).stream().sorted(((o1, o2) -> o1.getName().compareTo(o2.getName()))).toList();
    }
}
