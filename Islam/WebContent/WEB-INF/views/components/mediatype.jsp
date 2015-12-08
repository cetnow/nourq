<div class="ui search selection dropdown " id="typetemplate">
	<input type="hidden" id="typetemplatevalue"  /> 
	<i class="dropdown icon"></i>
	<div class="default text"></div>
	<div class="menu" id="suraslistcont">
		<div class="item" th:each="ele : ${types}" th:text="${ele['Name']}"
			th:attr="data-value=${ele['Id']}"></div>
	</div>
</div>