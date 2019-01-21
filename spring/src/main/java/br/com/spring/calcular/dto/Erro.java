package br.com.spring.calcular.dto;

public class Erro {

	private String codigo;
	private String descricao;
	private String detalhes;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	@Override
	public String toString() {
		return "Erro [codigo=" + codigo + ", descricao=" + descricao + ", detalhes=" + detalhes + "]";
	}
}
