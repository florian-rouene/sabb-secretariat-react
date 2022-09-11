package fr.sabb.data.dto;

import fr.sabb.data.object.Official;
import fr.sabb.data.object.Team;

import java.sql.Timestamp;

import static java.util.Objects.isNull;

public class OfficialDto extends Official {

    private int teamId;

    private String opponent;

    private Timestamp matchDate;
    
    private int licenseeTable1Id;
    
    private int licenseeTable2Id;
    
    private int licenseeReferee1Id;
    
    private int licenseeReferee2Id;

    public OfficialDto() {
        super();
    }

    public OfficialDto(Official official) {
        super();
        super.setId(official.getId());
        super.setMatch(official.getMatch());
        this.teamId = official.getMatch().getTeam().getId();
        this.opponent = official.getMatch().getOpponent();
        this.matchDate = official.getMatch().getMatchDate();
        this.licenseeTable1Id = isNull(official.getLicenseeTable1()) ? 0 : official.getLicenseeTable1().getId();
        this.licenseeTable2Id = isNull(official.getLicenseeTable2()) ? 0 : official.getLicenseeTable2().getId();
        this.licenseeReferee1Id = isNull(official.getLicenseeReferee1()) ? 0 : official.getLicenseeReferee1().getId();
        this.licenseeReferee2Id = isNull(official.getLicenseeReferee2()) ? 0 : official.getLicenseeReferee2().getId();
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

    public int getLicenseeTable1Id() {
        return licenseeTable1Id;
    }

    public void setLicenseeTable1Id(int licenseeTable1Id) {
        this.licenseeTable1Id = licenseeTable1Id;
    }

    public int getLicenseeTable2Id() {
        return licenseeTable2Id;
    }

    public void setLicenseeTable2Id(int licenseeTable2Id) {
        this.licenseeTable2Id = licenseeTable2Id;
    }

    public int getLicenseeReferee1Id() {
        return licenseeReferee1Id;
    }

    public void setLicenseeReferee1Id(int licenseeReferee1Id) {
        this.licenseeReferee1Id = licenseeReferee1Id;
    }

    public int getLicenseeReferee2Id() {
        return licenseeReferee2Id;
    }

    public void setLicenseeReferee2Id(int licenseeReferee2Id) {
        this.licenseeReferee2Id = licenseeReferee2Id;
    }
}
