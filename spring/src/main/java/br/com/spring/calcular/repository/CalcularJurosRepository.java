package br.com.spring.calcular.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.calcular.entity.CalcularJuros;

@Repository
public interface CalcularJurosRepository extends CrudRepository<CalcularJuros, Long> {

}
