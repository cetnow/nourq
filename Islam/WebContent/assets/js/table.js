function startactions(){
	filltable(1);
	$('#pagination-demo').twbsPagination({
        totalPages: Math.ceil(options.tabledata.length/options.totalonpage),
        visiblePages: 5,
        onPageClick: function (event, page) {
        	filltable(page);
        }
    });
	
	$('#tbody').find('tr').click( function(){
		rowselected($(this).index());
	});
}

function filltable(pagenumber){
	var index=(pagenumber-1)*options.totalonpage;
	options.currentpage=index;
	var max=Math.min((options.tabledata.length), index+options.totalonpage);
	var table = $('#tbody');
	table.html('');
	for(var i=index ;i<max ; i++){
		var row = $('<tr></tr>');
		row.append($("<td></td>").html(options.tabledata[i].AyaUthmani));
		row.append($("<td></td>").html(options.tabledata[i].Aya));
		row.append($("<td></td>").html(options.tabledata[i].SuraName));
		table.append(row);
	}
	$('#ayasearchresult').modal('refresh');
	$('#tbody').find('tr').click( function(){
		rowselected($(this).index());
	});
	
}

function rowselected(rowindex){
	if(options.doubleclick){
		stopReading();
		options.lastrowselected=options.currentpage+rowindex;
		options.selectedAya=options.tabledata[options.lastrowselected].Aya;
		options.selectedSura=options.tabledata[options.lastrowselected].Sura;
		options.selectedToAya=options.selectedAya+10;
		
		$('#s_aya').html(options.tabledata[options.lastrowselected].AyaUthmani);
		$('#aya_dis').html('سورة '+options.tabledata[options.lastrowselected].SuraName+' آية '+options.tabledata[options.lastrowselected].Aya);
		$('#ayasearchresult').modal('hide');
		
		prepareReading(false);

	}
	options.doubleclick=!options.doubleclick;
	
}