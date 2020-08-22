package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	@Transactional(readOnly = true)
	Client findByEmail(String email);
}
