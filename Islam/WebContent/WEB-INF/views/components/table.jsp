


<table class="ui selectable celled table" id="tablecont">
  <thead>
    <tr>
    <th th:text="#{aya}"></th>
    <th th:text="#{ayanumber}"></th>
    <th th:text="#{sora}"></th>
  </tr></thead>
  <tbody id="tbody" class="ayacontainersearch">
    
  </tbody>
  <tfoot>
    <tr>
	    <th colspan="3">
	      <ul id="pagination-demo" class="ui right floated pagination menu"></ul>
	    </th>
  </tr></tfoot>
</table>
<script th:src="@{/assets/js/table.js}" th:inline="javascript"></script>
<script th:inline="javascript">
options.tabledata = JSON.parse(/*[[${l.toString()}]]*/);
startactions();
</script>


