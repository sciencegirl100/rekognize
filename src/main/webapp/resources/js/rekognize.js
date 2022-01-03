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
	}
});

rekognize.controller('HomePageController', function HomePageController($scope) {
	console.log("Home Page");
});

rekognize.controller('RunPageController', function RunPageController($scope) {
	console.log("Run Page");
});

rekognize.controller('ConfigurePageController', function ConfigurePageController($scope) {
	$scope.ConfigureTooltip = "Configure AWS Credentials and S3 Bucket";
	console.log("Configure Page");
});

