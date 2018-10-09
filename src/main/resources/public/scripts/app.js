var app = angular.module("DungeonLords", [
    "ngCookies",
    "ngResource",
    "ngSanitize",
    "ngRoute"
]);

app.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "views/mountainList.html",
        controller: "MountainsList"
    }).when("/dungeon", {
        templateUrl: "views/dungeonView.html",
    	controller: "DungeonController"
    }).otherwise({
        redirectTo: "/"
    })
});

app.controller("DungeonController", function($scope, $http) {
	$http.get("/api/dungeon").success(function (data) {
		$scope.map = data;
	}).error(function (data, status) {
        console.log("Error " + data)
    })
});

app.controller("JoinController", function($scope, $http, $location) {
    $scope.join = function() {
        $http.get("/api/join").success(function (_, status) {
            if(status === 200) {
                $location.path("/dungeon");
            }
        }).error(function (data, status) {
            console.log("Error " + data)
        })
    };
});

app.controller("MountainsList", function ($scope, $http) {
    $http.get("/api/mountain").success(function (data) {
        $scope.dungeons = data;
    }).error(function (data, status) {
        console.log("Error " + data)
    })
});