package fr.sabb.service.team;

import fr.sabb.data.dto.TeamDto;
import fr.sabb.data.object.Team;
import fr.sabb.service.SabbObjectService;

import java.util.List;

public interface TeamService extends SabbObjectService<Team>{

	Team getFirstTeamForCategoryAndSex(int idCategory, String sex);

	List<TeamDto> getAllDto();
}
