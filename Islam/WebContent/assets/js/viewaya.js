$(document).ready(
		function() {
			
			$('#dosearch').click(function(){
				$('#ayasearchresult').modal('show');
				$.get(options.baseurl + "searchaya", {
					query : $(this).val()
				}, function(data) {
					$("#ayasearchresult").html(data);
				});
			});
			
			$.post(options.baseurl +"getcurrentreader",function(data) {
				options.currentreader=data;
				$('#readerslist').dropdown("set selected", options.currentreader);
				$('#readerslist').dropdown('refresh');
				
			});
			
			$('#readerslist').dropdown({
				onChange : function(value, text, selectedItem) {
						$.post(options.baseurl +"updatereader", {readerId:value},function(data) {
							if(!isPaused()){
								stopReading();
							}
							prepareReading(!isPaused());
					});
				}
			});
			
			$('.booksdd').dropdown(
					{
						onChange : function(value, text, selectedItem) {
							options.selectedBook = (selectedItem.attr('id')
									.replace('s_b_', ''));
							reloadAllFromCurrent();
						}
					});

			$('.button').popup();

			$('.qitem').on('click', function() {
				$(this).addClass('active').siblings().removeClass('active');
				if ($(this).attr('id') == 'q1c') {
					options.selectedFormat = 1;
					fillAyaText(options.current);
				}

				if ($(this).attr('id') == 'q2c') {
					options.selectedFormat = 2;
					fillAyaText(options.current);
				}

				if ($(this).attr('id') == 'q3c') {
					options.selectedFormat = 3;
					fillAyaText(options.current);
				}
			});

			$('#ayasearch').keypress(function(e) {
				if (e.which == 13) {
					$('#ayasearchresult').modal('show');
					$.get(options.baseurl + "searchaya", {
						query : $(this).val()
					}, function(data) {
						$("#ayasearchresult").html(data);
					});
				}
			});

			options.player1 = $("#jquery_jplayer_1").jPlayer({
				swfPath : options.baseurl + "assets/jplayer",
				preload:'auto',
				ended : function() {
					if(options.continuousReading){
						loadNextToPlayer();
					}
					
				},
				timeupdate:function(event){
			         if(options.checktime && event.jPlayer.status.currentTime>0){
			        	 console.log('play1');
			           setAllTimeOut(options.mp3data[1].Data,1,0);
			           options.checktime=false;
			         }
			       },
				supplied : "mp3"
			});

			$("#play_button").click(function() {
				if ($(this).children().hasClass("pause")) {
					stopReading();
				} else {
					StartPlay();
				}

			});
			
			$("#ff_button").click(function() {
				addWaiting();
				stopReading();
				var playNow=false;
				if(options.streamdata.ayatcount==options.selectedAya){
					options.selectedAya=1;
					options.selectedSura=options.selectedSura==114?1:options.selectedSura+1;
				}else{
					options.selectedAya++;
				}
				prepareReading(playNow);
			});
			
			$("#bb_button").click(function() {
				addWaiting();
				stopReading();
				var playNow=false;
				if(options.selectedAya==1){
					options.selectedSura=options.selectedSura==1?114:options.selectedSura-1;
					$.getJSON(options.baseurl + "getsreaminfo", {
						selectedBook : options.selectedBook,
						sura : options.selectedSura,
						fromAya : 1
					}, function(data) {
						options.selectedAya= data.ayatcount;
						prepareReading(playNow);
					});
					
				}else{
					options.selectedAya--;
					prepareReading(playNow);
				}
				
			});
			
			prepareReading(false);
			$('#s_b_4').addClass('active selected');
			
			$( "#searchform" ).submit(function( event ) {
				  event.preventDefault();
			});
			
		});

function loadNextToPlayer(){
	console.log('try');
	if(options.nextstreamdata==null || options.nextbooksdata==null || options.nextmp3data==null){
		setTimeout(loadNextToPlayer(), 1000);
	}else{
		options.streamdata = options.nextstreamdata;
		options.booksdata = options.nextbooksdata;
		options.mp3data = options.nextmp3data;
		options.nextstreamdata = null;
		options.player1.jPlayer("setMedia", {
			mp3 : options.streamdata.mp3URL
		});
		play1Event();
		options.player1.jPlayer("play");
	}
}
function  StartPlay(){
	options.continuousReading=true;
	$("#play_button").children().removeClass("play");
	$("#play_button").children().addClass("pause");
	if(options.isPrepared){
		startReading();
	}else{
		prepareReading(true);
	}
}
function startReading(){
	play1Event();
	options.player1.jPlayer("play");
}

function play1Event(){
	options.checktime=true;
	options.current = 0;
}
function prepareReading(withPlay) {
	addWaiting();
	options.isPrepared=true;
	options.playernumber = 1;
	options.current=0;
	$.getJSON(options.baseurl + "getsreaminfo", {
		selectedBook : options.selectedBook,
		sura : options.selectedSura,
		fromAya : options.selectedAya
	}, function(data) {
		options.streamdata = data;
		options.player1.jPlayer("setMedia", {
			mp3 : options.streamdata.mp3URL
		});

		$.getJSON(options.streamdata.ayatdata, function(data) {
			options.mp3data = data;
			options.nextstreamdata == null;
			if(withPlay){
				startReading();
			}else{
				fillAyaText(0);
			}
			removeWaiting();
		});
		if (options.selectedBook > 0) {
			$.getJSON(options.streamdata.bookdata,{selectedBook:options.selectedBook}, function(data) {
				options.booksdata = data;
			});
		}
		setBookData();
	});
}

