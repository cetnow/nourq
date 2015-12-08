<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
        <div th:fragment="content">
        <h2 class="ui center aligned icon header">
		  <i class="circular options icon"></i>
		  <span th:text="#{mediaapprovalhead}"></span>
		</h2>
		
		<form class="ui form warning content error" th:action="@{/mediaapproval}" method="post" >
			<table class="ui selectable celled table" id="tablecont">
			  <thead>
			    <tr>
			    <th th:text="#{No}"></th>
			    <th th:text="#{FileName}"></th>
			    <th th:text="#{Url}"></th>
			    <th th:text="#{SuraInfo}"></th>
			    <th th:text="#{mediarange}"></th>
			    <th th:text="#{MediaType}"></th>
			    <th th:text="#{MediaAuth}"></th>
			    <th th:text="#{Actions}"></th>
			  </tr></thead>
			  <tbody id="tbody" class="ayacontainersearch">
			    
			  </tbody>
			  <tfoot>
			    <tr>
				    <th colspan="8">
				      <ul id="tpagination" class="ui right floated pagination menu"></ul>
				    </th>
			  </tr></tfoot>
			</table>
		</form>
		
		<script th:src="@{/assets/js/mediaapproval.js}" th:inline="javascript"></script>
		<script th:inline="javascript">
		options.tabledata = JSON.parse(/*[[${data.toString()}]]*/);
		startactions();
		</script>
		
        </div>
</html>