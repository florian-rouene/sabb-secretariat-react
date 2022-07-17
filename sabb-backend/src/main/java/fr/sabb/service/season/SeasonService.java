package fr.sabb.service.season;

import fr.sabb.data.object.Season;
import fr.sabb.service.SabbObjectService;

public interface SeasonService extends SabbObjectService<Season>{

	Season getCurrentSeason();
}
