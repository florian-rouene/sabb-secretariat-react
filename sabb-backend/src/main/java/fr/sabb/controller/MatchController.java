package fr.sabb.controller;

import fr.sabb.business.MatchFillerBusiness;
import fr.sabb.data.converter.MatchConverter;
import fr.sabb.data.dto.MatchDto;
import fr.sabb.data.object.Match;
import fr.sabb.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/matchs")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchFillerBusiness matchFillerBusiness;

    @Autowired
    private MatchConverter matchConverter;

    @GetMapping
    public List<MatchDto> getMatchs() {
        return getAllMatchsSorted();
    }

    @PostMapping("/reload")
    public List<MatchDto> getMatchsAfterReload() throws Exception {
        matchFillerBusiness.reloadGameFromFFBBForAllTeam();
        return getAllMatchsSorted();
    }

    private List<MatchDto> getAllMatchsSorted() {
        return this.matchService.getAll().stream().map(matchConverter::convertToDto).sorted((Comparator.comparing(Match::getMatchDate))).toList();
    }
}
