package br.com.spring.calcular.enumeration;

public enum Risco {

	A("A"), B("B"), C("C");

	private String descricao;
	
	private Risco(String valor) {
		descricao = valor;
	}

	public String getDescricao() {
		return descricao;
	}
}
