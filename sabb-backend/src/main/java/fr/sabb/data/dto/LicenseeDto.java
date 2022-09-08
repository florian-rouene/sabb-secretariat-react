package fr.sabb.data.dto;

import fr.sabb.data.object.Licensee;

import java.util.Objects;

public class LicenseeDto extends Licensee {

    private int teamId;

    public LicenseeDto(Licensee licensee) {
        super();
        super.setId(licensee.getId());
        super.setName(licensee.getName());
        super.setFirstname(licensee.getFirstname());
        super.setSex(licensee.getSex());
        super.setNumLicensee(licensee.getNumLicensee());
        this.teamId= Objects.isNull(licensee.getTeam()) ? 0 : licensee.getTeam().getId();
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
