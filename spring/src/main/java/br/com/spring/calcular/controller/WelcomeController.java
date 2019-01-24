package br.com.spring.calcular.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {	
	@RequestMapping("/index")
	public String loginMessage(){
		return "index";
	}
	
	@RequestMapping("/pagamentos")
	public String loginPagamentos(){
		return "pagamentos";
	}
}