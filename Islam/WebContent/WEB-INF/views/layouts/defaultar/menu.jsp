<div th:fragment="copy">
	<div class="ui fixed  menu inverted orange ">
		<div class="left menu">
			<div class="item">
				<i class="home icon"></i>
		        <a th:href="@{/viewaya}" th:text="#{home}" class="headerfont"></a>
		    </div>
		    
		    <div class="item">
				<i class="sound icon"></i>
		        <a th:href="@{/allmedia}" th:text="#{lessmoha}" class="headerfont"></a>
		    </div>
		    
		    <div th:if="${@session.isAdmin()==true}"  class="item">
				<i class="sound icon"></i>
		        <a th:href="@{/mediaapproval}" th:text="#{adminapprovals}" class="headerfont"></a>
		    </div>
		    
		</div>
		
		<div th:if="${@session.getUser()==null}" class="right menu">
			<div class="item">
				<i class="add user icon"></i>
		        <a th:href="@{/user/signup}" th:text="#{newuser}" class="headerfont"></a>
		    </div>
		    <div class="item">
				<i class="sign in icon"></i>
		        <a th:href="@{/user/login}" th:text="#{login}" class="headerfont"></a>
		    </div>
		</div>
		
		<div th:if="${@session.getUser()!=null}" class="right menu">
			<div class="item">
				<i class="sign out icon"></i>
		        <a class="headerfont" th:href="@{/user/logout}" th:text="#{logout(${@session.getUser().getFirstName()},${@session.getUser().getLastName() })}"></a>
		    </div>
		</div>
	</div>
</div>