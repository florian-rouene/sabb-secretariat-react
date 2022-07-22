package fr.sabb.data.dto;

import fr.sabb.data.object.Association;
import fr.sabb.data.object.Category;
import fr.sabb.data.object.Season;
import fr.sabb.data.object.Team;

public class TeamDto extends Team {
    private String assoName;
    private int categoryId;

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
