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

    public Team convertTeamDto(TeamDto dto) {
        Team team = new Team();

        team.setAssociation(this.associationService.getByFfbbName(dto.getAssoName()).orElseThrow());
        team.setCategory(this.categoryService.getById(dto.getCategoryId()).orElseThrow());

        return team;
    }
}
