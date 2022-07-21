package fr.sabb.business;

import fr.sabb.data.dto.FfbbTeamDto;
import fr.sabb.exception.ValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
public class TeamFillerBusiness {

    public List<FfbbTeamDto> getFFBBTeamForAssociation(String ffbbName) throws ValidationException, IOException {

        if (Objects.isNull(ffbbName)) {
            throw new ValidationException("Definissez au préalable l'identifiant ffbb");
        }


        String ffbbCode = this.getFfbbCodeFromAssociationName(ffbbName);

        if (Objects.isNull(ffbbCode)) {
            throw new ValidationException("Club non trouvé");
        }

        Document doc = Jsoup.connect("https://resultats.ffbb.com/championnat/equipe/" + ffbbCode + ".html").get();


        return doc.getElementById("idTableClub").getElementsByTag("option").stream().map(this::convertToFfbbTeam).filter(Objects::nonNull).toList();
    }

    private FfbbTeamDto convertToFfbbTeam(Element matchElements) {

        FfbbTeamDto ffbbTeam = new FfbbTeamDto();
        ffbbTeam.setFfbbUniqueId(matchElements.attr("value"));
        mapNameAndPoule(matchElements, ffbbTeam);
        return ffbbTeam;
    }

    private void mapNameAndPoule(Element e, FfbbTeamDto ffbbTeamDto) {
        if (e.text().contains("(")) {
            ffbbTeamDto.setName(e.text().substring(0, e.text().indexOf("(")));
            ffbbTeamDto.setPoule(e.text().substring(e.text().indexOf("(") + 1, e.text().indexOf(")")));
        } else {
            ffbbTeamDto.setName(e.text());
        }
    }

    private String extractTeamFfbbUniqueId(String href) {
        return href.substring(href.indexOf("championnat/") + 12, href.indexOf(".html"));
    }

    private String getFfbbCodeFromAssociationName(String ffbbName) throws IOException {
        Document doc = Jsoup.connect("https://resultats.ffbb.com/organisation/listeorganismes/816.html").get();

        return Stream.of(doc.getElementsByClass("no-altern-2"),
                        doc.getElementsByClass("altern-2"))
                .flatMap(Collection::stream).filter(e -> this.isGoodFfbbName(e, ffbbName)).findFirst().map(this::extractFfbbCode).orElse(null);
    }

    private boolean isGoodFfbbName(Element e, String ffbbName) {
        return e.text().contains(ffbbName);
    }

    private String extractFfbbCode(Element e) {
        return e.getElementsByAttribute("href").eachAttr("href").stream().map(this::extractClubFfbbCode).findFirst().orElse(null);
    }

    private String extractClubFfbbCode(String href) {
        return href.substring(href.indexOf("../") + 3, href.indexOf(".html"));
    }
}
