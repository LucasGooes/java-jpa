package br.com.marinhosoftware.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.marinhosoftware.dao.CategoriaDao;
import br.com.marinhosoftware.dao.ClienteDao;
import br.com.marinhosoftware.dao.PedidoDao;
import br.com.marinhosoftware.dao.ProdutoDao;
import br.com.marinhosoftware.domain.Categoria;
import br.com.marinhosoftware.domain.Cliente;
import br.com.marinhosoftware.domain.ItemPedido;
import br.com.marinhosoftware.domain.Pedido;
import br.com.marinhosoftware.domain.Produto;
import br.com.marinhosoftware.util.JPAUtil;

public class Performance {

	public static void main(String[] args) {
		popularBD();
		EntityManager em = JPAUtil.getEntityManager();
		PedidoDao pedidoDao = new PedidoDao(em);
		Pedido pedido = pedidoDao.buscarPedidoComCliente(1l);
		em.close();
		
		System.out.println(pedido.getCliente().getNome());
	}
	
	private static void popularBD() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMÁTICA");
		
		Produto celular = new Produto("xiaomi Redmi Note", "Xiaomi Redmi Note 8 Pro ", new BigDecimal("999"), celulares);
		Produto playstation = new Produto("PS5", "Playstation 5", new BigDecimal("6345"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook pro 13", new BigDecimal("10000"), informatica);
		
		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, celular));
		pedido.adicionarItem(new ItemPedido(40, pedido, playstation));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(2, pedido2, macbook));
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		CategoriaDao catDao = new CategoriaDao(em);
		ClienteDao cliDao = new ClienteDao(em);
		PedidoDao pedDao = new PedidoDao(em);
		
		em.getTransaction().begin();
		catDao.insert(celulares);
		catDao.insert(informatica);
		catDao.insert(videogames);
		
		prodDao.insert(celular);
		prodDao.insert(macbook);
		prodDao.insert(playstation);
		
		cliDao.insert(cliente);
		
		pedDao.insert(pedido);
		pedDao.insert(pedido2);
		
		em.getTransaction().commit();
		em.close();
	}

}
