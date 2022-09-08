package fr.sabb.data.mapper;

import fr.sabb.data.object.Licensee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LicenseeMapper extends SabbMapper<Licensee> {

	@Select("SELECT * FROM sabb.licensee ORDER BY id")
	@Results({ @Result(property = "team", column = "id_team", one = @One(select = "getTeam")),
			@Result(property = "category", column = "id_category", one = @One(select = "getCategory")),
			@Result(property = "association", column = "id_association", one = @One(select = "getAssociation")),
			@Result(property="dateOfBirth", column="date_of_birth"),
			@Result(property = "numLicensee", column = "num_licensee"),})
	List<Licensee> getAll();
	
	@Select("SELECT * FROM sabb.licensee WHERE num_licensee=#{numLicensee}")
	@Results({ @Result(property = "team", column = "id_team", one = @One(select = "getTeam")),
			@Result(property = "category", column = "id_category", one = @One(select = "getCategory")) })
	Licensee getLicenseeByNum(String numLicensee);

	@Insert("INSERT INTO sabb.licensee(id_team, id_category, num_licensee, name, firstname, phone, mail, adress, date_of_birth, sex, id_association) VAlUES(#{team.id}, #{category.id}, #{numLicensee},  #{name}, #{firstname}, #{phone}, #{mail}, #{adress}, #{dateOfBirth}, #{sex}, #{association.id})")
	void insert(Licensee licensee);

	@Update("UPDATE sabb.licensee SET id_category=#{category.id}, id_team=#{team.id}, num_licensee=#{numLicensee}, name=#{name}, firstname=#{firstname}, phone=#{phone}, mail=#{mail}, adress=#{adress}, sex=#{sex}, id_association=#{association.id} WHERE id=#{id}")
	void update(Licensee licensee);
}
