package br.com.zaqueucavalcante.ecommercespringjava.resources;

import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import br.com.zaqueucavalcante.ecommercespringjava.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	private final OrderService orderService;

	public OrderResource(OrderService orderService) {
		this.orderService = orderService;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		Order order = orderService.findById(id);
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping
	public ResponseEntity<Page<Order>> findPage(
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber, 
			@RequestParam(value = "entitiesPerPage", defaultValue = "24") Integer entitiesPerPage, 
			@RequestParam(value = "direction", defaultValue = "DESC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy) {
		Page<Order> orderPage = orderService.findPage(pageNumber, entitiesPerPage, direction, orderBy);
		return ResponseEntity.ok().body(orderPage);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping
	public ResponseEntity<Order> insert(@Valid @RequestBody Order order) {
		Order newOrder = orderService.insert(order);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newOrder);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PutMapping(value = "/{id}")
	public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order order) {
		Order updatedOrder = orderService.update(id, order);
		return ResponseEntity.ok().body(updatedOrder);
	}

}
