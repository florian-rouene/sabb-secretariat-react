package fr.sabb.data.mapper;

import fr.sabb.data.object.Association;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssociationMapper extends SabbMapper<Association> {

	@Select("SELECT * FROM sabb.association ORDER BY id")
	@Results(value= {
		@Result(property="nameFfbb", column="name_ffbb"),
		@Result(property="nameFfbbCtc", column="name_ffbb_ctc"),
		@Result(property="ffbbLocation", column="ffbb_location"),
		@Result(property="ffbbLocationBis", column="ffbb_location_bis"),
	})
	List<Association> getAll();
	
	@Insert("INSERT INTO sabb.association(name,main,name_ffbb, name_ffbb_ctc, ffbb_location, ffbb_location_bis) VAlUES(#{name},#{main}, #{nameFfbb}, #{nameFfbbCtc}, #{ffbbLocation}, #{ffbbLocationBis})")
	void insert(Association association);
	
	@Delete("DELETE FROM sabb.association WHERE id=#{id}")
	void delete(Association association);
	
	@Update("UPDATE sabb.association SET name=#{name}, main=#{main}, name_ffbb=#{nameFfbb}, name_ffbb_ctc=#{nameFfbbCtc}, ffbb_location=#{ffbbLocation}, ffbb_location_bis=#{ffbbLocationBis} WHERE id=#{id}")
	void update(Association association);
}
