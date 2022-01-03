<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html ng-app="rekognize">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Rekognize - Configure</title>
    <link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/resources/css/style.css" rel="stylesheet" />
    <script src="https://kit.fontawesome.com/d451b60ca2.js" crossorigin="anonymous"></script>
    <script src="/resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resources/angularjs/angular.min.js"></script>
    <script src="/resources/angularjs/angular-route.min.js"></script>
    <script src="/resources/js/rekognize.js"></script>

    <!-- For development: -->
    <script src="https://cdn.jsdelivr.net/npm/live@0.1.25-beta.0/lib/index.min.js"></script>
</head>
<!-- This is the landing page with details about the application as well as access credentials -->

<body ng-controller="ConfigurePageController">
    <menubar></menubar>
    <div class="container-fluid contentOffset">
        <div class="row content">
            <div class="col-6">
                <!-- Configure Form -->
                <!-- TODO: Custom JS for parsing data for transmission -->
                <form novalidate class="configure-form" action="/configure">
                    <div class="row">
                        <div class="col">
                            <label>
                                AWS Access Key ID <hh class="req-mark">*</hh>
                            </label>
                            <br>
                            <input type="text" name="awsId" value="${awsId}" class="themed-input" required>
                            <br>
                            <br>
                            <label>
                                AWS Access Key Secret <hh class="req-mark">*</hh> 
                            </label>
                            <br>
                            <input type="password" name="awsSecret" placeholder="${awsKeyMask}" class="themed-input" required>
                            <br>
                            <br>
                        </div>
                        <div class="col">
                            <label>
                                AWS Region
                            </label>
                            <br>
                            <select class="themed-select" name="awsRegion" title="Rekognition AWS Regions">
                                ${awsRegion}
                                <option value="us-east-1">US East (N. Virginia)</option>
                                <option value="us-east-2">US East (Ohio)</option>
                                <option value="us-west-1">US West (N. California)</option>
                                <option value="us-west-2">US West (Oregon)</option>
                                <option value="eu-central-1">EU (Frankfurt)</option>
                                <option value="eu-west-1">EU (Ireland)</option>
                                <option value="eu-west-2">EU (London)</option>
                                <option value="ap-northeast-1">Asia Pacific (Tokyo)</option>
                                <option value="ap-northeast-2">Asia Pacific (Seoul)</option>
                                <option value="ap-southeast-1">Asia Pacific (Singapore)</option>
                                <option value="ap-southeast-2">Asia Pacific (Sydney)</option>
                                <option value="ap-south-1">Asia Pacific (Mumbai)</option>
                            </select>
                            <br>
                            <br>
                            <label>
                                S3 Bucket Behavior
                            </label>
                            <br>
                            <input type="checkbox" name="awsKeep" ${awsKeep}>
                            Keep images in S3
                        </div>
                    </div>
                    <div class="row">
                        <button type="submit" class="themed-button">Save</button>
                    </div>
                </form>
            </div>
            <div class="col-6">
                <div id="tooltip" class="row">
                    ${status}
                </div>
            </div>
        </div>
    </div>
</body>

<Script>
    switch("${pushState}"){
        case "configure":
            history.pushState({}, "Rekognize - Configure", "/configure");
            break;
        default:
            console.log("Unknown pushState: ${pushState}");
            break;
    }
</Script>
</html>