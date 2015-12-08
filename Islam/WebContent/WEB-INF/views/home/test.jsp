<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
      <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />	
     
      </head>
    <body>
        <div th:fragment="content">
        <script th:src="@{/assets/jwplayer-7.1.4/jwplayer.js}"></script>
        <script type="text/javascript">jwplayer.key="uCzMTTyIfbm6ZACiF2Uhr+lC5q6QLiRZunl0hA==";</script>
        <script type="text/javascript">
        	$(document).ready(function (){
        		var playerInstance = jwplayer('myElement');
        		  playerInstance.setup({ 
        			flashplayer:options.baseurl + "assets/jwplayer-7.1.4/jwplayer.flash.swf",
        		    file: 'http://localhost:8080/Islam/getfile?id=11',
        		    type: "mp3",
        		    width: 640,
        			height: 40
        		  });
        	});
        </script>
        <div id='myElement'>Loading the player ...</div>
        </div>
    </body>
</html>