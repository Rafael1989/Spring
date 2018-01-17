package br.com.casadocodigo.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
public class ProdutosController {
	
	@Autowired
	private ProdutoDao produtoDao;

	@RequestMapping("/produtos/form")
	public ModelAndView form() {
		return new ModelAndView("produtos/form").addObject("tipos", TipoPreco.values());
	}
	
	@RequestMapping("/produtos")
	public String adiciona(Produto produto) {
		produtoDao.adiciona(produto);
		return "produtos/ok";
	}
}
