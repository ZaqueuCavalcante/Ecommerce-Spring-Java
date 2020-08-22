package br.com.zaqueucavalcante.ecommercespringjava.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.CategoryDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> categoryList = service.findAll();
		List<CategoryDTO> categoryDTOList = categoryList.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoryDTOList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category category = service.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoryDTO>> findPage(
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber, 
			@RequestParam(value = "entitiesPerPage", defaultValue = "24") Integer entitiesPerPage, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		Page<Category> categoryPage = service.findPage(pageNumber, entitiesPerPage, direction, orderBy);
		Page<CategoryDTO> categoryDTOPage = categoryPage.map(category -> new CategoryDTO(category));
		return ResponseEntity.ok().body(categoryDTOPage);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category) {
		Category newCategory = service.insert(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(uri).body(newCategory);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PutMapping(value = "/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
		Category updatedCategory = service.update(id, category);
		return ResponseEntity.ok().body(updatedCategory);
	}
}
