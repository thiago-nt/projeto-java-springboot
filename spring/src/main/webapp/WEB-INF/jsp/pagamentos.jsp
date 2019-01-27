<html>
<head>
<title>Formulário de Cadastro</title>
<link href="webjars/bootstrap/4.1.0/css/bootstrap.min.css"
	rel="stylesheet">

</head>
<body ng-app="PagamentosApp" ng-controller="PagamentosController">
	<div class="container">
		<form id="formPagamentos" class="needs-validation" novalidate>
			<div>
				<a class="btn btn-default" ng-click="initSession()">Consultar</a> <a
					class="btn btn-default" ng-click="obterBandeiraCartao()">Obter
					Bandeira</a> <a class="btn btn-default" ng-click="formasDePagamento()">Formas
					de Pagamento</a> <a class="btn btn-default"
					ng-click="opcoesParcelamentoDisponivel()">Opções de parcelamento</a>
				<a class="btn btn-default" ng-click="obterTokenCartaoCredito()">Obter
					Token Cartao</a> <a class="btn btn-default"
					ng-click="gerarTokenComprador()">Gerar Token Comprador</a> <a
					class="btn btn-default" ng-click="transactionsPaymentCreditCard()">Finalizando
					transação</a>
	
			</div>
			<nav>
			  <div class="nav nav-tabs" id="nav-tab" role="tablist">
			    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Cartão de crédito</a>
			    <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Boleto</a>
			    <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Débito online</a>
			  </div>
			</nav>
			<div class="tab-content" id="nav-tabContent">
			  <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
			  	<br>
					<div class="form-row">
						<div class="form-group col-md-3">
							<label for="inputNumeroCartao">Número do cartão</label> <input
								type="text" class="form-control" id="inputNumeroCartao" ng-model="cadastro.inputNumeroCartao" maxlength="16" required>
							<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-2">
							<label for="bandeiraCartao">Bandeira</label> <select
								class="custom-select" id="bandeiraCartao" required>
								<option disabled selected>Selecione</option>
								<option value="master">MasterCard</option>
								<option value="visa">VISA</option>
								<option value="amex">American Express</option>
							</select>
							<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-2">
							<label for="inputDataValidade">Data de validade</label> <input
								type="text" class="form-control" id="inputDataValidade" ng-model="cadastro.inputDataValidade" maxlength="5" ng-change="validacaoDataValidade(cadastro.inputDataValidade)"
								placeholder="MM/AA" required>
								<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-3"></div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-3">
							<label for="inputNomeDono">Nome do dono do cartão</label> <input
								type="text" class="form-control" id="inputNomeDono" ng-model="cadastro.inputNomeDono" maxlength="40"
								placeholder="Ex.:THIAGO N TAVARES" required>
								<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col">
							<label for="inputCodigoSeguranca">Código de segurança</label> <input
								type="text" class="form-control" id="inputCodigoSeguranca" maxlength="3" ng-model="cadastro.inputCodigoSeguranca"
								placeholder="000" required>
								<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-7"></div>
					</div>
			  </div>
			  <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">...</div>
			  <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">...</div>
			</div>
			<nav>
				  <div class="nav nav-tabs" id="nav-tab" role="tablist">
				    <a class="nav-item nav-link active" id="nav-dados-cartao-tab" data-toggle="tab" href="#nav-dados-cartao" role="tab" aria-controls="nav-dados-cartao" aria-selected="true">Dados do dono do cartão</a>
				  </div>
			 </nav>
			 <div class="tab-content" id="nav-tabContent">
			 	<div class="tab-pane fade show active" id="nav-dados-cartao" role="tabpanel" aria-labelledby="nav-dados-cartao-tab">
			 		<div class="form-row">
						<div class="form-group col-md-3">
							<label for="inputCpfDonoCartao">CPF do dono do cartão</label> <input
								type="text" class="form-control" id="inputCpfDonoCartao" autocomplete="off" ng-model="cadastro.inputCpfDonoCartao" maxlength="14" required>
							<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-2">
							<label for="inputDataValidade">Celular do dono do cartão</label> <input
								type="text" class="form-control" id="inputDataValidade" ng-model="cadastro.inputDataValidade" maxlength="5" ng-change="validacaoDataValidade(cadastro.inputDataValidade)" required>
								<div class="invalid-feedback">Campo obrigatório.</div>
						</div>
						<div class="form-group col-md-3"></div>
					</div>
			 	</div>
			 </div>
			 <button type="submit" class="btn btn-primary">Confirmar o
							pagamento</button>
		</form>
	</div>
	<script>
		// Exemplo de JavaScript inicial para desativar envios de formulário, se houver campos inválidos.
		(function() {
			'use strict';
			window.addEventListener('load',
					function() {
						// Pega todos os formulários que nós queremos aplicar estilos de validação Bootstrap personalizados.
						var forms = document
								.getElementsByClassName('needs-validation');
						// Faz um loop neles e evita o envio
						var validation = Array.prototype.filter.call(forms,
								function(form) {
									form.addEventListener('submit', function(
											event) {
										if (form.checkValidity() === false) {
											event.preventDefault();
											event.stopPropagation();
										}
										form.classList.add('was-validated');
									}, false);
								});
					}, false);
		})();
	</script>
	<script src="/angular/angular.js"></script>
	<script src="/angular/angular-resource.js"></script>
	<script src="/js/modules/PagamentosApp.js"></script>
	<script src="/js/services/PagamentosServices.js"></script>
	<script src="/js/controllers/PagamentosController.js"></script>
	<script src="webjars/jquery/3.2.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.1.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.directpayment.js"></script>
</body>
</html>
