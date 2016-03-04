//
var MenuCtrl = ['$scope','$http',function($scope,$http){
			$http.get('/salary/indexManage/getMenu').success(function(data){
				$scope.privilegeList = data.privilegeList;
				$scope.user = data.user;
			});
		}];