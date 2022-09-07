package fr.sabb.controller;

import fr.sabb.data.converter.OfficialConverter;
import fr.sabb.data.dto.OfficialDto;
import fr.sabb.data.object.Match;
import fr.sabb.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public List<OfficialDto> getOfficialsForWeekend() {

            return getAllMatchsSorted();

    }

    private List<OfficialDto> getAllMatchsSorted() {
        return this.matchService.getAllHomeMainMatch().
                map(officialConverter::convertMatchToOfficialDto).sorted((Comparator.comparing(OfficialDto::getMatchDate))).toList();
    }
}
