 
angular.module('myApp', [])
.controller('myCtrl', ['$scope','$window', function($scope,$window) {
  $scope.launchSBI = function() {
	  $window.open('https://retail.onlinesbi.com/retail/login.htm', '_blank');
  };
  $scope.launchICICI = function(){
	  $window.open('https://infinity.icicibank.com/corp/AuthenticationController?FORMSGROUP_ID__=AuthenticationFG&__START_TRAN_FLAG__=Y&FG_BUTTONS__=LOAD&ACTION.LOAD=Y&AuthenticationFG.LOGIN_FLAG=1&BANK_ID=ICI','_blank');
  };
  $scope.launchIOB = function(){
	 $window.open('https://www.iobnet.co.in/ibanking/login.do','_blank');  
  };
}]);

 