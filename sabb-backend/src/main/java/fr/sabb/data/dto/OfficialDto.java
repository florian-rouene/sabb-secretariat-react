package fr.sabb.data.dto;

import fr.sabb.data.object.Official;
import fr.sabb.data.object.Team;

import java.sql.Timestamp;

public class OfficialDto extends Official {

    private int teamId;

    private String opponent;

    private Timestamp matchDate;

    public OfficialDto(Official official) {
        super();
        super.setId(official.getId());
        this.teamId = official.getMatch().getTeam().getId();
        this.opponent = official.getMatch().getOpponent();
        this.matchDate = official.getMatch().getMatchDate();
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Timestamp getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Timestamp matchDate) {
        this.matchDate = matchDate;
    }
}
