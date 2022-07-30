package fr.sabb.service;


import fr.sabb.data.dto.TeamDto;
import fr.sabb.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public interface SabbObjectService<T> {

	List<T> getAll();
	
	List<T> getAllWithCaching();
	
	void updateOrInsert(T t) throws ValidationException;
	
	void delete(T t);
	
	Optional<T> getById(int id);

}
