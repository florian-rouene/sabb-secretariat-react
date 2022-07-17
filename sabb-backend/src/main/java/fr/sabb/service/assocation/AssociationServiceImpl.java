package fr.sabb.service.assocation;

import fr.sabb.data.mapper.AssociationMapper;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.object.Association;
import fr.sabb.service.SabbObjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociationServiceImpl extends SabbObjectServiceImpl<Association> implements AssociationService {

	@Autowired
	private AssociationMapper mapper;

	@Override
	public SabbMapper<Association> getMapper() {
		return mapper;
	}

	@Override
	public List<Association> getAllActive() {
		return getAll().stream().filter(Association::isActive).collect(Collectors.toList());
	}

}
