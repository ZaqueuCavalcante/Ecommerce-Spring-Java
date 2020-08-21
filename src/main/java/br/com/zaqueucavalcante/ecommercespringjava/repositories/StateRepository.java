package br.com.zaqueucavalcante.ecommercespringjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zaqueucavalcante.ecommercespringjava.entities.State;

public interface StateRepository extends JpaRepository<State, Long> {

}
