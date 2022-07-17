package fr.sabb.data.mapper;

import fr.sabb.data.object.Season;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeasonMapper extends SabbMapper<Season> {

	@Select("SELECT * FROM sabb.season ORDER BY id")
	List<Season> getAll();
	
	@Insert("INSERT INTO sabb.season(name,active,reference_year) VAlUES(#{name}, #{active}, #{referenceYear})")
	void insert(Season season);
	
	@Delete("DELETE FROM sabb.season WHERE id=#{id}")
	void delete(Season season);
	
	@Update("UPDATE sabb.season SET name=#{name}, active=#{active}, reference_year=#{referenceYear} WHERE id=#{id}")
	void update(Season season);
}
