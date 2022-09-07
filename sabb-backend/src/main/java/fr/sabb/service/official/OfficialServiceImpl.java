package fr.sabb.service.official;

import fr.sabb.exception.ValidationException;
import fr.sabb.data.mapper.OfficialMapper;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.object.Licensee;
import fr.sabb.data.object.Match;
import fr.sabb.data.object.Official;
import fr.sabb.service.SabbObjectServiceImpl;
import fr.sabb.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OfficialServiceImpl extends SabbObjectServiceImpl<Official> implements OfficialService {

    @Autowired
    private OfficialMapper mapper;

    @Override
    public SabbMapper<Official> getMapper() {
        return mapper;
    }

    @Override
    public List<Official> getAll() {
        return getMapper().getAll().stream().filter(o -> o.getMatch() != null).collect(Collectors.toList());
    }

    @Override
    public Official getOfficialFromMatch(Match match) {
        return this.getAll().stream().filter(o -> o.getMatch().getId() == match.getId()).findFirst()
                .orElse(new Official(match));
    }

    @Override
    public int countLicenseeOfficialNumber(Licensee licensee) {
        return getAllWithCaching().stream().filter(o -> isLicenseeOfficie(o, licensee)).toList()
                .size();
    }

    private boolean isLicenseeOfficie(Official official, Licensee licensee) {
        return isSameLicensee(official.getLicenseeReferee1(), licensee)
                || isSameLicensee(official.getLicenseeReferee2(), licensee)
                || isSameLicensee(official.getLicenseeTable1(), licensee)
                || isSameLicensee(official.getLicenseeTable2(), licensee);
    }

    private boolean isSameLicensee(Licensee officialLicensee, Licensee licensee) {
        return officialLicensee != null && officialLicensee.getId() == licensee.getId();
    }

    @Override
    public void updateOrInsert(Official official) throws ValidationException {
        Official existingOfficial = this.getOfficialFromMatch(official.getMatch());
        if (existingOfficial != null) {
            official.setId(existingOfficial.getId());
        }
        super.updateOrInsert(official);
    }

    @Override
    public int countScoreOfficial(Licensee licensee) {

        return getAllWithCaching().stream()
                .filter(o -> isLicenseeOfficie(o, licensee))
                .map(Official::getMatch).filter(Objects::nonNull).max(Comparator.comparing(Match::getMatchDate))
                .map(Match::getMatchDate)
                .map(DateUtils::calculateScore)
                .orElse(0);
    }


}
