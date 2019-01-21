package br.com.spring.calcular.enumeration;

public enum TaxaJuros {

	DEZ_PORCENTO("0.10"), VINTE_PORCENTO("0.20");

	private String descricao;
	
	private TaxaJuros(String valor) {
		descricao = valor;
	}

	public String getDescricao() {
		return descricao;
	}
}
