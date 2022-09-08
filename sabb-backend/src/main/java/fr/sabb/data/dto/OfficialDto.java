package fr.sabb.data.dto;

import fr.sabb.data.object.Official;
import fr.sabb.data.object.Team;

import java.sql.Timestamp;

public class OfficialDto extends Official {

    private int teamId;

    private String opponent;

    private Timestamp matchDate;
    
    private int licenseeTable1Id;
    
    private int licenseeTable2Id;
    
    private int licenseeReferee1Id;
    
    private int licenseeReferee2Id;

    public OfficialDto(Official official) {
        super();
        super.setId(official.getId());
        this.teamId = official.getMatch().getTeam().getId();
        this.opponent = official.getMatch().getOpponent();
        this.matchDate = official.getMatch().getMatchDate();
        this.licenseeTable1Id = official.getLicenseeTable1().getId();
        this.licenseeTable2Id = official.getLicenseeTable2().getId();
        this.licenseeReferee1Id = official.getLicenseeReferee1().getId();
        this.licenseeReferee2Id = official.getLicenseeReferee2().getId();
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
