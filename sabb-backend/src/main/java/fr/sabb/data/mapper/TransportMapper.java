package fr.sabb.data.mapper;

import fr.sabb.data.object.Transport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransportMapper  extends SabbMapper<Transport> {

	@Select("SELECT * FROM sabb.transport ORDER BY id")
	@Results(value = { 
			@Result(property = "match", column = "id_match", one = @One(select = "getMatchById")),
			@Result(property = "licensee1", column = "id_licensee_1", one = @One(select = "getLicensee")),
			@Result(property = "licensee2", column = "id_licensee_2", one = @One(select = "getLicensee")),
			@Result(property = "licensee3", column = "id_licensee_3", one = @One(select = "getLicensee"))
			})
	List<Transport> getAll();

	@Insert("INSERT INTO sabb.transport(id_match,id_licensee_1,id_licensee_2,id_licensee_3) VAlUES(#{match.id}, #{licensee1.id},#{licensee2.id},#{licensee3.id})")
	void insert(Transport transport);

	@Delete("DELETE FROM sabb.transport WHERE id=#{id}")
	void delete(Transport transport);

	@Update("UPDATE sabb.transport SET id_match=#{match.id}, id_licensee_1=#{licensee1.id}, id_licensee_2=#{licensee2.id},id_licensee_3=#{licensee3.id} WHERE id=#{id}")
	void update(Transport transport);

}
