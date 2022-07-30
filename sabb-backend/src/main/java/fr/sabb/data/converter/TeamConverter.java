package fr.sabb.data.converter;

import fr.sabb.data.dto.TeamDto;
import fr.sabb.data.object.Team;
import fr.sabb.service.assocation.AssociationService;
import fr.sabb.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter {

    @Autowired
    private AssociationService associationService;

    @Autowired
    private CategoryService categoryService;

    public Team convertToTeam(TeamDto dto) {

        dto.setAssociation(this.associationService.getByFfbbName(dto.getAssoName()).orElseThrow());
        dto.setCategory(this.categoryService.getById(dto.getCategoryId()).orElseThrow());

        return dto;
    }

    public TeamDto convertToTeamDto(Team team) {
        TeamDto teamDto = new TeamDto(team);

        teamDto.setCategoryId(team.getCategory().getId());
        teamDto.setAssoName(team.getAssociation().getName());

        return teamDto;
    }
}
