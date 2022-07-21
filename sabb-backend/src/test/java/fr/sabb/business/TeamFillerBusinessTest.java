package fr.sabb.business;

import fr.sabb.data.dto.FfbbTeamDto;
import org.jsoup.HttpStatusException;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public class TeamFillerBusinessTest {

    TeamFillerBusiness teamFillerBusiness = new TeamFillerBusiness();

    @Test
    public void testLoadTeam404() {

        assertThrows(HttpStatusException.class, () -> {
            teamFillerBusiness.getFFBBTeamForAssociation("Test");
        }, "404 was expected");
    }

    @Test
    public void testLoadTeamSabb() {

        List<FfbbTeamDto> ffbbTeamDtoList = null;
        try {
            ffbbTeamDtoList = teamFillerBusiness.getFFBBTeamForAssociation("SAINT ANDRE BASKET BALL");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception non attendue");
        }

        Assert.notNull(ffbbTeamDtoList,"result is null");
    }
}
