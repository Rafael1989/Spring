package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.confs.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {JPAConfiguration.class,ProdutoDao.class,DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDaoTest {

	@Autowired
	private ProdutoDao produtoDao;
	
	@Test
	@Transactional
	public void deveSomarTodosOsPrecosPorTipoLivro() {
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO,BigDecimal.TEN).more(3).buildAll();
		List<Produto> ebooks = ProdutoBuilder.newProduto(TipoPreco.EBOOK,BigDecimal.TEN).more(3).buildAll();
		
		livrosImpressos.stream().forEach(produtoDao::adiciona);
		ebooks.stream().forEach(produtoDao::adiciona);
		
		BigDecimal valor = produtoDao.somaPrecosPorTipo(TipoPreco.EBOOK);
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}

}
