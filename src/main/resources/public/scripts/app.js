var app = angular.module("DungeonLords", [
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

app.controller("MountainsList", ["$scope", "$http", "$location", function ($scope, $http, $location) {
    $scope.total = 0;
    $scope.update = function() {
        $http.get("/api/exist").success(function(data) {
            $scope.dungeonExist = data;
            console.log(data);
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/rock").success(function(data) {
            $scope.rock = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/gold").success(function(data) {
            $scope.gold = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/gems").success(function(data) {
            $scope.gems = data;
        }).error(function(data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/mountain").success(function (data) {
            $scope.dungeons = data;
        }).error(function (data, status) {
            console.log("Error " + data);
        });
        $http.get("/api/dungeon_total").success(function (data) {
            $scope.total = data;
        }).error(function (data, status) {
            console.log("Error " + data);
        });
    };
    $scope.join = function() {
        $http.get("/api/join").success(function (_, status) {
            if(status === 200) {
                $scope.myDungeon();
            }
            $scope.update();
        }).error(function (data, status) {
            console.log("Error " + data)
        })
    };
    $scope.myDungeon = function() {
        $scope.refresh = function() {
            $http.get("/api/dungeon").success(function (data) {
                $scope.map = data;
            }).error(function (data, status) {
                console.log("Error " + data)
            });
        };

        $http.get("/api/itemTypes").success(data => {
            $scope.itemTypes = data;
        }).error((data, status) => {
            console.log("Error "+ data);
        });

        $scope.select = item => {
            console.log(item);
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
                            $http.post("/api/build", data).success(data => {
                                $scope.buildLabel = data.label;
                                $scope.buildBody = data.bodyLabel;
                                if(data.code === 200) {
                                    $('#myModal').modal('show');
	                                $scope.map[data.row][data.col] = value;

                                    /*const img = document.getElementById(data.row+"-"+data.col);
                                    img.setAttribute("src", "images/"+data.image);*/

                                    const divContent = document.getElementById("content-"+data.row+"-"+data.col);

                                    const bar = document.createElement("div");
                                    bar.classList.add("progressBar");

	                                divContent.appendChild(bar);
	                            } else if (data.code === 500) {
	                                $('#myModal').modal('show');
	                            }
	                            $scope.update();
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
	                $http.post("/api/build", data).success(data => {
	                    if(data.code === 200) {
	                        $scope.buildLabel = data.label;
	                        $('#myModal').modal("show");
                                    divContent.appendChild(bar);
                                } else if (data.code === 500) {
                                    $('#myModal').modal('show');
                                }
                                $scope.update();
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
                    $http.post("/api/build", data).success(data => {
                        if(data.code === 200) {
                            $scope.buildLabel = data.label;
                            $('#myModal').modal("show");
	                    }
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
	$scope.update();
}]);