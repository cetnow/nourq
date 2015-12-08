$(document).ready(function (){
	$.getJSON(options.baseurl +"getallsura", function(data) {
		options.allsura = data;
		fillsura();
	});
	$('#MediaAuth').dropdown({
		allowAdditions:true
  	  });
	$('#MediaType').dropdown({
		allowAdditions:true
  	  });
	
	$( "#youtubeurl").on('input',function (){
		slider($(this).val());
	});
	
	buildoldslide();
	
	
});

function buildoldslide(){
	if($('#End').val()>0){
		var url=$('#youtubeurl').val();
		var youtubeid=getYoutubeId(url);
		$.getJSON("https://www.googleapis.com/youtube/v3/videos?id="+youtubeid+"&key=AIzaSyDYwPzLevXauI-kTSVXTLroLyHEONuF9Rw&part=snippet,contentDetails", function(data) {
			var leng=(convert_time(data.items[0].contentDetails.duration));
			if(options.slider==null){
				options.slider=document.getElementById('slider-limit');
			}else{
				options.slider.noUiSlider.destroy();
			}
			noUiSlider.create(options.slider, {
				start: [ $('#Start').val(), $('#End').val() ],
				behaviour: 'drag',
				connect: true,
				step: 1,
				range: {
					'min': 0,
					'max': leng
				}
			});
			
			var limitFieldMin = document.getElementById('slider-limit-value-min');
			var limitFieldMax = document.getElementById('slider-limit-value-max');
			
			var StartSeconds = document.getElementById('Start');
			var EndSeconds = document.getElementById('End');
			
			var limitFieldLength = document.getElementById('slider-length');
			
			options.slider.noUiSlider.on('update', function( values, handle ){
				(handle ? limitFieldMax : limitFieldMin).value = secondToTime(values[handle]);
				(handle ? EndSeconds : StartSeconds).value = values[handle];
				limitFieldLength.value=secondToTime(values[1]-values[0]);
				
			});
			
		});
		
	}
}

function fillsura(){
	var slist=$('#suraslist');
	var dhtml='';
	for(i=0;i<options.allsura.length;i++){
		dhtml+='<div class="item" data-value="'+options.allsura[i].Id+'">'+options.allsura[i].ArName+'</div>';
	}
	$('#suraslistcont').html(dhtml);
	slist.dropdown({
		onChange : function(value, text, selectedItem) {
			options.suraforlink=value;
			if(options.selectedSura!=value){
				options.selectedAya=1;
			}
			fillayafrom(value);
		}
	,showOnFocus:false
	});
	options.suraforlink=options.selectedSura;
	fillayafrom(options.selectedSura);
	fillayato(options.ayatoforlink);
}
function fillayafrom(sura){
	var slist=$('#surasayafrom');
	slist.dropdown("set selected", 1);
	var dhtml='';
	for(i=0;i<options.allsura[sura-1].AyaCount;i++){
		dhtml+='<div class="item" data-value="'+(i+1)+'">'+(i+1)+'</div>';
	}
	$('#surasayafromcont').html(dhtml);
	slist.dropdown({
		onChange : function(value, text, selectedItem) {
			fillayato(value);
		}
	});
	slist.dropdown("set selected", options.selectedAya);
	slist.dropdown('refresh');
	fillayato(options.selectedAya);
}
function fillayato(from){
	//console.debug('TO:'+(options.allsura[options.suraforlink-1].AyaCount));
	var slist=$('#surasayato');
	
	var dhtml='';
	for(i=from;i<=options.allsura[options.suraforlink-1].AyaCount;i++){
		dhtml+='<div class="item" data-value="'+(i)+'">'+(i)+'</div>';
	}
	$('#surasayatocont').html(dhtml);
	slist.dropdown();
	slist.dropdown("set selected", from);
	slist.dropdown('refresh');
}


function convert_time(duration) {
    var a = duration.match(/\d+/g);

    if (duration.indexOf('M') >= 0 && duration.indexOf('H') == -1 && duration.indexOf('S') == -1) {
        a = [0, a[0], 0];
    }

    if (duration.indexOf('H') >= 0 && duration.indexOf('M') == -1) {
        a = [a[0], 0, a[1]];
    }
    if (duration.indexOf('H') >= 0 && duration.indexOf('M') == -1 && duration.indexOf('S') == -1) {
        a = [a[0], 0, 0];
    }

    duration = 0;

    if (a.length == 3) {
        duration = duration + parseInt(a[0]) * 3600;
        duration = duration + parseInt(a[1]) * 60;
        duration = duration + parseInt(a[2]);
    }

    if (a.length == 2) {
        duration = duration + parseInt(a[0]) * 60;
        duration = duration + parseInt(a[1]);
    }

    if (a.length == 1) {
        duration = duration + parseInt(a[0]);
    }
    var h = Math.floor(duration / 3600);
    var m = Math.floor(duration % 3600 / 60);
    var s = Math.floor(duration % 3600 % 60);
    //return ((h > 0 ? h + ":" + (m < 10 ? "0" : "") : "") + m + ":" + (s < 10 ? "0" : "") + s);
    return duration;
}

function slider(url){
	//http://refreshless.com/nouislider/slider-options/
	var youtubeid=getYoutubeId(url);
	$.getJSON("https://www.googleapis.com/youtube/v3/videos?id="+youtubeid+"&key=AIzaSyDYwPzLevXauI-kTSVXTLroLyHEONuF9Rw&part=snippet,contentDetails", function(data) {
		var leng=(convert_time(data.items[0].contentDetails.duration));
		if(options.slider==null){
			options.slider=document.getElementById('slider-limit');
		}else{
			options.slider.noUiSlider.destroy();
		}

		noUiSlider.create(options.slider, {
			start: [ 0, leng ],
			behaviour: 'drag',
			connect: true,
			step: 1,
			range: {
				'min': 0,
				'max': leng
			}
		});
		
		
		var limitFieldMin = document.getElementById('slider-limit-value-min');
		var limitFieldMax = document.getElementById('slider-limit-value-max');
		
		var StartSeconds = document.getElementById('Start');
		var EndSeconds = document.getElementById('End');
		
		var limitFieldLength = document.getElementById('slider-length');
		
		options.slider.noUiSlider.on('update', function( values, handle ){
			(handle ? limitFieldMax : limitFieldMin).value = secondToTime(values[handle]);
			(handle ? EndSeconds : StartSeconds).value = values[handle];
			limitFieldLength.value=secondToTime(values[1]-values[0]);
			
		});
		
	});
	
	
}
function secondToTime(seconds){
	var sec_num = parseInt(seconds, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    var time    = hours+':'+minutes+':'+seconds;
    return time;
}
function getYoutubeId(url){
	var videoid = url.match(/(?:https?:\/{2})?(?:w{3}\.)?youtu(?:be)?\.(?:com|be)(?:\/watch\?v=|\/)([^\s&]+)/);
	return videoid[1];
}
