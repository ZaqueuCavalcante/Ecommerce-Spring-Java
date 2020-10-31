package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	@Transactional(readOnly = true)
	Client findByEmail(String email);

}
