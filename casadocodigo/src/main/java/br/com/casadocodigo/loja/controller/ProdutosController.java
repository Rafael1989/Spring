package br.com.casadocodigo.loja.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		return new ModelAndView("produtos/form").addObject("tipos", TipoPreco.values());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView adiciona(MultipartFile sumario, @Valid Produto produto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return form(produto);
		}
		
		String sumarioPath = fileSaver.write("arquivo-x", sumario);
		produto.setSumarioPath(sumarioPath);
		
		produtoDao.adiciona(produto);
		redirectAttributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso");
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView lista() {
		return new ModelAndView("produtos/lista").addObject("produtos", produtoDao.lista());
	}
	
	@RequestMapping("/detalha/{id}")
	public ModelAndView detalha(@PathVariable Integer id) {
		Produto produto = produtoDao.getProduto(id);
		return new ModelAndView("produtos/detalhe").addObject("produto", produto);
	}
}
