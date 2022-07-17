package fr.sabb.data.mapper;

import fr.sabb.data.object.SubCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubCategoryMapper extends SabbMapper<SubCategory> {

	@Select("SELECT * FROM sabb.sub_category ORDER BY id")
	@Results({ @Result(property = "category", column = "id_category", one = @One(select = "getCategory")) })
	List<SubCategory> getAll();

	@Insert("INSERT INTO sabb.sub_category(name,id_category,sex) VAlUES(#{name}, #{category.id}, #{sex})")
	void insert(SubCategory subCategory);

	@Delete("DELETE FROM sabb.sub_category WHERE id=#{id}")
	void delete(SubCategory subCategory);

	@Update("UPDATE sabb.sub_category SET name=#{name}, id_category=#{category.id}, sex=#{sex} WHERE id=#{id}")
	void update(SubCategory subCategory);
}
