package fr.sabb.service.assocation;

import fr.sabb.data.mapper.AssociationMapper;
import fr.sabb.data.mapper.SabbMapper;
import fr.sabb.data.object.Association;
import fr.sabb.service.SabbObjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
		return this.getAll();
	}

	@Override
	public Optional<Association> getByFfbbName(String name) {
		return this.getAll().stream().filter(s -> s.getNameFfbb().equals(name)).findFirst();
	}

}
