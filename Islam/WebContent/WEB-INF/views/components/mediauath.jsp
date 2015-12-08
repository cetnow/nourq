<div class="ui search selection dropdown " id="authtemplate">
	<input type="hidden" id="authtemplatevalue" /> 
	<i class="dropdown icon"></i>
	<div class="default text"></div>
	<div class="menu" id="suraslistcont">
		<div class="item" th:each="ele : ${auths}" th:text="${ele['Name']}"
			th:attr="data-value=${ele['Id']}"></div>
	</div>
</div>