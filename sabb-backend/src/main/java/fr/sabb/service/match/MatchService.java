package fr.sabb.service.match;

import fr.sabb.data.object.Match;
import fr.sabb.data.object.Team;
import fr.sabb.service.SabbObjectService;

import java.util.List;
import java.util.stream.Stream;

public interface MatchService  extends SabbObjectService<Match>{

	
	Match getMatch(Match match);
	
	List<Match> getAllExt();
	
	List<Match> getAllMatchByTeam(Team team);
	
	List<Match> getAllExtMatchByTeam(Team team);

	List<Match> getAllMatchByWeekOfYear(int year, int weekOfYear);

	Stream<Match> getAllCTCMatchForCurrentSeasonByWeekOfYear(int year, int weekOfYear);
	
	Stream<Match> getAllHomeMainMatch();

	Match getExtRencontreByOpponent(String opponent, Team team);
}
