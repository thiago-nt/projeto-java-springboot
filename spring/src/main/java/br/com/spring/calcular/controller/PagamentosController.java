package br.com.spring.calcular.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.spring.calcular.dto.PagamentosDTO;
import br.com.spring.calcular.entity.ResponseTransaction;
import br.com.spring.calcular.entity.Transaction;
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

	private static final String sellerEmail = "thiagonascimentotav@gmail.com";
	private static final String sellerToken = "36325EDC1D6E421988CE68043864166F";

	private static final String sellerEmailSandbox = "thiago@sandbox.pagseguro.com.br";
	private static final String sellerTokenSandbox = "917718X2577277t4";
	
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	private static final String MSG_UTF_8 = "UTF-8";
	private static final Charset UTF_8 = Charset.forName(MSG_UTF_8);
	private static final Charset ISO = Charset.forName("ISO-8859-1");
	private static String codigo = null;

	/**
	 * Iniciar sessão de pagamento. return ID Session
	 */
	@RequestMapping(value = "/initSessionPayment", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<PagamentosDTO> initSessionPayment(final HttpServletRequest request,
			@RequestParam("email") String email) {
		PagamentosDTO pagamentos = new PagamentosDTO();
		final PagSeguro pagSeguro = PagSeguro.instance(Credential.sellerCredential(sellerEmail, sellerToken),
				PagSeguroEnv.SANDBOX);
		CreatedSession createdSession = pagSeguro.sessions().create();
		String idSession = createdSession.getId();
		pagamentos.setIdSession(idSession);
		System.out.println(idSession);
		return new ResponseEntity<PagamentosDTO>(pagamentos, HttpStatus.OK);
	}

	/**
	 * Finalizando transação para cartao de credito return ID Session
	 */
	@RequestMapping(value = "/transactionsPaymentCreditCard", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<PagamentosDTO> transactionsPaymentCreditCard(final HttpServletRequest request,
			@RequestParam("token") String token, @RequestParam("hash") String hash) {
		PagamentosDTO pagamentos = new PagamentosDTO();
		try {
			String params = xmlSoap(token, hash);
			consultarUrlEncoded(params);
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
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
		String url = "https://ws.sandbox.pagseguro.uol.com.br/v2/transactions?email=" + sellerEmail + "&token="
				+ sellerToken + "";
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		MimeHeaders headers = new MimeHeaders();
		headers.addHeader("Content-Type", "application/xml; charset=ISO-8859-1");

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage msg = messageFactory.createMessage(headers, (new ByteArrayInputStream(xmlSoap.getBytes())));
		SOAPMessage soapResponse = soapConnection.call(msg, url);

		String body = soapResponse.getSOAPBody().toString();
		System.out.println(body);

		return body;
	}

	public String consultarUrlEncoded(String xmlString) throws Exception {
		String url = "https://ws.sandbox.pagseguro.uol.com.br/v2/transactions?email=" + sellerEmail + "&token="
				+ sellerToken + "";

		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		HttpEntity<String> entity = new HttpEntity<String>(xmlString, headers);
		ResponseEntity<String> responseRest = restTemplate.postForEntity(url, entity, String.class);

		if (responseRest.getStatusCode() == HttpStatus.OK) {
			JSONObject xmlJSONObj = XML.toJSONObject(responseRest.getBody());
			String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			System.out.println(jsonPrettyPrintString);
			ResponseTransaction response = convertJsonUtf8(jsonPrettyPrintString);
			System.out.println(response);
		}
		return "";
	}
	
	public static ResponseTransaction convertJsonUtf8(String value) {
		ResponseTransaction response = new ResponseTransaction();
		String textUtf8 = new String(value.toString().getBytes(ISO), UTF_8);
		Gson g2 = new Gson();
		response = g2.fromJson(textUtf8, ResponseTransaction.class);
		return response;
	}

	public String parametersUrlEncoded(String token) {
		String xml = "&paymentMode=default" + "&paymentMethod=creditCard" + "&receiverEmail=suporte@lojamodelo.com.br"
				+ "&currency=BRL" + "&extraAmount=1.00" + "&itemId1=0001" + "&itemDescription1=Notebook Prata"
				+ "&itemAmount1=24300.00" + "&itemQuantity1=1" + "&notificationURL=https://sualoja.com.br/notifica.html"
				+ "&reference=REF1234" + "&senderName=Jose Comprador" + "&senderCPF=22111944785" + "&senderAreaCode=11"
				+ "&senderPhone=56273440" + "&senderEmail=comprador@uol.com.br" + "&senderHash=abc123"
				+ "&shippingAddressStreet=Av. Brig. Faria Lima" + "&shippingAddressNumber=1384"
				+ "&shippingAddressComplement=5o andar" + "&shippingAddressDistrict=Jardim Paulistano"
				+ "&shippingAddressPostalCode=01452002" + "&shippingAddressCity=Sao Paulo" + "&shippingAddressState=SP"
				+ "&shippingAddressCountry=BRA" + "&shippingType=1" + "&shippingCost=1.00" + "&creditCardToken=" + token
				+ "" + "&installmentQuantity=5" + "&installmentValue=125.22" + "&noInterestInstallmentQuantity=2"
				+ "&creditCardHolderName=Jose Comprador" + "&creditCardHolderCPF=22111944785"
				+ "&creditCardHolderBirthDate=27/10/1987" + "&creditCardHolderAreaCode=11"
				+ "&creditCardHolderPhone=56273440" + "&billingAddressStreet=Av. Brig. Faria Lima"
				+ "&billingAddressNumber=1384" + "&billingAddressComplement=5o andar"
				+ "&billingAddressDistrict=Jardim Paulistano" + "&billingAddressPostalCode=01452002"
				+ "&billingAddressCity=Sao Paulo" + "&billingAddressState=SP" + "&billingAddressCountry=BRA";
		return xml;
	}

	public String xmlSoap(String token, String hash) {
		String xml = "<payment>\n" + "<mode>default</mode>\n" + "<method>creditCard</method>\n" + "<sender>\n"
				+ "<name>Fulano Silva</name>\n" + "<email>c84026693547344247670@sandbox.pagseguro.com.br</email>\n"
				+ "<phone>\n" + "<areaCode>11</areaCode>\n" + "<number>30380000</number>\n" + "</phone>\n"
				+ "<documents>\n" + "<document>\n" + "<type>CPF</type>\n" + "<value>11475714734</value>\n"
				+ "</document>\n" + "</documents>\n" + "<hash>" + hash + "</hash>\n" + "</sender>\n"
				+ "<currency>BRL</currency>\n"
				+ "<notificationURL>https://sualoja.com.br/notificacao</notificationURL>\n" + "<items>\n" + "<item>\n"
				+ "<id>1</id>\n" + "<description>Plano Basico</description>\n" + "<quantity>1</quantity>\n"
				+ "<amount>25.00</amount>\n" + "</item>\n" + "</items>\n" + "<extraAmount>0.00</extraAmount>\n"
				+ "<reference>R123456</reference>\n" + "<shipping>\n" + "<address>\n"
				+ "<street>Av. Brigadeiro Faria Lima</street>\n" + "<number>1384</number>\n"
				+ "<complement>1 andar</complement>\n" + "<district>Jardim Paulistano</district>\n"
				+ "<city>Sao Paulo</city>\n" + "<state>SP</state>\n" + "<country>BRA</country>\n"
				+ "<postalCode>01452002</postalCode>\n" + "</address>\n" + "<type>3</type>\n" + "<cost>0.00</cost>\n"
				+ "</shipping>\n" + "<creditCard>\n" + "<token>" + token + "</token>\n" + "<installment>\n"
				+ "<quantity>1</quantity>\n" + "<value>25.00</value>\n" + "</installment>\n" + "<holder>\n"
				+ "<name>Nome impresso no cartao</name>\n" + "<documents>\n" + "<document>\n" + "<type>CPF</type>\n"
				+ "<value>34904918827</value>\n" + "</document>\n" + "</documents>\n"
				+ "<birthDate>20/10/1980</birthDate>\n" + "<phone>\n" + "<areaCode>11</areaCode>\n"
				+ "<number>999991111</number>\n" + "</phone>\n" + "</holder>\n" + "<billingAddress>\n"
				+ "<street>Av. Brigadeiro Faria Lima</street>\n" + "<number>1384</number>\n"
				+ "<complement>1 andar</complement>\n" + "<district>Jardim Paulistano</district>\n"
				+ "<city>Sao Paulo</city>\n" + "<state>SP</state>\n" + "<country>BRA</country>\n"
				+ "<postalCode>01452002</postalCode>\n" + "</billingAddress>\n" + "</creditCard>" + "</payment>";
		return xml;
	}

}
