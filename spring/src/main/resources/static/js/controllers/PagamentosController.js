function PagamentosController($scope, $http, $rootScope,
		PagamentosServices) {
	$scope.valorSession = {};
	$scope.bandeira = "";
	$scope.hashCreditCard = "";
	
	$scope.initSession = function() {
		PagamentosServices.initSessionPayment({
			email: 'teste@teste.com'
		}, function(response) {
			PagSeguroDirectPayment.setSessionId(response.idSession);
		}, function(error) {
			alert(error);
		})
	};
	
	$scope.finalizarPagamento = function() {
		PagSeguroDirectPayment.onSenderHashReady(function(response){
		    if(response.status == 'error') {
		        console.log(response.message);
		        return false;
		    }
		    var hash = response.senderHash; //Hash estará disponível nesta variável.
		});
	}
	
	$scope.valorPagamento = function() {
		PagSeguroDirectPayment.getPaymentMethods({
			amount: 500.00,
			success: function(response) {
				alert(response);
				//meios de pagamento disponíveis
			},
			error: function(response) {
				alert(response);
				//tratamento do erro
			},
			complete: function(response) {
				alert(response);
				//tratamento comum para todas chamadas
			}
		});
	}
	
	$scope.obterBandeiraCartao = function() {
		PagSeguroDirectPayment.getBrand({
				cardBin:'411111',
			success: function(response) {
				alert(response);
				$scope.bandeira = response.brand.name;
				//meios de pagamento disponíveis
				$scope.obterTokenCartaoCredito(response);
			},
			error: function(response) {
				alert(response);
				//tratamento do erro
			},
			complete: function(response) {
				alert(response);
				//tratamento comum para todas chamadas
			}
		});
	}
	
	$scope.obterTokenCartaoCredito = function(brandJson) {
		PagSeguroDirectPayment.createCardToken({
				cardNumber: '4111111111111111',
				brand: brandJson.brand.name,
				cvv: '123',
				expirationMonth: '12',
				expirationYear: '2030',
			success: function(response) {
				alert(response);
				$scope.hashCreditCard = response;
				//meios de pagamento disponíveis
			},
			error: function(response) {
				alert(response);
				//tratamento do erro
			},
			complete: function(response) {
				alert(response);
				//tratamento comum para todas chamadas
			}
		});
	}
	
	$scope.opcoesParcelamentoDisponivel = function() {
		PagSeguroDirectPayment.getInstallments({	
				amount: 500.00,
				brand: $scope.bandeira,
				maxInstallmentNoInterest: 2,
			success: function(response) {
			//opções de parcelamento disponível
			},
			error: function(response) {
				//tratamento do erro
			},
			complete: function(response) {
				//tratamento comum para todas chamadas
			}
		});
	}
	
	$scope.transactionsPaymentCreditCard = function() {
		let xml = '<payment>'+
			    '<mode>default</mode>' +
			    '<method>creditCard</method>' +
			    '<sender>' +
			        '<name>Fulano Silva</name>' +
			        '<email>fulano.silva@uol.com.br</email>' +
			        '<phone>' +
			            '<areaCode>11</areaCode>' +
			            '<number>30380000</number>' +
			        '</phone>' +
			        '<documents>' +
			            '<document>' +
			                '<type>CPF</type>' +
			                '<value>11475714734</value>' +
			            '</document>' +
			        '</documents>' +
			        '<hash>abc1234</hash>' +
			    '</sender>' +
			    '<currency>BRL</currency>' +
			    '<notificationURL>' +
			    '<items>' +
			        '<item>' +
			            '<id>1</id>' +
			            '<description>Descricao do item a ser vendido</description>' +
			            '<quantity>2</quantity>' +
			            '<amount>1.00</amount>' +
			        '</item>' +
			    '</items>' +
			'<extraAmount>0.00</extraAmount>' +
			    '<reference>R123456</reference>' +
			    '<shipping>' +
			        '<address>' +
			            '<street>Av. Brigadeiro Faria Lima</street>' +
			            '<number>1384</number>' +
			            '<complement>1 andar</complement>' +
			            '<district>Jardim Paulistano</district>' +
			            '<city>Sao Paulo</city>' +
			            '<state>SP</state>' +
			            '<country>BRA</country>' +
			            '<postalCode>01452002</postalCode>' +
			        '</address>' +
			        '<type>3</type>' +
			        '<cost>0.00</cost>' +
			    '</shipping>' +
			    '<creditCard>' +
			        '<token>'+$scope.hashCreditCard+'</token>' +
			       '<installment>' +
			            '<quantity>2</quantity>' +
			            '<value>5.50</value>' +
			        '</installment>' +
			        '<holder>' +
			            '<name>Nome impresso no cartao</name>' +
			            '<documents>' +
			                '<document>' +
			                    '<type>CPF</type>' +
			                    '<value>00722333665</value>' +
			                '</document>' +
			            '</documents>' +
			            '<birthDate>20/10/1980</birthDate>' +
			            '<phone>' +
			                '<areaCode>11</areaCode>' +
			                '<number>999991111</number>' +
			            '</phone>' +
			        '</holder>' +
			        '<billingAddress>' +
			            '<street>Av. Brigadeiro Faria Lima</street>' +
			            '<number>1384</number>' +
			            '<complement>1 andar</complement>' +
			            '<district>Jardim Paulistano</district>' +
			            '<city>Sao Paulo</city>' +
			            '<state>SP</state>' +
			            '<country>BRA</country>' +
			            '<postalCode>01452002</postalCode>' +
			        '</billingAddress>' +
			    '</creditCard>' +
			'</payment>';
		PagamentosServices.transactionsPaymentCreditCard({
			xmlCreditCard : xml,
		}, function(response) {
			let resp = response;
		}, function(error) {
			alert(error);
		})
	};
	
//	$scope.initSession();
}
