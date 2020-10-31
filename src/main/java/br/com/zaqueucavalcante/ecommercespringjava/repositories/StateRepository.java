package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
