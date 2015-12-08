<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
<div th:fragment="content">
	 	<h1 class="ui block header" th:text="#{linkheader}"></h1>
	 
		<form class="ui form warning content error" th:action="@{/addmedia}" method="post" id="ayatyoutube">
		<div th:if="${errors!= null and errors.isSuccess()==false}" th:utext="${errors.getMessage()}" class="ui error message" id="FormsErrors"></div>
		 <div class="ui warning message" th:text="#{urladdnote}"></div>
		  
		  <div class="three fields">
		  	<div class="field required">
		  		<label th:text="#{suran}"></label>
		  		<div class="ui search selection dropdown " id="suraslist">
					 <input type="hidden" id="selectedsura" name="Sura" th:value="${sura}" />
				 	 <i class="dropdown icon"></i>
				  	<div class="default text" th:text="#{suran}"></div>
					  <div class="menu" id="suraslistcont">
					    
					  </div>
				</div>
		  	</div>
		  	
		  	<div class="field required">
		  		<label th:text="#{ayaf}"></label>
		  		<div class="ui search selection dropdown " id="surasayafrom">
					 <input type="hidden" id="selectedsurasayafrom" name="FromAya" />
				 	 <i class="dropdown icon"></i>
				  	<div class="default text" th:text="#{ayaf}"></div>
					  <div class="menu" id="surasayafromcont">
					    
					  </div>
				</div>
		  	</div>
		  	
		  	<div class="field required">
		  		<label th:text="#{ayat}"></label>
		  		<div class="ui search selection dropdown " id="surasayato">
					 <input type="hidden" id="selectedsurasayato" name="ToAya" />
				 	 <i class="dropdown icon"></i>
				  	<div class="default text" th:text="#{ayat}"></div>
					  <div class="menu" id="surasayatocont">
					    
					  </div>
				</div>
		  	</div>
		  	
		  </div>
		  <div class="three fields">
		      <div class="field required">
			  		<label th:text="#{FileName}"></label>
			  		<input type="text" id="FileName" name="FileName" th:value="*{mi!=null}?*{mi.getFileName()}:''"  />
			  </div>
			  <div class="field required">
			  		<label th:text="#{MediaAuth}"></label>
			  		<div class="ui selection dropdown search" id="MediaAuth" >
			  		<input type="hidden" name="MediaAutherName" th:value="*{ma!=null}?*{ma.getMediaAutherName()}:''" />
			  		<div class="default text" th:text="#{MediaAuth}"></div>
			  		<div class="menu" id="suraslistcont">
						<div class="item" th:each="ele : ${authsloo}" th:text="${ele['Name']}"
							th:attr="data-value=${ele['Id']}"></div>
					</div>
			  		</div>
			  </div>
			  <div class="field required">
			  		<label th:text="#{MediaType}"></label>
			  		<div class="ui selection dropdown search" id="MediaType">
			  		<input type="hidden" name="MediaType" th:value="*{ma!=null}?*{ma.getMediaType()}:''" />
			  		<div class="default text" th:text="#{MediaType}"></div>
			  		<div class="menu" id="suraslistcont">
						<div class="item" th:each="ele : ${typesloo}" th:text="${ele['Name']}"
							th:attr="data-value=${ele['Id']}"></div>
					</div>
			  		</div>
			  </div>
		  </div>
		  
		<div class="field required">
			<label th:text="#{youtubeurl}"></label>
			<div class="ui right icon input">
			  
			  <input type="text" id="youtubeurl" name="MediaUrl" th:value="*{mi!=null}?*{mi.getMediaUrl()}:''" />
			  <i class="youtube icon"></i>
			</div>
		</div>
		<div class="field required">
		<label th:text="#{youtubeselectrange}"></label>
		  <div class="field" id="slider-limit">
		  	
		  </div>
		</div>
		  
		  <div class="three fields">
		    
		    <div class="field">
		      <label th:text="#{endyoutube}"></label>
		      <input  readonly="readonly" type="text" id="slider-limit-value-max"/>
		    </div>
		    <div class="field">
		      <label th:text="#{youtubelen}"></label>
		      <input  readonly="readonly" type="text" id="slider-length"/>
		    </div>
		    <div class="field">
		      <label th:text="#{starturlform}"></label>
		      <input type="hidden" id="Start" name="Start" value="0" th:value="*{mi!=null}?*{mi.getStart()}:''" />
		      <input type="hidden" id="End" name="End" value="0" th:value="*{mi!=null}?*{mi.getEnd()}:''" />
		      <input  readonly="readonly" type="text" id="slider-limit-value-min"/>
		    </div>
		  </div>
	
	  <div>
	    <button type="submit" class="ui positive right labeled icon button" th:text="#{ok}"></button>
	  </div>
	  
	  </form>
	  	<script th:inline="javascript">
	  	options.selectedSura=/*[[${sura}]]*/ '';
	  	options.selectedAya=/*[[${aya}]]*/ '';
	  	options.ayatoforlink=/*[[${toaya}]]*/ '';
		</script>
	  <script th:src="@{/assets/js/addmedia.js}" th:inline="javascript"></script>
</div>
</html>