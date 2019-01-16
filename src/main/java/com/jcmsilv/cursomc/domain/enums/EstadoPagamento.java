package com.jcmsilv.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

	private String descricao;
	private int cod;

	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return this.cod;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static EstadoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (EstadoPagamento controle : EstadoPagamento.values()) {
			if (cod.equals(controle.getCod())) {
				return controle;
			}
		}
		throw new IllegalArgumentException("Id Inválido: " + cod);
	}
}
