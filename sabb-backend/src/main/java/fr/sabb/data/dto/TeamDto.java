package fr.sabb.data.dto;

import fr.sabb.data.object.Team;

public class TeamDto extends Team {
    private String assoName;
    private int categoryId;

    public TeamDto(){
        super();
    }

    public TeamDto(Team team) {
        super();
        super.setId(team.getId());
        super.setName(team.getName());
        super.setFfbbUniqueId(team.getFfbbUniqueId());
        super.setSort(team.getSort());
        super.setCtc(team.isCtc());
        super.setExcelReference(team.getExcelReference());
        super.setExcelReferenceCtc(team.getExcelReferenceCtc());
        super.setRefereeReplacmentLabel(team.getRefereeReplacmentLabel());
        super.setHasOfficialReferee(team.isHasOfficialReferee());
        super.setSex(team.getSex());
    }

    public String getAssoName() {
        return assoName;
    }

    public void setAssoName(String assoName) {
        this.assoName = assoName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
