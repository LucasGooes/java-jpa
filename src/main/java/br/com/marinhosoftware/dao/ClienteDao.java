package br.com.marinhosoftware.dao;

import javax.persistence.EntityManager;

import br.com.marinhosoftware.domain.Cliente;

public class ClienteDao {

	private EntityManager em;

	public ClienteDao(EntityManager em) {
		this.em = em;
	}

	public void insert(Cliente cli) {
		this.em.persist(cli);
	}
	
	public Cliente findById(Long id) {
		return em.find(Cliente.class, id);
	}

}
