package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.model.Produto;

@Repository
@Transactional
public class ProdutoDao {
	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Produto produto) {
		manager.persist(produto);
	}
	
	public List<Produto> lista(){
		return manager.createQuery("select p from Produto p",Produto.class).getResultList();
	}
	
	public Produto getProduto(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos precos where p.id = :id", Produto.class).setParameter("id", id).getSingleResult();
	}

}
