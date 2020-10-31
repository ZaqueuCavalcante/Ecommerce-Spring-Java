package br.com.zaqueucavalcante.ecommercespringjava.resources;

import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientDTO;
import br.com.zaqueucavalcante.ecommercespringjava.datatransferobjects.ClientFullDTO;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	private final ClientService service;

	public ClientResource(ClientService service) {
		this.service = service;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
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

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClientDTO>> findPage(
			@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(value = "entitiesPerPage", defaultValue = "24") Integer entitiesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		Page<Client> clientPage = service.findPage(pageNumber, entitiesPerPage, direction, orderBy);
		Page<ClientDTO> clientDTOPage = clientPage.map(client -> new ClientDTO(client));
		return ResponseEntity.ok().body(clientDTOPage);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PostMapping
	public ResponseEntity<Client> insert(@Valid @RequestBody ClientFullDTO clientFullDTO) {
		Client client = service.fromDTO(clientFullDTO);
		Client newClient = service.insert(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newClient.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newClient);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@PutMapping(value = "/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
		Client client = service.fromDTO(clientDTO);
		Client updatedClient = service.update(id, client);
		return ResponseEntity.ok().body(updatedClient);
	}

}
