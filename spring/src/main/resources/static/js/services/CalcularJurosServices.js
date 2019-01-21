var modulo = angular.module('CalcularJurosServices', [ 'ngResource' ]);

modulo.factory('CalcularJurosServices', [ '$resource', function($resource) {

	return $resource('/rest/:action/:id', {}, {
		  findAll :{
			     isArray: true,
				 method: 'GET',
			     params: {action: 'findAll'}
		  },
		  create :{
			     isArray: false,
				 method: 'POST',
			     params: {action: 'create'}
		  }
	});
} ]);
