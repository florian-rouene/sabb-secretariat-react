package fr.sabb.service.subcategory;

import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.mapper.SubCategoryMapper;
import fr.sabb.data.object.SubCategory;
import fr.sabb.service.SabbObjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl extends SabbObjectServiceImpl<SubCategory> implements SubCategoryService {

	@Autowired
	private SubCategoryMapper mapper;

	@Override
	public SabbMapper<SubCategory> getMapper() {
		return mapper;
	}

}
