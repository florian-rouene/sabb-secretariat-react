package fr.sabb.data.mapper;

import fr.sabb.data.object.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper extends SabbMapper<Category> {

	@Select("SELECT * FROM sabb.category ORDER BY id")
	List<Category> getAll();
	
	@Insert("INSERT INTO sabb.category(name,autobind) VAlUES(#{name}, #{autobind})")
	void insert(Category category);
	
	@Delete("DELETE FROM sabb.category WHERE id=#{id}")
	void delete(Category category);
	
	@Update("UPDATE sabb.category SET name=#{name}, autobind=#{autobind} WHERE id=#{id}")
	void update(Category category);
}
