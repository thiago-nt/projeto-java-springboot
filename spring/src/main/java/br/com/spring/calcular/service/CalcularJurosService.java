package br.com.spring.calcular.service;

import java.util.List;

import br.com.spring.calcular.dto.CalcularJurosDTO;
import br.com.spring.calcular.entity.CalcularJuros;

public interface CalcularJurosService {

	public CalcularJurosDTO create(String json) throws Exception;
	public List<CalcularJuros> findAll();
}
