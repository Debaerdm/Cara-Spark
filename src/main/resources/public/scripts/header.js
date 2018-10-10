function header($scope, $interval, $http) {
	$scope.total = -1;
	$scope.updateTotal = function() {
		$http.get("/api/dungeon_total").success(function(data) {
			$scope.total = data;
		}).error(function (data, status) {
			console.log("Error " + data)
		});
	}
	$interval($scope.updateTotal(), 1000);
}