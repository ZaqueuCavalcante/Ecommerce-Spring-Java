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

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.CategoryDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.CategoryRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ProductRepository;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.DatabaseException;
import br.com.zaqueucavalcante.ecommercespringjava.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> entity = productRepository.findById(id);
		return entity.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Page<Product> search(String productName, List<Long> categories, Integer pageNumber, Integer entitiesPerPage,
			String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(pageNumber, entitiesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categoryList = categoryRepository.findAllById(categories);
		return productRepository.findDistinctByNameContainingAndCategoriesIn(productName, categoryList, pageRequest);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Category insert(Category category) {
		category.setId(null);
		return repository.save(category);
	}
	
	public Category fromDTO(CategoryDTO categoryDTO) {
		return new Category(categoryDTO.getId(), categoryDTO.getName());
	}
	
//	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	public void delete(Long id) {
//		try {
//			repository.deleteById(id);
//		} catch (EmptyResultDataAccessException e) {
//			throw new ResourceNotFoundException(id);
//		} catch (DataIntegrityViolationException e) {
//			throw new DatabaseException(e.getMessage());
//		}
//	}
//
//	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	public Category update(Long id, Category updatedCategory) {
//		try {
//			Category category = repository.getOne(id);
//			updateCategory(category, updatedCategory);
//			return repository.save(category);
//		} catch (EntityNotFoundException e) {
//			throw new ResourceNotFoundException(id);
//		}
//	}
//	
//	private void updateCategory(Category category, Category updatedCategory) {
//		category.setName(updatedCategory.getName());
//	}
}
