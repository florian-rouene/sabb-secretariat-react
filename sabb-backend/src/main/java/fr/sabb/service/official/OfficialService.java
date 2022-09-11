package fr.sabb.service.official;

import fr.sabb.data.object.Licensee;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Official;
import fr.sabb.service.SabbObjectService;

public interface OfficialService  extends SabbObjectService<Official>{

	Official getOfficialFromMatch(Match match);

	Official getOfficialFromMatchId(int matchId);

	int countLicenseeOfficialNumber(Licensee licensee);
	
	int countScoreOfficial(Licensee licensee);
}
