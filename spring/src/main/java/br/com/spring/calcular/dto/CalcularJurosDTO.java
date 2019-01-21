package br.com.spring.calcular.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author Thiago Nascimento
 * @since 20/01/2019
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nomeCliente", "limiteCredito", "risco" })
public class CalcularJurosDTO {

	@JsonProperty("nomeCliente")
	private String nomeCliente;
	@JsonProperty("limiteCredito")
	private String limiteCredito;
	@JsonProperty("risco")
	private String risco;
	@JsonProperty("taxaJuros")
	private String taxaJuros;
	private Erro erro;

	public Erro getErro() {
		return erro;
	}

	public void setErro(Erro erro) {
		this.erro = erro;
	}

	@JsonProperty("nomeCliente")
	public String getNomeCliente() {
		return nomeCliente;
	}

	@JsonProperty("nomeCliente")
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	@JsonProperty("limiteCredito")
	public String getLimiteCredito() {
		return limiteCredito;
	}

	@JsonProperty("limiteCredito")
	public void setLimiteCredito(String limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	@JsonProperty("risco")
	public String getRisco() {
		return risco;
	}

	@JsonProperty("risco")
	public void setRisco(String risco) {
		this.risco = risco;
	}

	@JsonProperty("taxaJuros")
	public String getTaxaJuros() {
		return taxaJuros;
	}

	@JsonProperty("taxaJuros")
	public void setTaxaJuros(String taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	@Override
	public String toString() {
		return "CalcularJurosDTO [nomeCliente=" + nomeCliente + ", limiteCredito=" + limiteCredito + ", risco=" + risco
				+ ", taxaJuros=" + taxaJuros + ", erro=" + erro + "]";
	}

}
