package br.com.marinhosoftware.testes;

import java.math.BigDecimal;
import java.util.List;

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
import br.com.marinhosoftware.vo.RelatorioDeVendasVo;

public class CadastroPedidos {

	public static void main(String[] args) {
		popularBD();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		ClienteDao cliDao = new ClienteDao(em);
		
		Produto produto =  prodDao.findById(1l);
		Cliente cliente = cliDao.findById(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.insert(pedido);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: "+totalVendido);
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		relatorio.forEach(System.out::println);
	}
	
	private static void popularBD() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("xiaomi Redmi Note", "Muito Legal", new BigDecimal("800"), celulares);
		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao prodDao = new ProdutoDao(em);
		CategoriaDao catDao = new CategoriaDao(em);
		ClienteDao cliDao = new ClienteDao(em);
		
		em.getTransaction().begin();
		catDao.insert(celulares);
		prodDao.insert(celular);
		cliDao.insert(cliente);
		em.getTransaction().commit();
		em.close();
	}

}
