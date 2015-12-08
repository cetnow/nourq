var lastelement=null;
var tablelength=10;
var playerInstance=null;
$(document).ready(function(){
	$('#types').dropdown();
	$('#authers').dropdown();
	$('#suars').dropdown();
	$('#submit').click(function(){
		$(this).addClass('loading');
	});
	
	playerInstance = jwplayer('player_ele');
});


function startactions(){
	filltable(1);
	$('#tpagination').twbsPagination({
        totalPages: Math.ceil(options.tabledata.length/tablelength),
        visiblePages: 5,
        onPageClick: function (event, page) {
        	filltable(page);
        }
    });
}

function filltable(pagenumber){
	var index=(pagenumber-1)*tablelength;
	options.currentpage=index;
	var max=Math.min((options.tabledata.length), index+tablelength);
	var table = $('#tbody');
	table.html('');
	for(var i=index ;i<max ; i++){
		var buttons='';
		var link='';
		var row = $('<tr></tr>');
		row.append($("<td></td>").html(options.tabledata[i].FileName));
		row.append($("<td></td>").html(options.tabledata[i].SuraInfo));
		row.append($("<td></td>").html(options.tabledata[i].Type));
		row.append($("<td></td>").html(options.tabledata[i].Auther));
		buttons +='<button onclick="mediasetmedia(this)" class="ui button"><i data-value3="'+(options.tabledata[i].FileName+' - '+options.tabledata[i].Type)+'" class="play icon linkpoint" data-value2="'+options.tabledata[i].MediaFileFK+'"></i></button>';
		link=options.baseurl + "getfile?id=" + options.tabledata[i].MediaFileFK;
		buttons +='<a href="'+link+'" class="ui button"><i class="download icon linkpoint" data-value2="'+options.tabledata[i].MediaFileFK+'"></i></a>';
		row.append($('<td class="center aligned"></td>').html('<div class="ui buttons basic">'+buttons+'</div>'));

		table.append(row);
		$('.pop').popup();

	}
	
}

function mediasetmedia(ele){
	ele=$(ele).children();
	lastelement=ele;
	if($(lastelement).hasClass("play")){
		$('.pause').addClass("play");
		$('.pause').removeClass("pause");
		$(lastelement).removeClass("play");
		$(lastelement).addClass("pause");
		
		//$(ele).attr('data-value3')
		playerInstance.setup({ 
			flashplayer:options.baseurl + "assets/jwplayer-7.1.4/jwplayer.flash.swf",
			file:options.baseurl+"getfile?id="+$(ele).attr('data-value2'),
			primary:"flash",
		    type: "mp3",
		    width: 640,
			height: 40,
			autostart:false,
		  });
		playerInstance.play();
		
	}else{
		$(lastelement).removeClass("pause");
		$(lastelement).addClass("play");
		playerInstance.pause();
	}
	
}

