package fr.sabb.data.converter;

import fr.sabb.data.dto.MatchDto;
import fr.sabb.data.dto.OfficialDto;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Official;
import fr.sabb.service.official.OfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfficialConverter {
    @Autowired
    private OfficialService officialService;

    public OfficialDto convertToDto (Official official) {

        OfficialDto dto = new OfficialDto(official);


        return dto;
    }

    public OfficialDto convertMatchToOfficialDto(Match match) {
        Official official = this.officialService.getOfficialFromMatch(match);

        return convertToDto(official);
    }
}
