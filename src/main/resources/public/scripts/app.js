var app = angular.module("DungeonLords", [
	"ngCookies",
	"ngResource",
	"ngSanitize",
	"ngRoute"
	]).controller("Header", ["$http", "$scope"]);

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

app.controller("DungeonController", function($scope, $http, $interval) {
	$http.get("/api/dungeon").success(function (data) {
		$scope.map = data;
	}).error(function (data, status) {
		console.log("Error " + data)
	});

    $http.get("/api/itemTypes").success(data => {
        $scope.itemTypes = data;
    }).error((data, status) => {
        console.log("Error "+ data);
    });

	$scope.select = item => {
        const menu = document.getElementById("menu");
        while (menu.firstChild) {
            menu.removeChild(menu.firstChild);
        }

        const title = document.createElement("h1");
        const titleText = document.createTextNode(item.isWall ? "Ceci est un mur, vous voulez creuser ?" : "Vous pouvez construire un building sur cette terre !");
        title.appendChild(titleText);
        menu.appendChild(title);

        if(!item.isWall) {
            $scope.itemTypes.forEach(element => {
                const button = document.createElement("button");
                button.innerText = element;
                button.classList.add("btn", "btn-primary");
                button.addEventListener("click", () => {
                    const data = {
                        "buildItem": element,
                        "row": item.row,
                        "col": item.col
                    };
                    $http.post("/api/build", data).success(status => {
                        console.log("BUILD !!! ["+status+"]");
                    }).error(function (data, status) {
                        console.log("Error " + data);
                    });
                });

                menu.appendChild(button);
            });
        } else {
            const button = document.createElement("button");
            button.innerText = "Creuser";
            button.classList.add("btn", "btn-primary");
            button.addEventListener("click", () => {
                const data = {
                    "buildItem": "dirt",
                    "row": item.row,
                    "col": item.col
                };
                $http.post("/api/build", data).success(status => {
                    console.log("BUILD !!! ["+status+"]");
                }).error(function (data, status) {
                    console.log("Error " + data);
                });
            });
            menu.appendChild(button);
        }
	}
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
		console.log("Error " + data);
	});
});