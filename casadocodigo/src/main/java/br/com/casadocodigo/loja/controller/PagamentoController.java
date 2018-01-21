package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.casadocodigo.loja.model.Usuario;

@Controller
@RequestMapping("/pagamento")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;
	
	@RequestMapping(value="finaliza",method=RequestMethod.POST)
	public Callable<ModelAndView> finaliza(@AuthenticationPrincipal Usuario usuario, RedirectAttributes redirectAttributes) {
		return()->{
			try {
				String url = "http://book-payment.herokuapp.com/payment";
				restTemplate.postForObject(url, new DadosPagamento(carrinho.getTotal()), String.class);
				redirectAttributes.addFlashAttribute("sucesso", "Pagamento realizado com sucesso");
				//enviaEmailCompraProduto(usuario);
				return new ModelAndView("redirect:/produtos");
			}catch(HttpClientErrorException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("sucesso", "Valor maior que o permitido");
				return new ModelAndView("redirect:produtos");
			}
			
		};
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		email.setTo(usuario.getEmail());
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compra@casadocodigo.com.br");
		
		sender.send(email);
	}
		

}
