package br.com.spring.calcular.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonSyntaxException;

import br.com.spring.calcular.dto.CalcularJurosDTO;
import br.com.spring.calcular.entity.CalcularJuros;
import br.com.spring.calcular.service.CalcularJurosService;
import br.com.spring.calcular.utils.Utils;

/**
 * 
 * @author Thiago Nascimento
 * @since 20/01/2019
 */
@Controller
@RequestMapping("/rest")
public class CalcularJurosController {

	private static final Logger log = LoggerFactory.getLogger(CalcularJurosController.class);
	@Autowired
	CalcularJurosService calcularJurosService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<CalcularJurosDTO> create(final HttpServletRequest request,
			@RequestBody String json) {
		log.info("[CalcularJurosController] - create");
		CalcularJurosDTO calcularJurosDTO = new CalcularJurosDTO();
		try {
			calcularJurosDTO = calcularJurosService.create(json);
		} catch (JsonSyntaxException e) {
			Utils.genericErroJSON(calcularJurosDTO, e); 
			return new ResponseEntity<CalcularJurosDTO>(calcularJurosDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.genericErro(calcularJurosDTO, e); 
			return new ResponseEntity<CalcularJurosDTO>(calcularJurosDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		return new ResponseEntity<CalcularJurosDTO>(calcularJurosDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<CalcularJuros>> findAll() {
		List<CalcularJuros> calcularJuros = calcularJurosService.findAll();
        return new ResponseEntity<List<CalcularJuros>>(calcularJuros, HttpStatus.OK);
    }
}
