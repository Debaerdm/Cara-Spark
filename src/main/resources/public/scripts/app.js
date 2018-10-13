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
    moneyfn($scope, $http);

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
	    console.log(item);
        const menu = document.getElementById("menu");
        while (menu.firstChild) {
            menu.removeChild(menu.firstChild);
        }

        const title = document.createElement("h1");
        const titleText = document.createTextNode(item.isWall ? "Ceci est un mur, vous voulez creuser ?" : "Vous pouvez construire un building sur cette terre !");
        title.appendChild(titleText);
        menu.appendChild(title);
        const divButton = document.createElement("div");
        divButton.classList.add("divButton");

        if(!item.isWall) {
            JSON.parse(JSON.stringify($scope.itemTypes), (key, value) => {
                if(key) {
                    const button = document.createElement("button");
                    button.innerText = key + "\n (Cout : "+value+")";
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

                                const value = $scope.map[data.row][data.col];
                                value.imagePath = data.image;
                                $scope.map[data.row][data.col] = value;

                                /*const img = document.getElementById(data.row+"-"+data.col);
                                img.setAttribute("src", "images/"+data.image);*/

                                const divContent = document.getElementById("content-"+data.row+"-"+data.col);

                                const bar = document.createElement("div");
                                bar.classList.add("progressBar");

                                divContent.appendChild(bar);
                                moneyfn($scope, $http)
                            } else if (data.code === 500) {
                                $('#myModal').modal('show');
                            }
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

                        const img = document.getElementById(data.row+"-"+data.col);
                        img.setAttribute("src", "images/"+data.image);
                        moneyfn($scope, $http)
                    }
                }).error(function (data, status) {
                    console.log("Error " + data);
                });
            });
            divButton.appendChild(button);
        }

        menu.appendChild(divButton);
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

const moneyfn = ($scope, $http) => {
    $http.get('/api/money').success(data => {
        console.log(data);
        $scope.money = data;
    }).error((data, status) => {
        console.log("Error " + data);
    })
};