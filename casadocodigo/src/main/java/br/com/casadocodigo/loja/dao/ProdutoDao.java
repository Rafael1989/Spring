package br.com.casadocodigo.loja.dao;

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

}