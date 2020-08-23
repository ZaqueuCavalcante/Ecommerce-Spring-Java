package br.com.zaqueucavalcante.ecommercespringjava.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.CategoryDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;
import br.com.zaqueucavalcante.ecommercespringjava.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
		List<Order> ordersList = orderService.findAll();
		return ResponseEntity.ok().body(ordersList);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order order = orderService.findById(id);
		return ResponseEntity.ok().body(order);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping
	public ResponseEntity<Order> insert(@Valid @RequestBody Order order) {
		Order newOrder = orderService.insert(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newOrder);
	}
}
