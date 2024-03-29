package fr.sabb.service.licensee;

import fr.sabb.data.object.Association;
import fr.sabb.data.object.Licensee;
import fr.sabb.data.object.OfficialLicensee;
import fr.sabb.data.object.Team;
import fr.sabb.service.SabbObjectService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LicenseeService extends SabbObjectService<Licensee> {

	void fillDBWithCsvFile(Association association, String fileName);

	void fillDBWithCsvFile(Association association, MultipartFile file);

	List<Licensee> getAllByTeam(Team team);
	
	List<OfficialLicensee> getAllOfficialLicenseeByTeam(Team team);

	List<OfficialLicensee> getAllMainOfficialLicenseeSorted();

}
