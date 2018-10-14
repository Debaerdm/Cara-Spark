const app = angular.module("DungeonLords", [
    "ngCookies",
    "ngResource",
    "ngSanitize",
    "ngRoute"
]);

app.config(function ($routeProvider) {
    $routeProvider.when("/", {
        templateUrl: "views/mountainList.html",
    }).when("/dungeon", {
        templateUrl: "views/dungeonView.html",
    }).otherwise({
        redirectTo: "/"
    })
});

app.controller("MountainsList", ["$scope", "$http", "$location", "$interval", function ($scope, $http, $location, $interval) {
    $scope.total = 0;
    $scope.updateAll = function() {
        $http.get("/api/dungeon/exist").success(function(data) {
            $scope.dungeonExist = data;
            console.log(data);
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/dungeon/rock").success(function(data) {
            $scope.rock = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/dungeon/gold").success(function(data) {
            $scope.gold = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/dungeon/gems").success(function(data) {
            $scope.gems = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/mountain").success(function (data) {
            $scope.dungeons = data;
        }).error(function (data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/dungeon/dungeon_total").success(function (data) {
            $scope.total = data;
        }).error(function (data, status) {
            console.log("Error " + data);
        });
    };
    $scope.join = function() {
        $http.post("/api/join", null).success(function (_, status) {
            if(status === 200) {
                $scope.myDungeon();
            }
            $scope.updateAll();
        }).error(function (data, status) {
            console.log("Error " + data)
        })
    };
    $scope.myDungeon = function() {
        $scope.refresh = function() {
            $http.get("/api/dungeon/maps").success(function (data) {
                $scope.map = data;
            }).error(function (data, status) {
                console.log("Error " + data)
            });
        };

        $http.get("/api/dungeon/itemTypes").success(data => {
            $scope.itemTypes = data;
        }).error((data, status) => {
            console.log("Error "+ data);
        });

        $scope.select = item => {
            const menu = document.getElementById("menu");
            while (menu.firstChild) {
                menu.removeChild(menu.firstChild);
            }

            const divButton = document.createElement("div");
            divButton.classList.add("divButton");

            if(!item.isWall) {
                JSON.parse(JSON.stringify($scope.itemTypes), (key, value) => {
                    if(key) {
                        const button = document.createElement("button");
                        let text;
                        if (key === "ROCK") { text = "Construire une carri\u00e8re"; }
                        else if (key === "GOLD") { text = "Construire une mine d'or"; }
                        else if (key ==="GEMS") { text = "Construire une mine de pierres pr\u00e9cieuses"; }
                        button.innerText = text + "\n (Co\u00fbt : "+value+" pierres)";
                        button.classList.add("btn", "btn-primary");
                        button.addEventListener("click", () => {
                            const data = {
                                "buildItem": key,
                                "row": item.row,
                                "col": item.col
                            };
                            $http.put("/api/dungeon/build", data).success(data => {
                                $scope.buildLabel = data.label;
                                $scope.buildBody = data.bodyLabel;
                                if(data.code === 200) {
                                    $('#myModal').modal('show');
	                                $scope.map[data.row][data.col] = value;
	                            } else if (data.code === 500) {
	                                $('#myModal').modal('show');
	                            }
	                            $scope.updateAll();
	                            $scope.refresh();
	                        }).error(function (data, status) {
	                            console.log("Error " + data);
	                        });
	                    });
	                    divButton.appendChild(button);
	                }
	            });
	        } else {
                const button = document.createElement("button");
                button.innerText = "Creuser";
                button.classList.add("btn", "btn-primary");
                button.addEventListener("click", () => {
                    const data = {
                        "buildItem": "DIRT",
                        "row": item.row,
                        "col": item.col
                    };
                    $http.put("/api/dungeon/build", data).success(data => {
                        if (data.code === 200) {
                            $scope.buildLabel = data.label;
                            $('#myModal').modal("show");
                        } else if (data.code === 500) {
                            $('#myModal').modal('show');
                        }
                        $scope.updateAll();
                        $scope.refresh();
                    }).error(function (data, status) {
                        console.log("Error " + data);
                    });
                });
                divButton.appendChild(button);
            }
	        menu.appendChild(divButton);
		};
	    $scope.refresh();
		$location.path("/dungeon");
	};
	$scope.test = function() {
		$scope.join();
	};
	$scope.updateAll();
	$interval($scope.updateAll, 1000);
}]);