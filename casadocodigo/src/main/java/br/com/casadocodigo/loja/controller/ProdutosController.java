package br.com.casadocodigo.loja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDao produtoDao;

	@RequestMapping("/form")
	public ModelAndView form() {
		return new ModelAndView("produtos/form").addObject("tipos", TipoPreco.values());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String adiciona(Produto produto) {
		produtoDao.adiciona(produto);
		return "produtos/ok";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		return new ModelAndView("produtos/lista").addObject("produtos", produtoDao.lista());
	}
}
