package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Transactional(readOnly = true)
	Client findByEmail(String email);
}
