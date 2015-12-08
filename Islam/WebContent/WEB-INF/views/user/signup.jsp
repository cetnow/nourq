<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	th:include="/layouts/defaultar/layout :: page">
	<div th:fragment="content">
		<div class="container">
			<form class="ui form warning content error" th:action="@{/user/signup}" method="post" >
				<div th:if="${errors!= null and errors.isSuccess()==false}" th:utext="${errors.getMessage()}" class="ui error message" id="FormsErrors"></div>
				<div class="ui stacked segment">
				<div class="three fields">
					<div class="field">
						<label th:text="#{FirstName}"></label>
						<input type="text" id="FirstName" name="FirstName" th:value="*{user!=null}?*{user.getFirstName()}:''"/>
					</div>
					<div class="field">
						<label th:text="#{LastName}"></label>
						<input type="text" id="LastName" name="LastName" th:value="*{user!=null}?*{user.getLastName()}:''" />
					</div>
					<div class="field">
				  		<label th:text="#{Gender}"></label>
				  		<div class="ui selection dropdown ">
							 <input type="hidden" name="Gender" th:value="*{user!=null}?*{user.getGender()}:0" />
							 <div class="default text" th:text="#{PleaseSelect}"></div>
						 	 <i class="dropdown icon"></i>
							  <div class="menu" id="surasayatocont">
							  	<div class="item"  th:each="ele : ${gens}" th:text='${ele["Name"]}' th:attr='data-value=${ele["Id"]}'></div>
							  </div>
						</div>
				  	</div>
				</div>
				
				<div class="field required">
					<label th:text="#{Email}"></label>
					<input type="text" id="Email" name="Email" th:value="*{user!=null}?*{user.getEmail()}:''"/>
				</div>
				
				<div class="field required">
					<label th:text="#{Password}"></label>
					<input type="password" id="Password" name="Password"/>
				</div>
				
				
				<button class="ui fluid submit button positive" th:text="#{Send}" type="submit"></button>
				</div>
			</form>
		</div>
		<script th:src="@{/assets/js/signup.js}"></script>
	</div>
</html>