function setAllTimeOut(filedata, index, initLength) {
	fillAyaText(0);
	if(filedata.length>1){
		var filelength = initLength;
		for (var i = 0; i < filedata.length - 1; i++) {
			filelength += (filedata[i].FileLength) / 1000;
			setOnce(i + index, filelength);
		}
	}else{
		loopStream();
	}
	
}

function setOnce(index, length) {
	options.timeouts.push(setTimeout(function() {
		fillAyaText(index);
		console.log('set');
		loopStream();
	}, (length)));
}

function loopStream() {
	console.log('loop');
	if (options.nextstreamdata == null
			&& ((options.streamdata.to - options.streamdata.from) <= options.loadbefore)) {
		var nextsura = options.streamdata.remaining == 0 ? options.streamdata.sura == 114 ? 1
				: options.streamdata.sura + 1
				: options.streamdata.sura;
		var formAya = options.streamdata.remaining == 0 ? 1
				: options.streamdata.to + 1;

		$.getJSON(options.baseurl + "getsreaminfo", {
			selectedBook : options.selectedBook,
			sura : nextsura,
			fromAya : formAya
		}, function(data) {
			options.nextstreamdata = data;
			$.getJSON(options.nextstreamdata.ayatdata, function(data) {
				options.nextmp3data = data;

			});
			if (options.selectedBook > 0) {
				$.getJSON(options.nextstreamdata.bookdata,{selectedBook:options.selectedBook}, function(data) {
					options.nextbooksdata = data;
				});
			}
		});
	}

	options.streamdata.from++;
	options.current++;
}

function fillAyaText(fileindex) {
	options.selectedSura = options.mp3data[1].Data[fileindex].Sura;
	options.selectedAya = options.mp3data[1].Data[fileindex].Aya;
	$('#addmedia').attr('href',options.baseurl+'addmedia?aya='+options.selectedAya+'&sura='+options.selectedSura);
	//console.log('Sura:'+options.selectedSura+'Aya:'+options.selectedAya);
	options.timeouts.shift();
	var ayatext = options.selectedFormat == 1 ? options.mp3data[1].Data[fileindex].AyaUthmani
			: options.selectedFormat == 2 ? options.mp3data[1].Data[fileindex].AyaUthmaniMin
					: options.mp3data[1].Data[fileindex].AyaClean;
	$('#s_aya').html(ayatext);
	$('#aya_dis').html(
			'سورة ' + options.mp3data[1].Data[fileindex].suraname + ' آية '
					+ options.mp3data[1].Data[fileindex].Aya);
	if (options.selectedBook > 0 && options.booksdata != null) {
		$('#bookcont').html(options.booksdata[fileindex].Content);

	}
	
	if(options.mp3data[1].Data[fileindex].MediaCount>0){
		var link=options.baseurl+'ayamediainfo?sura='+options.mp3data[1].Data[fileindex].Sura+'&aya='+options.mp3data[1].Data[fileindex].Aya;
		$('#ayamedire').html('<a href="'+link+'">يوجد '+options.mp3data[1].Data[fileindex].MediaCount+' شرح /شروحات مرتبطة بالاية الحاليه لمشاهدتها يرجى الضغط هنا</a>');
		$('#mediaprevlink').html('<a id="mediaprevlinka" data-variation="basic" data-content="مشاهدة الشروحات للاية" class="floating circular ui yellow label" href="'+link+'">'+options.mp3data[1].Data[fileindex].MediaCount+'</a>');
		$('#mediaprevlinka').popup();
	}else{
		$('#mediaprevlink').html('');
	}
}

function stopReading() {

	if (!isPaused()) {
		options.continuousReading=false;
		
		options.player1.jPlayer("stop");

		for (var i = 0; i < options.timeouts.length; i++) {
			clearTimeout(options.timeouts.shift());
		}

		$("#play_button").children().removeClass("pause");
		$("#play_button").children().addClass("play");
		options.nextstreamdata = null;
		options.isPrepared=false;
	}
	
}

function reloadAllFromCurrent() {
	if (options.streamdata != null) {
		setBookData();
	}
}

function setBookData() {
	
	if (options.selectedBook > 0) {
		addWaiting();
		$.getJSON(options.streamdata.bookdata, {
			selectedBook : options.selectedBook
		}, function(data) {
			options.booksdata = data;
			$('#bookcont').html(options.booksdata[options.current].Content);
			removeWaiting();
		});
	}
}

function isMobile() {
	var isMobile = false;
	if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i
			.test(navigator.userAgent)
			|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
					.test(navigator.userAgent.substr(0, 4)))
		isMobile = true;
	return isMobile;
}
function isPaused() {
	return $('#jquery_jplayer_1').data('jPlayer').status.paused;
}

