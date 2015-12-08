<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	th:include="/layouts/defaultar/layout :: page">
	<div th:fragment="content">
		<div class="ui error message centered" th:text="#{notlogin1 }"></div>
	</div>
</html>