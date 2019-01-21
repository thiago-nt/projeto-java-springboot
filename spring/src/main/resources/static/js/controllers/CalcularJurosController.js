function CalcularJurosController($scope, $http, $rootScope,
		CalcularJurosServices) {
	$scope.alertFields = false;
	$scope.itemList = {};

	$scope.salvarDados = function(cadastro) {
		if ($scope.validandoCamposObrigatorios(cadastro)) {
			CalcularJurosServices.create({
				nomeCliente : cadastro.nomeCliente,
				limiteCredito : cadastro.limiteCredito,
				risco : cadastro.risco
			}, function(response) {
				cadastro.nomeCliente = '';
				cadastro.limiteCredito = '';
				$scope.consultar();
			}, function(error) {
				alert(response);
			})
		}
	};

	$scope.validandoCamposObrigatorios = function(cadastro) {
		if (cadastro != undefined && cadastro != null && cadastro.nomeCliente != null
				&& cadastro.limiteCredito != null && cadastro.risco != null) {
			return true;
		} else {
			alert('Favor preencher os campos obrigatorios');
			return false;
		}
	};

	$scope.consultar = function() {
		$scope.itemList = CalcularJurosServices.findAll({}, function(response) {
		}, function(error) {
		})
	};
}
