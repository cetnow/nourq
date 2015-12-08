<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	th:include="/layouts/defaultar/layout :: page">
<div th:fragment="content">
	<h2 class="ui center aligned icon header">
		<i class="circular sound icon"></i> <span th:text="${header}"></span>
	</h2>
	<div class="content center">
		<center><div id='player_ele'></div></center>
	</div>
	<div class="ui middle aligned list selection animated fonttext">
		<div class="item" th:each="ele : ${data}">
			<div class="content mediaclick"
				th:attr="data-value=${ele['Id']},data-value2=${ele['FileName']+' - '+ele['TypeName']}">
				<div class="header linkpoint fonttext "
					th:text="${ele['FileName']+' - '+ele['TypeName']}"></div>
				<div th:text="${ele['MediaDec']}"></div>
				<div th:text="${ele['AutherName']}"></div>
			</div>
		</div>

	</div>


	<script th:src="@{/assets/js/mediapage.js}"></script>
</div>
</html>