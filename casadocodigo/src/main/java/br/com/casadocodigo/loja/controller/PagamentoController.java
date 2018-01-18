package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.model.CarrinhoCompras;
import br.com.casadocodigo.loja.model.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="finaliza",method=RequestMethod.POST)
	public Callable<ModelAndView> finaliza(RedirectAttributes redirectAttributes) {
		return()->{
			try {
				String url = "http://book-payment.herokuapp.com/payment";
				restTemplate.postForObject(url, new DadosPagamento(carrinho.getTotal()), String.class);
				redirectAttributes.addFlashAttribute("sucesso", "Pagamento realizado com sucesso");
				return new ModelAndView("redirect:produtos");
			}catch(HttpClientErrorException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("sucesso", "Valor maior que o permitido");
				return new ModelAndView("redirect:produtos");
			}
			
		};
	}
		

}
