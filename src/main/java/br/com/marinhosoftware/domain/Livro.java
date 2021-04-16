package br.com.marinhosoftware.domain;

import javax.persistence.Entity;

@Entity
public class Livro extends Produto {

	private String autor;
	private Integer numeroDePaginas;
	
	public Livro() {
	}
	
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public Integer getNumeroDePaginas() {
		return numeroDePaginas;
	}
	public void setNumeroDePaginas(Integer numeroDePaginas) {
		this.numeroDePaginas = numeroDePaginas;
	}
}
