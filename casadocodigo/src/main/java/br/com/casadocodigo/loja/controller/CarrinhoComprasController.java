package br.com.casadocodigo.loja.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.model.CarrinhoCompras;
import br.com.casadocodigo.loja.model.CarrinhoItem;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController implements Serializable{
	
	private static final long serialVersionUID = -3386551333610233701L;

	@Autowired
	private ProdutoDao ProdutoDao;
	
	@Autowired
	private CarrinhoCompras carrinhoCompras;
	
	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipo) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criaItem(produtoId,tipo);
		carrinhoCompras.add(carrinhoItem);
		return modelAndView;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens() {
		return new ModelAndView("/carrinho/itens");
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public ModelAndView remove(Integer produtoId, TipoPreco tipoPreco) {
		carrinhoCompras.remove(produtoId,tipoPreco);
		return new ModelAndView("redirect:/carrinho");
	}
	
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipo) {
		Produto produto = ProdutoDao.getProduto(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto,tipo);
		return carrinhoItem;
	}
	
	
	
	

}
