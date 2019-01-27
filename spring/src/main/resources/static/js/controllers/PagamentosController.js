function PagamentosController($scope, $http, $rootScope,
		PagamentosServices) {
	$scope.valorSession = {};
	$scope.bandeira = "";
	$scope.hashCreditCard = "";
	$scope.hashComprador = "";
	
	$scope.initSession = function() {
		console.log('initSessionPayment');
		PagamentosServices.initSessionPayment({
			email: 'teste@teste.com'
		}, function(response) {
			console.log('initSessionPayment: '+response.idSession);
			PagSeguroDirectPayment.setSessionId(response.idSession);
		}, function(error) {
			alert(error);
		})
	};
	
	$scope.gerarTokenComprador = function() {
		console.log('gerarTokenComprador');
		PagSeguroDirectPayment.onSenderHashReady(function(response){
		    if(response.status == 'error') {
		        console.log(response.message);
		        return false;
		    }
		    console.log('onSenderHashReady - hash:' +response.senderHash);
		    $scope.hashComprador = response.senderHash; //Hash estará disponível nesta variável.
		});
	}
	
	$scope.formasDePagamento = function() {
		console.log('getPaymentMethods - Meio de pagamentos disponiveis');
		PagSeguroDirectPayment.getPaymentMethods({
			amount: 25.00,
			success: function(response) {
				console.log('getPaymentMethods:'+response.paymentMethods);
				//meios de pagamento disponíveis
			},
			error: function(response) {
				console.log('getPaymentMethods: Error: '+response);
				//tratamento do erro
			},
			complete: function(response) {
				//tratamento comum para todas chamadas
			}
		});
	}
	
	$scope.obterBandeiraCartao = function(cartao) {
		console.log('obterBandeiraCartao');
		PagSeguroDirectPayment.getBrand({
				cardBin:'411111',
			success: function(response) {
				console.log('Bandeira: ' +response.brand.name);
				$scope.bandeira = response.brand.name;
			},
			error: function(response) {
				console.log('obterBandeiraCartao - Error: '+response);
			},
			complete: function(response) {
			}
		});
	}
	
	$scope.obterTokenCartaoCredito = function() {
		console.log('obterTokenCartaoCredito');
		PagSeguroDirectPayment.createCardToken({
				cardNumber: '4111111111111111',
				brand: $scope.bandeira,
				cvv: '123',
				expirationMonth: '12',
				expirationYear: '2030',
			success: function(response) {
				$scope.hashCreditCard = response["card"].token;
				console.log('Token: '+$scope.hashCreditCard);
				//meios de pagamento disponíveis
			},
			error: function(response) {
				console.log('obterTokenCartaoCredito- Erro: '+response);
			},
			complete: function(response) {
			}
		});
	}
	
	$scope.opcoesParcelamentoDisponivel = function() {
		console.log('opcoesParcelamentoDisponivel');
		PagSeguroDirectPayment.getInstallments({	
				amount: 25.00,
				brand: $scope.bandeira,
				maxInstallmentNoInterest: 1,
			success: function(response) {
				console.log('opcoesParcelamentoDisponivel: ' +response.installments);
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
		console.log('transactionsPaymentCreditCard');
		PagamentosServices.transactionsPaymentCreditCard({
			token : $scope.hashCreditCard,
			hash : $scope.hashComprador
		}, function(response) {
			let resp = response;
		}, function(error) {
			alert(error);
		})
	};
	
	$scope.validacaoDataValidade = function(validade) {
		if(validade.length > 0) {
			validade = validade.replace(/\D/g, "");
			let dataValidadeFormat = validade.match(/\d{1,2}/g).join('/')
			$scope.cadastro.inputDataValidade = dataValidadeFormat;
		}
	}
	
//	$scope.initSession();
}
