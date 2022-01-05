<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="rekognize">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rekognize - Run</title>
        <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="/resources/css/style.css" rel="stylesheet"/>
        <script src="https://kit.fontawesome.com/d451b60ca2.js" crossorigin="anonymous"></script>
        <script src="/resources/bootstrap/js/bootstrap.min.js"></script>
        <script src="/resources/angularjs/angular.min.js"></script>
        <script src="/resources/angularjs/angular-route.min.js"></script>
        <script src="/resources/js/rekognize.js"></script>

        <!-- For development: -->
        <script src="https://cdn.jsdelivr.net/npm/live@0.1.25-beta.0/lib/index.min.js"></script>
    </head>
    <!-- This is the landing page with details about the application as well as access credentials -->
    <body ng-controller="RunPageController">
	   	<menubar></menubar>
	   	<!-- TODO: add async call to JSP REST API -->
        <div class="container-fluid contentOffset">
            <div class="row content">
                <div class="col-4">
                    <div class="row overflow-auto test">
                        <!-- Past Analysis  -->
                        <!-- <savedanalysis></savedanalysis> -->
                    </div>
                </div>
                <div class="col-8">
                    <div class="row">
                        ${status}
                    </div>
                    <form novalidate class="configure-form">
                        <div class="row">
                            <!-- Image upload and view -->
                            <input type="file" id="file" name="file" accept="image/*" Required/>
                        </div>
                        <div class="row">
                            <button type="submit" class="themed-button" onclick="submitFile">Analyze Image</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <Script>
        function submitFile(){
            console.log("submitFile");
            var file = document.getElementById("file").files[0];
        }
    </Script>
</html>
