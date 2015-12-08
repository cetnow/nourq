<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
        <div th:fragment="content">
		<a href="socialauth.do?id=facebook" th:text="${success1}">
          </a>
        </div>
</html>