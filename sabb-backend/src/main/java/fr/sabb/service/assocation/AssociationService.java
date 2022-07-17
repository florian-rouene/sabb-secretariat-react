package fr.sabb.service.assocation;

import fr.sabb.data.object.Association;
import fr.sabb.service.SabbObjectService;

import java.util.List;

public interface AssociationService extends SabbObjectService<Association>{

	List<Association> getAllActive();
}
