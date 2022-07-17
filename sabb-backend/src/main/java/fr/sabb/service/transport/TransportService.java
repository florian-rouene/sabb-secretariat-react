package fr.sabb.service.transport;

import fr.sabb.data.object.Match;
import fr.sabb.data.object.Transport;
import fr.sabb.service.SabbObjectService;

public interface TransportService extends SabbObjectService<Transport>{

	Transport getTransportOrBarByMatch(Match match);
	
	void unvalidAllTransportForCurrentSeason();
}
