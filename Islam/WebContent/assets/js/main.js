var options = {
	baseurl : null,
	streamdata : null,
	nextmp3data : null,
	nextstreamdata : null,
	loadbefore : 2,
	lastrowselected : -1,
	tabledata : null,
	selectedAya : 1,
	selectedToAya : -1,
	selectedSura : 1,
	playernumber : 1,
	player1 : null,
	player2 : null,
	mp3data : null,
	booksdata : null,
	nextbooksdata : null,
	filetoread : 0,
	readdelay : 200,
	totalonpage : 10,
	currentpage : 0,
	selectedBook : 4,
	isstoped : true,
	timeouts : [],
	selectedFormat : 1,
	current : 0,
	checktime:false,
	skipplayevent1:false,
	skipplayevent2:false,
	firsttime:false,
	isPrepared:false,
	isPlayer2Loaded:false,
	currentreader:-1,
	allsura:null,
	suraforlink:-1,
	ayafromforlink:-1,
	ayatoforlink:-1,
	slider:null,
	continuousReading:false,
};

function addWaiting(){
	$('#waiting_modal').modal({closable:false});
	$('#waiting_modal').modal('show');
	
}
function removeWaiting(){
	$('#waiting_modal').modal('hide');
}

function fillMessage(cont){
	$('#MessagesBox').html(cont);
}