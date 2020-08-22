package br.com.zaqueucavalcante.ecommercespringjava.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ProductDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;
import br.com.zaqueucavalcante.ecommercespringjava.resources.utils.URL;
import br.com.zaqueucavalcante.ecommercespringjava.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product Product = productService.findById(id);
		return ResponseEntity.ok().body(Product);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber, 
			@RequestParam(value = "entitiesPerPage", defaultValue = "24") Integer entitiesPerPage, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		String productName = URL.decodeParam(name);
		List<Long> categoriesList = URL.stringToLongList(categories);
		Page<Product> productPage = productService.search(productName, categoriesList, pageNumber, entitiesPerPage, direction, orderBy);
		Page<ProductDTO> productDTOPage = productPage.map(product -> new ProductDTO(product));
		return ResponseEntity.ok().body(productDTOPage);
	}
	
//	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	@PostMapping
//	public ResponseEntity<Category> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
//		Category category = productService.fromDTO(categoryDTO);
//		Category newCategory = productService.insert(category);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCategory.getId()).toUri();
//		return ResponseEntity.created(uri).body(newCategory);
//	}
//	
//	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Void> delete(@PathVariable Long id) {
//		productService.delete(id);
//		return ResponseEntity.noContent().build();
//	}
//	
//	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	@PutMapping(value = "/{id}")
//	public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
//		Category category = productService.fromDTO(categoryDTO);
//		Category updatedCategory = productService.update(id, category);
//		return ResponseEntity.ok().body(updatedCategory);
//	}
}
