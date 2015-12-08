<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:fragment="page">
    <head>
    	<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-69847501-1', 'auto');
		  ga('send', 'pageview');
		
		</script>
	    <link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/fonts/ammri/styles.css}" />
	    <link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/fonts/midan/styles.css}" />
   		<script th:src="@{/assets/js/jquery-1.11.3.min.js}"></script>
   		<script th:src="@{/assets/jwplayer-7.1.4/jwplayer.js}"></script>
        <script type="text/javascript">jwplayer.key="uCzMTTyIfbm6ZACiF2Uhr+lC5q6QLiRZunl0hA==";</script>
   		<script th:src="@{/assets/semanticui/semantic.min.js}"></script>
   		<script th:src="@{/assets/semanticui/easing.min.js}"></script>
   		<script th:src="@{/assets/js/jquery.twbsPagination.min.js}"></script>
   		<script th:src="@{/assets/jplayer/jquery.jplayer.min.js}"></script>
   		<script th:src="@{/assets/jplayer/jplayer.playlist.min.js}"></script>
   		<script th:src="@{/assets/slider/nouislider.min.js}"></script>
   		
   		<script th:src="@{/assets/js/main.js}"></script>
 		<script th:inline="javascript">
 			options.baseurl=/*[[${@shared.getBaseUrl()}]]*/ '';
		</script>
		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/css/style.css}" />
   		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/semanticui/reset.rtl.css}" />
   		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/semanticui/semantic.rtl.min.css}" />
   		
   		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/fonts/styles.css}" />
   		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/slider/nouislider.min.css}" />
   		<link rel="stylesheet" type="text/css" media="all"  th:href="@{/assets/jplayer/css/flat.audio.css}" />
   		
   		
   		<script th:src="@{/assets/js/easing.min.js}"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title th:text="#{nour}"></title>
    </head>
    <body dir="rtl">
        <div id="waiting_modal" class="ui modal"><div class="ui large text loader" th:text="#{loading}"></div></div>
        <div th:include="/layouts/defaultar/menu :: copy"></div>
        
        
        <div class="ui main container">
        	<div id="MessagesBox"></div>
        	<div th:if="${_Message!= null}" th:utext="${_Message}"></div>
        	<br/><br/>
        	<div th:include="this :: content"></div>
        </div>
        <div th:include="/layouts/defaultar/footer :: copy"></div>
        
    </body>
</html>