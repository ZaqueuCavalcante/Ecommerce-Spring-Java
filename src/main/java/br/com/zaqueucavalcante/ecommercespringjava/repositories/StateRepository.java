package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
