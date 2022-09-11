package fr.sabb.controller;

import fr.sabb.data.converter.OfficialConverter;
import fr.sabb.data.dto.OfficialDto;
import fr.sabb.data.dto.TeamDto;
import fr.sabb.data.object.Licensee;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Official;
import fr.sabb.data.object.Team;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.match.MatchService;
import fr.sabb.service.official.OfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/officials")
public class OfficialController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private OfficialConverter officialConverter;

    @Autowired
    private OfficialService officialService;

    @GetMapping
    public List<OfficialDto> getOfficialsForWeekend() {

            return getAllMatchsSorted();

    }

    @PutMapping("/{id}")
    public ResponseEntity updateTeam(@PathVariable int id, @RequestBody OfficialDto officialDto) throws ValidationException {
        Official official = officialService.getOfficialFromMatchId(id);

        if(officialDto.getLicenseeReferee1Id() != 0) {
            official.setLicenseeReferee1(new Licensee(officialDto.getLicenseeReferee1Id()));
        }

        if(officialDto.getLicenseeTable1Id() != 0) {
            official.setLicenseeTable1(new Licensee(officialDto.getLicenseeTable1Id()));
        }

        officialService.updateOrInsert(official);
        return ResponseEntity.ok(official);
    }

    private List<OfficialDto> getAllMatchsSorted() {
        return this.matchService.getAllHomeMainMatch().
                map(officialConverter::convertMatchToOfficialDto).sorted((Comparator.comparing(OfficialDto::getMatchDate))).toList();
    }
}
