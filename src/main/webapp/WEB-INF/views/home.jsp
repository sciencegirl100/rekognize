<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="rekognize">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rekognize - Home</title>
        <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
        <script src="/resources/bootstrap/js/bootstrap.min.js"></script>
        <script src="/resources/angularjs/angular.min.js"></script>
        <script src="/resources/angularjs/angular-route.min.js"></script>
        <script src="/resources/js/rekognize.js"></script>
        
    </head>
    <!-- This is the landing page with details about the application as well as access credentials -->
    <body ng-controller="HomePageController">
	   	<h1>{{specialMessage}}</h1>
        <div id="page-wrapper">
		  <div id="page-viewport">
		    <div id="sidebar">
		      <div id="sidebar-header">
		        <a href="" class="toggle-sidebar"><h3>Rekognizer</h3></a>
		        <span class="pull-right">
		          <a href="" class="toggle-sidebar btn btn-default">
		            <i class="fa fa-bars"></i>
		          </a>
		        </span> 
		      </div>
		      <ul class="nav nav-pills nav-stacked">
		        <li><a ui-sref="menu.item1">Home<span class="pull-right"><i class="fa fa-info"></i></span></a>
		        </li>
		
		      </ul>
		    </div>
		    <div id="sidebar-page-content">
		      <div ui-view></div>
		    </div>
		  </div>
		</div>
    </body>
</html>
