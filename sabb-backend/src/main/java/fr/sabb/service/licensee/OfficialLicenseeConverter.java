package fr.sabb.service.licensee;

import fr.sabb.data.object.Licensee;
import fr.sabb.data.object.OfficialLicensee;
import fr.sabb.service.official.OfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficialLicenseeConverter {
	
	@Autowired
	private OfficialService officialService;

	public OfficialLicensee convertLicensee (Licensee licensee) {
		return new OfficialLicensee(licensee, this.officialService.countLicenseeOfficialNumber(licensee), this.officialService.countScoreOfficial(licensee));
	}
}
