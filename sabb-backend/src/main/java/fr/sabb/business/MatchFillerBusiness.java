package fr.sabb.business;


import fr.sabb.data.object.Association;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Team;
import fr.sabb.exception.ValidationException;
import fr.sabb.service.match.MatchService;
import fr.sabb.service.team.TeamService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MatchFillerBusiness {

    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    private int index = 0;
    private String dateStr = null;
    private Team team;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm");

    public void reloadGameFromFFBB(Team team) throws Exception {

        this.team = team;
        if (this.team.getFfbbUniqueId() == null) {
            throw new ValidationException("Definissez au préalable l'identifiant unique ffbb");
        }
        Document doc = Jsoup.connect("http://resultats.ffbb.com/championnat/equipe/division/" + team.getFfbbUniqueId() + ".html").get();

        doc.getElementsByClass("altern-2").stream().forEach(this::generateMatchFromFFBBElements);
        doc.getElementsByClass("no-altern-2").stream().forEach(this::generateMatchFromFFBBElements);

        System.out.println("Matchs rechargés : " + team.toString());
    }

    public void reloadGameFromFFBBForAllTeam() throws Exception {
        teamService.getAllActiveForCurrentSeason().stream().filter(m -> Objects.nonNull(m.getFfbbUniqueId())).forEach(t -> {
            try {
                reloadGameFromFFBB(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void generateMatchFromFFBBElements(Element matchElements) {

        if (matchElements.childNodeSize() != 7) {
            return;
        }
        Match match = new Match();
        match.setTeam(this.team);
        index = 0;
        dateStr = null;
        matchElements.getAllElements().forEach(e -> {
            switch (index) {
                case 1:
                    match.setIdFFBB(Integer.parseInt(e.text()));
                    break;
                case 2:
                    dateStr = e.text();
                    break;
                case 3:
                    match.setMatchDate(Timestamp.valueOf(LocalDateTime.parse(dateStr + e.text(), formatter)));
                    break;
                case 4:
                    if (e.text().contains(team.getAssociation().getNameFfbb()) || e.text().contains(team.getAssociation().getNameFfbbCtc())) {
                        match.setHome(true);
                    } else {
                        match.setHome(false);
                        match.setOpponent(e.text());
                    }
                    break;
                case 6:
                    if (match.isHome()) {
                        match.setOpponent(e.text());
                    }
                    break;
                case 9:
                    if (match.isHome() && doesLocationSwitch(e, team.getAssociation())) {
                        match.setLocationSwitched(true);
                    }
                    break;
                default:
                    break;
            }
            index++;
        });
        try {
            matchService.updateOrInsert(match);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        try {
            // sabbCalendarBusiness.addMatchToCalendar(match);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean doesLocationSwitch(Element e, Association association) {
        return Stream.of(association.getFfbbLocation(), association.getFfbbLocationBis()).filter(Objects::nonNull).noneMatch(l -> String.valueOf(e).contains(l));
    }
}
