package br.com.casadocodigo.loja.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CarrinhoCompras {
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<>();
	
	public void add(CarrinhoItem carrinhoItem) {
		itens.put(carrinhoItem, getQuantidade(carrinhoItem) + 1);
	}

	private int getQuantidade(CarrinhoItem carrinhoItem) {
		if(!itens.containsKey(carrinhoItem)) {
			itens.put(carrinhoItem, 0);
		}
		return itens.get(carrinhoItem);
	}
	
	public int getQuantidade() {
		return itens.values().stream().reduce(0, (proximo,acumulador)->(proximo + acumulador));
	}

}
