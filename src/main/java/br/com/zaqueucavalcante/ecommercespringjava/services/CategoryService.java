package br.com.zaqueucavalcante.ecommercespringjava.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.CategoryRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> entity = repository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Page<Category> findPage(Integer pageNumber, Integer entitiesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(pageNumber, entitiesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Category insert(Category category) {
		category.setId(null);
		return repository.save(category);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Category update(Long id, Category updatedCategory) {
		try {
			Category category = repository.getOne(id);
			updateCategory(category, updatedCategory);
			return repository.save(category);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	private void updateCategory(Category category, Category updatedCategory) {
		category.setName(updatedCategory.getName());
	}
}
