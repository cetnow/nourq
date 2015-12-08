$(document).ready(function (){
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
