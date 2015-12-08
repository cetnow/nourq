<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      th:include="/layouts/defaultar/layout :: page">
      
    <body>
        <div th:fragment="content">
            <script th:src="@{/assets/js/viewaya.js}"></script>
            <div class="ui icon message fonttext">
			  <i class="inbox icon"></i>
			  <div class="content">
			    <div class="header" th:text="#{mediawehave}">
			    </div>
			    <p th:utext="#{mediawehaved(${@dal.getAllApproval()},#{link(@{/addmedia},#{addmedialin})},#{link(@{/addmediaau},#{addedmiadi})}) }"></p>
			    <p id="ayamedire"></p>
			  </div>
			</div>
            <h2 class="ui center aligned icon header">
			  <i class="circular sound icon"></i>
			  <span th:text="#{viewayaheader}"></span>
			</h2>
            <br/>
			<div class="ui attached menu">
			  
			  <div class="left menu">
			    <div class="ui right aligned category search item">
			      <div class="ui transparent icon input">
			      	<form id="searchform" method="get">
				        <input class="prompt" type="search" th:placeholder="#{ayasearch}" id="ayasearch"/>
				       
				        <i class="search link icon"></i>
			        </form>
			      </div>
			      
			    </div>
			  </div>
			  <div class="right menu">
			  
			  <div class="item"> 
				  <a id="addmedia" class="linkpoint coloredtext"><i class="icon cloud upload"></i> <span th:text="#{ayalink}"></span></a>
				  <div id="mediaprevlink" ></div>
			  </div>
  
			  
			 </div>
			</div>
			<div class="left ui attached menu inverted yellow">
			  <a class="item active qitem" th:text="#{quran1}" id="q1c"></a>
			  <a class="item qitem" th:text="#{quran2}" id="q2c"></a>
			  <a class="item qitem" th:text="#{quran3}" id="q3c"></a>
			  
			</div>
			<div class="ui attached segment " id="ayacont">
				<p id="s_aya" class="ayacontainer coloredtext"></p>
				<a class="ui right ribbon label ayacontainerdis" id="aya_dis" ></a>
				
			</div>
			<div class="ui attached menu inverted yellow">
				<div class="item"> 
					<div class="ui dropdown booksdd">
					  <span th:text="#{tafseer}" class="text"></span>
					  <i class="book icon"></i>			
					  <div class="menu">
					    <div class="item"  th:each="ele : ${b1}" th:text="${ele.getArName()}" th:id="'s_b_'+${ele.getId()}"></div>
					  </div>
					</div>
				</div>
				
				<div class="item"> 
					<div class="ui dropdown booksdd">
					  <span th:text="#{trans}" class="text"></span>
					  <i class="book icon"></i>			
					  <div class="menu" >
					    <div class="item" th:each="ele : ${b2}" th:text="${ele.getArName()}" th:id="'s_b_'+${ele.getId()}"></div>
					  </div>
					</div>
				</div>
			  
			  <div class="ui icon buttons yellow">
				  <button class="ui button" id="bb_button">
				    <i class="forward icon" ></i>
				  </button>
				  <button class="ui button"  id="play_button">
				    <i class="play icon"></i>
				  </button>
				  <button class="ui button" id="ff_button">
				    <i class="backward icon" ></i>
				  </button>
		     </div>
		     <div class="ui menu inverted yellow">
			     <div class="ui fluid search selection dropdown"  id="readerslist">
				  <input type="hidden" id="selectedreader" />
				  <i class="dropdown icon"></i>
				  <div class="default text" th:text="#{readerlist}"></div>
				  <div class="menu">
				  	<div class="item"  th:each="ele : ${readers}" th:text="${ele.getName()}+' '+${ele.getQuality()}"   th:attr="data-value=${ele.getId()}" ></div>
				  </div>
				</div>
			</div>
			
			</div>
			<div class="ui  attached segment ammrifont coloredtext" id="bookcont">
			</div>

 			<div class="ui modal" id="ayasearchresult">
 				<div class="ui large loader"></div>
 			</div>
 			
 			<div id="jquery_jplayer_1"></div>
			<div id="jquery_jplayer_2"></div>
			<div class="results"></div>
        </div>
    </body>
</html>