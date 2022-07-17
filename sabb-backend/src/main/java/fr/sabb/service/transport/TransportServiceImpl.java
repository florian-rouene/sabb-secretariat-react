package fr.sabb.service.transport;

import fr.sabb.exception.ValidationException;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.mapper.TransportMapper;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Season;
import fr.sabb.data.object.Transport;
import fr.sabb.service.SabbObjectServiceImpl;
import fr.sabb.service.match.MatchService;
import fr.sabb.service.season.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportServiceImpl extends SabbObjectServiceImpl<Transport>implements TransportService {

	@Autowired
	private TransportMapper mapper;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private SeasonService seasonService;
	
	@Override
	public SabbMapper<Transport> getMapper() {
		return mapper;
	}
	
	@Override
	public List<Transport> getAll() {
		return getMapper().getAll().stream().filter(t -> t.getMatch() != null).collect(Collectors.toList());
	}

	@Override
	public Transport getTransportOrBarByMatch(Match match) {
		if (match.isHome()) {
			Match extMatch = matchService.getExtRencontreByOpponent(match.getOpponent(), match.getTeam());
			if (extMatch != null) {
				return this.getAll().stream().filter(t -> t.getMatch().getId() == extMatch.getId()).findFirst().orElse(null);	
			}
			return null;
		} else {
			return this.getAll().stream().filter(t -> t.getMatch().getId() == match.getId()).findFirst().orElse(null);
		}
		
	}

	@Override
	public void unvalidAllTransportForCurrentSeason() {
		Season currentSeason = this.seasonService.getCurrentSeason();
		
		this.getAll().stream().filter(t -> t.getMatch().getTeam().getSeason().equals(currentSeason)).forEach(this::unvalidTransport);
		
	}
	
	private void unvalidTransport(Transport transport) {
		transport.setMatch(null);
		this.mapper.update(transport);
	}
	
	@Override
	public void updateOrInsert(Transport transport) throws ValidationException {
		Transport existingTransport = this.getTransportOrBarByMatch(transport.getMatch());
		if (existingTransport != null) {
			transport.setId(existingTransport.getId());
		}
		super.updateOrInsert(transport);
	}

}
