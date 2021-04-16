package br.com.marinhosoftware.dao;

import javax.persistence.EntityManager;

import br.com.marinhosoftware.domain.Categoria;

public class CategoriaDao {
	
	private EntityManager em;
	
	public CategoriaDao(EntityManager em) {
		this.em = em;
	}
	
	public void  insert(Categoria cat) {
		this.em.persist(cat);
	}
	
	public void  update(Categoria cat) {
		this.em.merge(cat);
	}
	
	public void delete(Categoria cat) {
		cat = em.merge(cat);
		this.em.remove(cat);
	}
	
}
