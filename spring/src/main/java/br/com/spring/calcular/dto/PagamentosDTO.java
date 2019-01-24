package br.com.spring.calcular.dto;

public class PagamentosDTO {

	private String idSession;

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	@Override
	public String toString() {
		return "PagamentosDTO [idSession=" + idSession + "]";
	}
}
