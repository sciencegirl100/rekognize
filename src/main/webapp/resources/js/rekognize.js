// Define the `phonecatApp` module
var rekognize = angular.module('rekognize', []);

rekognize.component('menubar', {
	templateUrl: '/resources/templates/MenuBar.html',
   	controller: function menubarController() {
		console.log("Load Menubar");
	}
});

rekognize.component('about', {
	templateUrl: '/resources/templates/About.html',
	controller: function aboutController() {
		console.log("Load About");
	}
});

rekognize.component('savedanalysis', {
	templateUrl: '/resources/templates/SavedAnalysis.html',
	controller: function savedAnalysisController() {
		console.log("Load SavedAnalysis");
		$scope.getList = function(){
			$http.get("/api/list").then(function(response) {
				$scope.list = response;
	  		});
		}
	}
});

rekognize.controller('HomePageController', function HomePageController($scope) {
	console.log("Home Page");
});

rekognize.controller('RunPageController', function RunPageController($scope, $http) {
	console.log("Run Page");
	
	$scope.SubmitImage = function(files){
		console.log("Submit Image"); // 👍 That works
		console.dir(files);
		if(files.length >= 1){
			var image = files[0];
			var fd = new FormData();
			fd.append("image", image);
			$http({
				method: 'POST',
				url: '/api/face',
				data: fd,
				headers: {'Content-Type': undefined},
				transformRequest: angular.identity
			}).then(function(result) {
				console.dir(result.data);
				console.log(typeof result.data);
				$scope.APIResponse = JSON.stringify(result.data, null, "\t");
			});

		}
	}
});

rekognize.controller('ConfigurePageController', function ConfigurePageController($scope) {
	$scope.ConfigureTooltip = "Configure AWS Credentials and S3 Bucket";
	console.log("Configure Page");
});

