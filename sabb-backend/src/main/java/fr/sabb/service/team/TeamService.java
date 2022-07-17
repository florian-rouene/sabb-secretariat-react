package fr.sabb.service.team;

import fr.sabb.data.object.Team;
import fr.sabb.service.SabbObjectService;

import java.util.List;

public interface TeamService extends SabbObjectService<Team>{

	List<Team> getAllActiveForCurrentSeason();

	Team getFirstTeamForCategoryAndSex(int idCategory, String sex);

}
