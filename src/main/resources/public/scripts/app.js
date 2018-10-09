var app = angular.module('todoapp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/moutainList.html',
        controller: 'MountainsList'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('MountainsList', function ($scope, $http) {
    $http.get('/api/v1/moutain').success(function (data) {
        console.log(data);
        $scope.todos = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })
});