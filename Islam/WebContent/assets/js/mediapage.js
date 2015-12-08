var playerInstance=null;
jQuery(document).ready(function($) {
	playerInstance = jwplayer('player_ele');

	$('.mediaclick').click(function (){
		mediasetmedia(this);
	});
});

function mediasetmedia(ele){
	playerInstance.setup({ 
		flashplayer:options.baseurl + "assets/jwplayer-7.1.4/jwplayer.flash.swf",
		file:options.baseurl+"getfile?id="+$(ele).attr('data-value'),
		primary:"flash",
	    type: "mp3",
	    width: 640,
		height: 40,
		//autostart:true,
	  });
	playerInstance.play();
	
	//$(ele).attr('data-value2')
	
}

