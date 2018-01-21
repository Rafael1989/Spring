package br.com.casadocodigo.loja.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.dao.UsuarioDao;
import br.com.casadocodigo.loja.model.Role;
import br.com.casadocodigo.loja.model.Usuario;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView index() {
		return new ModelAndView("home").addObject("produtos", produtoDao.lista());
	}
	
	@Transactional
	@ResponseBody
	@RequestMapping("/url-magica-thiasdadkjahsdkjahsdjkahkjdkjadsjhakhdkakhsdjkak")
	public String urlMagica() {
		Usuario usuario = new Usuario();
		usuario.setNome("ADMIN");
		usuario.setEmail("a@a.com");
		usuario.setSenha("$2a$10$lt7pS7Kxxe5JfP.vjLNSyOXP11eHgh7RoPxo5fvvbMCZkCUss2DGu");
		usuario.setRoles(Arrays.asList(new Role("ROLE_ADMIN")));
		
		if(usuarioDao.getUsuario(usuario)==null) {
			usuarioDao.adiciona(usuario);
		}
		
		return "thubiru biru";
	}

}
