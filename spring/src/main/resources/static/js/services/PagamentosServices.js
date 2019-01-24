var modulo = angular.module('PagamentosServices', [ 'ngResource' ]);

modulo.factory('PagamentosServices', [ '$resource', function($resource) {

	return $resource('/rest/:action/:id', {}, {
		  initSessionPayment :{
			     isArray: false,
				 method: 'GET',
			     params: {action: 'initSessionPayment'}
		  },
		  transactionsPaymentCreditCard :{
			     isArray: false,
				 method: 'POST',
			     params: {action: 'transactionsPaymentCreditCard'}
		  }
	});
} ]);
