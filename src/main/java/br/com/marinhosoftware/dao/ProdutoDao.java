package br.com.marinhosoftware.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.marinhosoftware.domain.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public void insert(Produto prod) {
		this.em.persist(prod);
	}

	public Produto findById(Long id) {
		return em.find(Produto.class, id);
	}

	public List<Produto> findAll() {
		String queryJPQL = "SELECT p FROM Produto p";
		return em.createQuery(queryJPQL, Produto.class).getResultList();
	}

	public List<Produto> findByName(String nome) {
		String queryJPQL = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(queryJPQL, Produto.class).setParameter("nome", nome).getResultList();
	}

	public List<Produto> findByCategoria(String nomeCategoria) {
		String queryJPQL = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		return em.createQuery(queryJPQL, Produto.class).setParameter("nome", nomeCategoria).getResultList();
	}

	public BigDecimal findPrecoByName(String nome) {
		String queryJPQL = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(queryJPQL, BigDecimal.class).setParameter("nome", nome).getSingleResult();
	}

	//PARAMETROS DINÂMICOS COM CRITERIA
	public List<Produto> findByParametersWithCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		Predicate filtros = builder.and();
		if (nome != null && !nome.trim().isEmpty()) {
			filtros = builder.and( filtros, builder.equal(from.get("nome"), nome));
		}
		if (preco != null) {
			filtros = builder.and( filtros, builder.equal(from.get("preco"), preco));
		}
		if (dataCadastro != null) {
			filtros = builder.and( filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		}
		query.where(filtros);
		return em.createQuery(query).getResultList();
	}

}
