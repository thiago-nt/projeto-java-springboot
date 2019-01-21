<html>
<head>
<title>Formulário de Cadastro</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
</head>
<body ng-app="CalcularJurosApp" ng-controller="CalcularJurosController">
	<div class="container">
		<div>
			<a class="btn btn-default" ng-click="salvarDados(cadastro)">Salvar</a>
			<a class="btn btn-default" ng-click="consultar()">Consultar</a>
		</div>
		<caption>Formulário de Cadastro</caption>
		<form>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="nomeCliente" class="labelInput">Nome Cliente*</label> <input
						type="text" class="form-control" id="nomeCliente"
						ng-model="cadastro.nomeCliente" placeholder="Digite o seu nome">
				</div>
				<div class="form-group col-md-6">
					<label for="limiteCredito" class="labelInput">Limite de
						Credito*</label> <input type="text" class="form-control" id="limiteCredito"
						ng-model="cadastro.limiteCredito" placeholder="Limite de Credito">
				</div>
				<div class="form-group col-md-6">
					<label for="risco">Risco*</label> <select class="form-control"
						id="risco" ng-model="cadastro.risco">
						<option>A</option>
						<option>B</option>
						<option>C</option>
					</select>
				</div>
			</div>
		</form>
		<table class="table table-striped">
			<caption>Listar Dados Cadastrados</caption>
			<thead>
				<tr>
					<th>Nome Cliente</th>
					<th>Limite de Credito</th>
					<th>Risco</th>
					<th>Taxa de Juros</th>
				</tr>
			</thead>
			<tbody ng-repeat="item in itemList">
				<tr>
					<td>{{item.nomeCliente}}</td>
					<td>{{item.limiteCredito}}</td>
					<td>{{item.risco}}</td>
					<td>{{item.taxaJuros}}</td>
				</tr>
			</tbody>
		</table>
		<script src="/angular/angular.js"></script>
		<script src="/angular/angular-resource.js"></script>
		<script src="/js/modules/CalcularJurosApp.js"></script>
		<script src="/js/services/CalcularJurosServices.js"></script>
		<script src="/js/controllers/CalcularJurosController.js"></script>
		<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<script src="js/custom.js"></script>
	</div>
</body>
</html>
