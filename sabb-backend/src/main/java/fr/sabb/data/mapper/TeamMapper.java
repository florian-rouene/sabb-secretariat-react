package fr.sabb.data.mapper;

import fr.sabb.data.object.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper extends SabbMapper<Team> {

	@Select("SELECT * FROM sabb.team ORDER BY id")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"),
			@Result(property = "association", column = "id_association", one = @One(select = "getAssociation")),
			@Result(property = "category", column = "id_category", one = @One(select = "getCategory")),
			@Result(property = "ffbbUniqueId", column = "ffbb_unique_id"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "excelReference", column = "excel_reference"),
			@Result(property = "excelReferenceCtc", column = "excel_reference_ctc"),
			@Result(property = "refereeReplacmentLabel", column = "referee_replacment_label"),
			@Result(property = "hasOfficialReferee", column = "has_official_referee")})
	List<Team> getAll();

	@Insert("INSERT INTO sabb.team(name,id_category,id_association, ffbb_unique_id,sort, sex, ctc, excel_reference, excel_reference_ctc, referee_replacment_label, has_official_referee) VAlUES(#{name}, #{category.id},#{association.id}, #{ffbbUniqueId}, #{sort}, #{sex}, #{ctc}, #{excelReference}, #{excelReferenceCtc}, #{refereeReplacmentLabel}, #{hasOfficialReferee})")
	void insert(Team team);

	@Delete("DELETE FROM sabb.team WHERE id=#{id}")
	void delete(Team team);

	@Update("UPDATE sabb.team SET name=#{name}, id_category=#{category.id},id_association=#{association.id}, ffbb_unique_id=#{ffbbUniqueId}, sort=#{sort}, sex=#{sex}, ctc=#{ctc}, excel_reference=#{excelReference}, excel_reference_ctc=#{excelReferenceCtc}, referee_replacment_label=#{refereeReplacmentLabel}, has_official_referee=#{hasOfficialReferee} WHERE id=#{id}")
	void update(Team team);

}
