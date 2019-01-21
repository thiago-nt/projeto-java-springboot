package br.com.spring.calcular.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import br.com.spring.calcular.dto.CalcularJurosDTO;
import br.com.spring.calcular.dto.Erro;

/**
 * 
 * @author Thiago Nascimento
 * @since 20/01/2019
 */
public class Utils {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final Charset ISO = Charset.forName("ISO-8859-1");

	public static CalcularJurosDTO convertJson(String value) {
		CalcularJurosDTO response = new CalcularJurosDTO();
		String textUtf8 = new String(value.toString().getBytes(ISO), UTF_8);
		Gson g2 = new Gson();
		response = g2.fromJson(textUtf8, CalcularJurosDTO.class);
		return response;
	}
	
	public static BigDecimal calculoTaxaJuros(String taxa, String valorCredito) {
		BigDecimal valorTaxa = new BigDecimal(taxa);
		BigDecimal juros = new BigDecimal(valorCredito).multiply(valorTaxa).setScale(3, RoundingMode.UP);
		return juros;
	}
	
	public static CalcularJurosDTO genericErro(CalcularJurosDTO calcularJurosDTO, Exception e) {
		Erro erro = new Erro();
		erro.setCodigo("[Generic]");
		erro.setDescricao(e.getCause().toString());
		calcularJurosDTO.setErro(erro);
		return calcularJurosDTO;
	}
	
	public static CalcularJurosDTO erroCamposObrigatorios(CalcularJurosDTO calcularJurosDTO, String msg) {
		calcularJurosDTO = new CalcularJurosDTO();
		Erro erro = new Erro();
		erro.setCodigo("[Generic]");
		erro.setDescricao(msg);
		erro.setDetalhes("nomeCliente/ limiteCredito / risco=A-B-C");
		calcularJurosDTO.setErro(erro);
		return calcularJurosDTO;
	}
	
	public static CalcularJurosDTO genericErroJSON(CalcularJurosDTO calcularJurosDTO, Exception e) {
		Erro erro = new Erro();
		erro.setCodigo("[Generic]");
		erro.setDescricao("Erro nos parametros do JSON");
		erro.setDetalhes(e.getCause().toString());
		calcularJurosDTO.setErro(erro);
		return calcularJurosDTO;
	}
}
