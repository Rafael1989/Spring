package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Repository
@Transactional
public class ProdutoDao {
	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Produto produto) {
		manager.persist(produto);
	}
	
	public List<Produto> lista(){
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos",Produto.class).getResultList();
	}
	
	public Produto getProduto(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos precos where p.id = :id", Produto.class).setParameter("id", id).getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = manager.createQuery("select sum(preco.preco) from Produto p "
				+ "join p.precos preco where preco.tipo = :tipoPreco", BigDecimal.class);
		query.setParameter("tipoPreco", tipoPreco);
		return query.getSingleResult();
	}

}
