var app = angular.module('DungeonLords', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/mountainList.html',
        controller: 'MountainsList'
    }).when('/join', {
        controller: 'join'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('JoinController', function($scope, $http) {
    $scope.dungeon = "No dungeon";
    $scope.join = function() {
        $http.post('/api/join', null).success(function (data) {
            $scope.dungeon = data;
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    };
});

app.controller('MountainsList', function ($scope, $http) {
    $http.get('/api/mountain').success(function (data) {
        $scope.dungeons = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});