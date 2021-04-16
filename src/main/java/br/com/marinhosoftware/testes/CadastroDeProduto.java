package br.com.marinhosoftware.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.marinhosoftware.dao.CategoriaDao;
import br.com.marinhosoftware.dao.ProdutoDao;
import br.com.marinhosoftware.domain.Categoria;
import br.com.marinhosoftware.domain.Produto;
import br.com.marinhosoftware.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		
		Produto p = prodDao.findById(1l);
		System.out.println(p.getPreco());
		
//		List<Produto> lista = prodDao.findAll();
//		lista.forEach(p2 -> System.out.println(p2.getNome()));
		
//		List<Produto> lista = prodDao.findByName("xiaomi Redmi Note");
//		lista.forEach(p2 -> System.out.println(p2.getNome()));
		
//		List<Produto> lista = prodDao.findByCategoria("CELULARES");
//		lista.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal precoDoProduto = prodDao.findPrecoByName("xiaomi Redmi Note");
		System.out.println(precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("xiaomi Redmi Note", "Muito Legal", new BigDecimal("800"), celulares);
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		CategoriaDao catDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		catDao.insert(celulares);
		prodDao.insert(celular);
		em.getTransaction().commit();
		em.close();
	}

}
