package br.com.spring.calcular.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.spring.calcular.dto.PagamentosDTO;
import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.session.CreatedSession;

/**
 * 
 * @author Thiago Nascimento
 * @since 20/01/2019
 */
@Controller
@RequestMapping("/rest")
public class PagamentosController {
	/** ID da Aplicação */
	private static final String appId = "app0029705968";
	/** Especifica o token correspondente à aplicação PagSeguro que está realizando a requisição. */
	private static final String appKey = "85EE5E2A9C9C7AC6647C0F87FBDCC4FA";
	
	private static final String sellerEmail = "thiagonascimentotav@gmail.com";
	private static final String sellerToken = "36325EDC1D6E421988CE68043864166F";
	private static String sellerTokenSession = "36325EDC1D6E421988CE68043864166F";
	private static String codigo = null;
	
	/**
	 *  Iniciar sessão de pagamento.
	 *  return ID Session
	 */
	@RequestMapping(value = "/initSessionPayment", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<PagamentosDTO> initSessionPayment(final HttpServletRequest request,
			@RequestParam("email") String email) {
		PagamentosDTO pagamentos = new PagamentosDTO();
		final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail,
	            sellerToken), PagSeguroEnv.SANDBOX);
		CreatedSession createdSession = pagSeguro.sessions().create();
		String idSession = createdSession.getId();
		pagamentos.setIdSession(idSession);
		System.out.println(idSession);
		return new ResponseEntity<PagamentosDTO>(pagamentos, HttpStatus.OK);
	}
	
	/**
	 *  Finalizando transação para cartao de credito
	 *  return ID Session
	 */
	@RequestMapping(value = "/transactionsPaymentCreditCard", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<PagamentosDTO> transactionsPaymentCreditCard(final HttpServletRequest request,
			@RequestBody String xmlCreditCard) {
		PagamentosDTO pagamentos = new PagamentosDTO();
		try {
			consultaSOAP(xmlCreditCard);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<PagamentosDTO>(pagamentos, HttpStatus.OK);
	}
	
	/**
	 * Metodo para efetuar a consulta dos xml soap
	 * 
	 * @param xmlSoap
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String consultaSOAP(String xmlSoap) throws SOAPException, IOException, JAXBException {
		String url = "https://ws.sandbox.pagseguro.uol.com.br/v2/transactions";
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		MimeHeaders headers = new MimeHeaders();
		headers.addHeader("Content-Type", "application/xml");
		headers.addHeader("charset", "ISO-8859-1");

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(xmlSoap.getBytes())));
		SOAPMessage soapResponse = soapConnection.call(msg, url);

		String body = soapResponse.getSOAPBody().toString();
		System.out.println(body);
		
		return body;
	}
}
