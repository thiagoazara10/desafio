package desafio.com.br.modelo;

import com.google.gson.annotations.SerializedName;

public class Senha implements Comparable<Senha>{
	
	@SerializedName("prioridade")
	private String prioridade;
	@SerializedName("senha")
	private String senha;
	@SerializedName("emissao")
	private long emissao;
	@SerializedName("chamada")
	private long chamada;
	@SerializedName("naFrente")
	private int naFrente;
	@SerializedName("espera")
	private long espera;
	
	public long getEspera() {
		return espera;
	}
	public void setEspera(long espera) {
		this.espera = espera;
	}
	public long getChamada() {
		return chamada;
	}
	public void setChamada(long chamada) {
		this.chamada = chamada;
	}
	
	public int getNaFrente() {
		return naFrente;
	}
	public void setNaFrente(int naFrente) {
		this.naFrente = naFrente;
	}
	
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public long getEmissao() {
		return emissao;
	}
	public void setEmissao(long emissao) {
		this.emissao = emissao;
	}
	
	@Override
	public int compareTo(Senha senha1) {
		
		if (this.emissao < senha1.emissao) {
            return -1;
        }
        if (this.emissao > senha1.emissao) {
            return 1;
        }
        return 0;
	}
	
}
