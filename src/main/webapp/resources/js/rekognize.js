// Define the `phonecatApp` module
var rekognize = angular.module('rekognize', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
rekognize.controller('HomePageController', function HomePageController($scope) {
	$scope.specialMessage = "Error 763";
});