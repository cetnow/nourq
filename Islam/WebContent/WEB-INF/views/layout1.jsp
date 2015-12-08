<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:fragment="page">
    <head>
   		<script th:src="@{/assets/js/jquery-1.11.3.min.js}"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Test</title>
    </head>
    <body>
        layout page
        <div th:include="this :: content1"/>
        layout footer
        
        <div th:include="this :: content2"/>
    </body>
</html>