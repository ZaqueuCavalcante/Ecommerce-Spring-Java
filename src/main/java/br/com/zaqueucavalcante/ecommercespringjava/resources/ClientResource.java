package br.com.zaqueucavalcante.ecommercespringjava.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> clientsList = service.findAll();
		return ResponseEntity.ok().body(clientsList);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = service.findById(id);
		return ResponseEntity.ok().body(client);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client client) {
		Client newClient = service.insert(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient.getId()).toUri();
		return ResponseEntity.created(uri).body(newClient);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PutMapping(value = "/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
		Client updatedClient = service.update(id, client);
		return ResponseEntity.ok().body(updatedClient);
	}
}
