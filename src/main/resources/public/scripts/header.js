function header($scope, $interval, $http) {
	$scope.total = -1;
	$scope.updateTotal = () => {
		$http.get("/api/dungeon_total").success(data => {
			$scope.total = data;
		}).error((data, status) => {
			console.log("Error " + data)
		});
	};
	$interval($scope.updateTotal(), 1000);
}