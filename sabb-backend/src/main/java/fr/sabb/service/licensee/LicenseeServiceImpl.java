package fr.sabb.service.licensee;

import fr.sabb.exception.ValidationException;
import fr.sabb.data.mapper.LicenseeMapper;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.object.*;
import fr.sabb.service.SabbObjectServiceImpl;
import fr.sabb.service.category.CategoryService;
import fr.sabb.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LicenseeServiceImpl extends SabbObjectServiceImpl<Licensee> implements LicenseeService {

    @Autowired
    private LicenseeMapper mapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private OfficialLicenseeConverter officialConverter;

    @Override
    public SabbMapper<Licensee> getMapper() {
        return mapper;
    }

    @Override
    public void fillDBWithCsvFile(Association association, String fileName) {
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileName))) {
            bufferReader.lines().filter(s -> !s.isEmpty() && !s.startsWith(";") && !s.contains("cd_org"))
                    .forEach(l -> readLicenseeLine(l, association));
        } catch (IOException ex) {
            System.out.println("problème lors du parsage");
        }
    }

    @Override
    public void fillDBWithCsvFile(Association association, MultipartFile file) {
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            bufferReader.lines().filter(s -> !s.isEmpty() && !s.startsWith(";") && !s.contains("cd_org"))
                    .forEach(l -> readLicenseeLine(l, association));
        } catch (IOException ex) {
            System.out.println("problème lors du parsage");
        }
    }

    private void readLicenseeLine(String line, Association association) {
        Licensee licensee = new Licensee();
        String[] fields = line.split(";");
        licensee.setName(fields[5]);
        licensee.setFirstname(fields[6]);
        licensee.setAdress(String.format("%s;%s", fields[20], fields[17]));
        licensee.setSex(fields[9]);
        licensee.setNumLicensee(fields[10]);
        licensee.setPhone(getPhone(fields[24], fields[25], fields[26]));
        licensee.setMail(fields[23]);
        licensee.setCategory(this.getCategory(fields[12], licensee.getSex()));
        licensee.setTeam(getTeam(licensee.getCategory(), licensee.getSex(), association, licensee.getNumLicensee()));
        licensee.setDateOfBirth(getDateOfBirth(fields[7]));
        licensee.setAssociation(association);
        this.upsert(licensee);
        System.out.println("Upsert du licencié : " + licensee);
    }

    private Team getTeam(Category category, String sex, Association association, String numLicence) {
        if (category != null && association.isMain() && category.isAutobind()) {
            Licensee oldLicensee = getAll().stream().filter(l -> l.getNumLicensee().equals(numLicence)).findFirst().orElse(null);
            if (oldLicensee == null || oldLicensee.getTeam() == null) {
                return teamService.getFirstTeamForCategoryAndSex(category.getId(), sex);
            }
            return oldLicensee.getTeam();
        }
        return null;
    }

    private Timestamp getDateOfBirth(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("PB PARSE !" + e);
        }

        assert date != null;
        return new Timestamp(date.getTime());
    }

    private String getPhone(String phoneDom, String phoneWork, String phoneMobile) {
        List<String> numPhone = new ArrayList<>();
        if (!phoneMobile.isEmpty()) {
            numPhone.add(phoneMobile.replace(" ", "").replaceAll("\\.", ""));
        }
        if (!phoneWork.isEmpty()) {
            numPhone.add(phoneWork.replace(" ", "").replaceAll("\\.", ""));
        }
        if (!phoneDom.isEmpty()) {
            numPhone.add(phoneDom.replace(" ", "").replaceAll("\\.", ""));
        }
        return numPhone.stream().filter(n -> !n.startsWith("02")).findFirst().orElse(null);
    }

    private Category getCategory(String categoryStr, String sex) {
        return this.categoryService.getCategoryBySubCategoryName(categoryStr, sex);
    }

    private void upsert(Licensee licensee) {
        Licensee oldLicensee = mapper.getLicenseeByNum(licensee.getNumLicensee());
        if (oldLicensee != null) {
            licensee.setId(oldLicensee.getId());
        }
        try {
            updateOrInsert(licensee);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Licensee> getAllByTeam(Team team) {
        return this.getAll().stream().filter(l -> l.getTeam() != null).filter(l -> l.getTeam().getId() == team.getId()).collect(Collectors.toList());
    }

    @Override
    public void updateOrInsert(Licensee licensee) throws ValidationException {
        if (licensee.getTeam() != null && licensee.getCategory() == null) {
            licensee.setCategory(licensee.getTeam().getCategory());
        }
        if (licensee.getTeam() != null && licensee.getSex() == null) {
            licensee.setSex(licensee.getTeam().getSex());
        }
        super.updateOrInsert(licensee);
    }

    @Override
    public List<OfficialLicensee> getAllOfficialLicenseeByTeam(Team team) {
        return this.getAllByTeam(team).stream().map(this.officialConverter::convertLicensee).toList();
    }

    @Override
    public List<OfficialLicensee> getAllMainOfficialLicenseeSorted() {
        return this.getAll().stream()
                .filter(Objects::nonNull)
                .filter(l -> Objects.nonNull(l.getAssociation()))
                .filter(l -> l.getAssociation().isMain())
                .sorted(Comparator.comparing(Licensee::getName))
                .map(this.officialConverter::convertLicensee)
                .collect(Collectors.toList());
    }
}
