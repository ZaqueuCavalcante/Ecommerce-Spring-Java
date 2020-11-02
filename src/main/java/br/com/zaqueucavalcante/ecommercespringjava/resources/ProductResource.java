package br.com.zaqueucavalcante.ecommercespringjava.resources;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientDTO;
import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ProductDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Product;
import br.com.zaqueucavalcante.ecommercespringjava.resources.utils.URL;
import br.com.zaqueucavalcante.ecommercespringjava.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	private final ProductService service;

	public ProductResource(ProductService service) {
		this.service = service;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> productList = service.findAll();
		return ResponseEntity.ok().body(productList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product Product = service.findById(id);
		return ResponseEntity.ok().body(Product);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProductDTO>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(value = "entitiesPerPage", defaultValue = "24") Integer entitiesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		String nameDecoded = URL.decodeParam(name);
		List<Long> categoriesIds = URL.stringToLongList(categories);
		Page<Product> productPage = service.findPage(nameDecoded, categoriesIds, pageNumber, entitiesPerPage, direction, orderBy);
		Page<ProductDTO> productDTOPage = productPage.map(product -> new ProductDTO(product));
		return ResponseEntity.ok().body(productDTOPage);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Product> insert(@Valid @RequestBody Product product) {
		Product newProduct = service.insert(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newProduct.getId()).toUri();
		return ResponseEntity.created(uri).body(newProduct);
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
	public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
		Product updatedProduct = service.update(id, product);
		return ResponseEntity.ok().body(updatedProduct);
	}

}
