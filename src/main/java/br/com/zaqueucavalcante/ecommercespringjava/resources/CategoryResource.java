package br.com.zaqueucavalcante.ecommercespringjava.resources;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.CategoryDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Category;
import br.com.zaqueucavalcante.ecommercespringjava.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	private final CategoryService service;

	public CategoryResource(CategoryService service) {
		this.service = service;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> categoryList = service.findAll();
		List<CategoryDTO> categoryDTOList = categoryList.stream().map(CategoryDTO::new).collect(Collectors.toList());
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
		Page<CategoryDTO> categoryDTOPage = categoryPage.map(CategoryDTO::new);
		return ResponseEntity.ok().body(categoryDTOPage);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Category> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = service.fromDTO(categoryDTO);
		Category newCategory = service.insert(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategory.getId()).toUri();
		return ResponseEntity.created(uri).body(newCategory);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
		Category category = service.fromDTO(categoryDTO);
		Category updatedCategory = service.update(id, category);
		return ResponseEntity.ok().body(updatedCategory);
	}

}
