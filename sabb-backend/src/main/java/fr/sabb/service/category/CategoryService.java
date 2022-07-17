package fr.sabb.service.category;

import fr.sabb.data.object.Category;
import fr.sabb.service.SabbObjectService;

import java.util.List;

public interface CategoryService extends SabbObjectService<Category> {

	List<Category> getAllActive();

	Category getCategoryBySubCategoryName(String subCategoryName, String sex);
}
