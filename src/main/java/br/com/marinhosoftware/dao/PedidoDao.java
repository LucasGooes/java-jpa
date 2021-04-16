package br.com.marinhosoftware.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.marinhosoftware.domain.Pedido;
import br.com.marinhosoftware.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void insert(Pedido ped) {
		this.em.persist(ped);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	//NESSA CONSULTA VIRA EAGER
	public Pedido buscarPedidoComCliente(Long id) {
		String jpql = "SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id";
		return em.createQuery(jpql , Pedido.class)
				.setParameter( "id", id)
				.getSingleResult();		
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas() {
		String jpql = "SELECT new br.com.marinhosoftware.vo.RelatorioDeVendasVo("
					   + "produto.nome, "
					   + "SUM(item.quantidade), "
					   + "MAX(pedido.data)) "
					   + "FROM Pedido pedido "
					   + "JOIN pedido.itens item "
					   + "JOIN item.produto produto "
					   + "GROUP BY produto.nome "
					   + "ORDER BY item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
	}

}
