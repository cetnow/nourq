<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
        <div th:fragment="content">
        <h2 class="ui center aligned icon header">
		  <i class="circular sound icon"></i>
		  <span th:text="#{allmediaheader}"></span>
		</h2>

	

	<form class="ui form warning content error" th:action="@{/allmedia}" method="post" >
	<div class="two fields">
		<div class="field">
			<a class="fluid ui button olive coloredtext" th:href="@{/addmedia}"><i class="plus icon"></i> <span th:text="#{addmedialin}"></span></a>
		</div>
		<div class="field">
			<a class="fluid ui button olive coloredtext" th:href="@{/addmediaau}"><i class="plus icon"></i> <span th:text="#{addedmiadi}"></span></a>
		</div>
	</div>

	
			<div class="three fields">
				<div class="field">
					<label th:text="#{MediaType}"></label>
					<div class="ui search selection dropdown multiple" id="types">
						<input type="hidden" name="typesselected" th:value="${typesselected}" /> 
						<i class="dropdown icon"></i>
						<div class="default text" th:text="#{MediaType}"></div>
						<div class="menu" id="suraslistcont">
							<div class="item" th:each="ele : ${types}" th:text="${ele['Name']}"
								th:attr="data-value=${ele['Id']}"></div>
						</div>
					</div>
				</div>
				
				<div class="field">
					<label th:text="#{MediaAuth}"></label>
					<div class="ui search selection dropdown multiple" id="authers">
						<input type="hidden" name="authersselected" th:value="${authersselected}" /> 
						<i class="dropdown icon"></i>
						<div class="default text" th:text="#{MediaAuth}"></div>
						<div class="menu" id="suraslistcont">
							<div class="item" th:each="ele : ${auths}" th:text="${ele['Name']}"
								th:attr="data-value=${ele['Id']}"></div>
						</div>
					</div>
				</div>
				
				<div class="field">
					<label th:text="#{sora}"></label>
					<div class="ui search selection dropdown multiple" id="suars">
						<input type="hidden" name="suarsselected" th:value="${suarsselected}" /> 
						<i class="dropdown icon"></i>
						<div class="default text" th:text="#{sora}"></div>
						<div class="menu" id="suraslistcont">
							<div class="item" th:each="ele : ${suars}" th:text="${ele['Name']}"
								th:attr="data-value=${ele['Id']}"></div>
						</div>
					</div>
				</div>
				
				
			</div>
			<div class="three fields">
				<div class="field">
				</div>
				<div class="field">
					<button class="ui olive  button fluid coloredtext" th:text="#{search}" type="submit" id="submit"></button>
				</div>
				<div class="field">
				</div>
			</div>
			
        </form>
        <br/>
		<center><div id='player_ele'></div></center>
        <table class="ui selectable celled table" id="tablecont" th:if="${data!=null and data.length()>0 }">
			  <thead>
			    <tr>
			    <th th:text="#{FileName}"></th>
			    <th th:text="#{SuraInfo}"></th>
			    <th th:text="#{MediaType}"></th>
			    <th th:text="#{MediaAuth}"></th>
			    <th th:text="#{Actions}"></th>
			  </tr></thead>
			  <tbody id="tbody" class="ayacontainersearch">
			    
			  </tbody>
			  <tfoot>
			    <tr>
				    <th colspan="5">
				      <ul id="tpagination" class="ui right floated pagination menu"></ul>
				    </th>
			  </tr></tfoot>
		</table>
		
		<a id='downloadlink'></a>
		<div th:text="#{noresulttoshow}" th:if="${data==null or data.length()==0 }"></div>
        
        <script th:src="@{/assets/js/allmedia.js}"></script>
        <script th:if="${ispost==true}" th:inline="javascript">
		options.tabledata = JSON.parse(/*[[${data.toString()}]]*/);
		startactions();
		</script>
		
        </div>
</html>