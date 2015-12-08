<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">

<body>

	<div th:fragment="copy">
	<br/><br/><br/><br/><br/><br/>
		<div class="ui inverted vertical footer segment orange">
			<div class="ui center aligned container">
				<div class="ui horizontal inverted small divided link list">
					<span th:text="#{copyright }"></span> <i class="copyright icon"></i>
				</div>
			</div>
		</div>
	</div>

</body>

</html>