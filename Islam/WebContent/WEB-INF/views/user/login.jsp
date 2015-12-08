<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	th:include="/layouts/defaultar/layout :: page">
	<div th:fragment="content">
		<div class="container">
			<form class="ui form warning content error" th:action="@{/user/login}" method="post" >
			<div class="ui two column middle aligned very relaxed stackable grid">
			<div class="column">
				<div th:if="${errors!= null and errors.isSuccess()==false}" th:utext="${errors.getMessage()}" class="ui error message" id="FormsErrors"></div>
				<div class="ui stacked segment">
					<div class="field required">
						<label th:text="#{Email}"></label>
						
						<div class="ui left icon input">
							<i class="user icon"></i>
							<input type="text" id="Email" name="Email" th:value="*{user!=null}?*{user.getEmail()}:''"/>
						</div>
					</div>
					<div class="field required">
						<label th:text="#{Password}"></label>
						<div class="ui left icon input">
							<i class="lock icon"></i>
							<input type="password" id="Password" name="Password"/>
						</div>
					</div>
					<button class="ui fluid submit labeled icon button positive" type="submit" name="login" value="normal">
						<i class="sign in icon"></i>
				      	<span th:text="#{login}"></span>
					</button>
				</div>
				<div class="ui message">
			      <a th:href="@{/user/signup}" th:text="#{createuser}"></a>
			    </div>
				</div>
				
				<div class="ui vertical divider" th:text="#{Or}"></div>
				
				  <div class="center aligned column">
				  
				    <button class="ui facebook labeled icon button" type="submit" name="login" value="facebook">
				      <i class="facebook icon"></i>
				      <span th:text="#{facebooklogin}"></span>
				    </button>
				    
				  </div>
				  
				</div>
			</form>
		</div>
		<script th:src="@{/assets/js/signup.js}"></script>
	</div>
</html>