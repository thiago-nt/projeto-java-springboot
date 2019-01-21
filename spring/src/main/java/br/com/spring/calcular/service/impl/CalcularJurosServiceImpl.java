package br.com.spring.calcular.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.spring.calcular.dto.CalcularJurosDTO;
import br.com.spring.calcular.entity.CalcularJuros;
import br.com.spring.calcular.enumeration.Risco;
import br.com.spring.calcular.enumeration.TaxaJuros;
import br.com.spring.calcular.repository.CalcularJurosRepository;
import br.com.spring.calcular.service.CalcularJurosService;
import br.com.spring.calcular.utils.Constantes;
import br.com.spring.calcular.utils.Utils;

/**
 * 
 * @author Thiago Nascimento
 * @since 20/01/2019
 */
@Service
public class CalcularJurosServiceImpl implements CalcularJurosService {
     
	@Autowired
	CalcularJurosRepository calcularJurosRepository;
	
	@Override
	public List<CalcularJuros> findAll() {
		Iterable<CalcularJuros> calcularJuros = calcularJurosRepository.findAll();
		List<CalcularJuros> calcularJurosList = new ArrayList<>();
		calcularJuros.forEach(calcularJurosList::add);
		return calcularJurosList;
	}
	
	@Override
	public CalcularJurosDTO create(String json) throws Exception {
		CalcularJurosDTO calcularJurosDTO = Utils.convertJson(json);
		if(validandoCamposObrigatorios(calcularJurosDTO)) {
			calcularJurosDTO = calcularTaxaJuros(calcularJurosDTO);
			CalcularJuros calcularJuros = populandoEntity(calcularJurosDTO);
			calcularJurosRepository.save(calcularJuros);
		} else {
			calcularJurosDTO = Utils.erroCamposObrigatorios(calcularJurosDTO, Constantes.MSG_CAMPOS_OBRIGATORIOS); 
		}
		return calcularJurosDTO;
	}

	private CalcularJuros populandoEntity(CalcularJurosDTO calcularJurosDTO) {
		CalcularJuros calcularJuros = new CalcularJuros();
		BigDecimal limiteCredito = new BigDecimal("0");
		BigDecimal taxaJuros = new BigDecimal("0");
		if(!StringUtils.isEmpty(calcularJurosDTO.getLimiteCredito())) {
			limiteCredito = new BigDecimal(calcularJurosDTO.getLimiteCredito());
		}
		if(!StringUtils.isEmpty(calcularJurosDTO.getTaxaJuros())) {
			taxaJuros = new BigDecimal(calcularJurosDTO.getTaxaJuros());
		}
		calcularJuros.setLimiteCredito(limiteCredito);
		calcularJuros.setNomeCliente(calcularJurosDTO.getNomeCliente());
		calcularJuros.setRisco(calcularJurosDTO.getRisco());
		calcularJuros.setTaxaJuros(taxaJuros);
		return calcularJuros;
	}
	
	private CalcularJurosDTO calcularTaxaJuros(CalcularJurosDTO calcularJuros) {
		formatandoCaractersInvalido(calcularJuros);
		if(calcularJuros.getRisco().equals(Risco.B.getDescricao())) {
			BigDecimal valorTaxaJuros = Utils.calculoTaxaJuros(TaxaJuros.DEZ_PORCENTO.getDescricao(), calcularJuros.getLimiteCredito().toString());
	    	calcularJuros.setTaxaJuros(valorTaxaJuros.toString());
		} else if(calcularJuros.getRisco().equals(Risco.C.getDescricao())) {
			BigDecimal valorTaxaJuros = Utils.calculoTaxaJuros(TaxaJuros.VINTE_PORCENTO.getDescricao(), calcularJuros.getLimiteCredito().toString());
	    	calcularJuros.setTaxaJuros(valorTaxaJuros.toString());
		}
		return calcularJuros;
	}
	
	private Boolean validandoCamposObrigatorios(CalcularJurosDTO calcularJuros) throws Exception {
		if(StringUtils.isEmpty(calcularJuros.getLimiteCredito()) || StringUtils.isEmpty(calcularJuros.getNomeCliente()) || 
				StringUtils.isEmpty(calcularJuros.getRisco())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	private void formatandoCaractersInvalido(CalcularJurosDTO calcularJuros) {
		if(calcularJuros.getLimiteCredito().indexOf(",") != -1) {
			calcularJuros.setLimiteCredito(calcularJuros.getLimiteCredito().replace(",", "."));
		}
	}
}
