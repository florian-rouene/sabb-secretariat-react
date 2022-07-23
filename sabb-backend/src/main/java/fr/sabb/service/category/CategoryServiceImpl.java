package fr.sabb.service.category;

import fr.sabb.data.mapper.CategoryMapper;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.object.Category;
import fr.sabb.data.object.SubCategory;
import fr.sabb.service.SabbObjectServiceImpl;
import fr.sabb.service.subcategory.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends SabbObjectServiceImpl<Category> implements CategoryService {

	@Autowired
	private CategoryMapper mapper;

	@Autowired
	private SubCategoryService subCategoryService;

	@Override
	public SabbMapper<Category> getMapper() {
		return mapper;
	}

	@Override
	public Category getCategoryBySubCategoryName(String subCategoryName, String sex) {
		return this.subCategoryService.getAll().stream()
				.filter(c -> c.getName().equals(subCategoryName))
				.filter(s -> this.filterBySex(s, sex))
				.map(SubCategory::getCategory).findFirst().orElse(null);
	}
	
	private boolean filterBySex(SubCategory subCategory, String sex) {
		if (subCategory.getSex() == null) {
			return true;
		}
		return subCategory.getSex().equals(sex);
	}

}
