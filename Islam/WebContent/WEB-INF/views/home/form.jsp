<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Getting Started: Handing Form Submission</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<h1>Form</h1>
    <form action="#" th:action="@{/form}" th:object="${book}" method="post">
    	<p>Id: <input type="text" th:field="*{Id}" /></p>
        <p>Message: <input type="text" th:field="*{Name}" /></p>
        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
    </form>
    <div th:text="${e}"></div>
</body>
</html>