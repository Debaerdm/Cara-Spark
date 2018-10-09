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
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('MountainsList', function ($scope, $http) {
    $http.get('/api/mountain').success(function (data) {
        console.log(data);
        $scope.dungeons.name = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});