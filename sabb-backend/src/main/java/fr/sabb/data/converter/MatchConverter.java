package fr.sabb.data.converter;

import fr.sabb.data.dto.MatchDto;
import fr.sabb.data.object.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchConverter {

    public MatchDto convertToDto (Match match) {

        MatchDto dto = new MatchDto(match);

        dto.setTeamId(match.getTeam().getId());

        return dto;
    }
}
