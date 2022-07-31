package fr.sabb.data.dto;

import fr.sabb.data.object.Match;

public class MatchDto extends Match {

    private int teamId;

    public MatchDto(Match match) {
        super();
        super.setId(match.getId());
        super.setHome(match.isHome());
        super.setOpponent(match.getOpponent());
        super.setMatchDate(match.getMatchDate());
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
