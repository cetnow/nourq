var mediaTypes=null;
var mediaAuth=null;

$(document).ready(function (){
});

function startactions(){
	$.get(options.baseurl + "getMediaTypes", function(data) {
		mediaTypes=data;
		$.get(options.baseurl + "getMediaAuth", function(data) {
			mediaAuth=data;
			filltable(1);
			$('#tpagination').twbsPagination({
		        totalPages: Math.ceil(options.tabledata.length/options.totalonpage),
		        visiblePages: 5,
		        onPageClick: function (event, page) {
		        	filltable(page);
		        }
		    });
		});
	});
}

function filltable(pagenumber){
	var index=(pagenumber-1)*options.totalonpage;
	options.currentpage=index;
	var max=Math.min((options.tabledata.length), index+options.totalonpage);
	var table = $('#tbody');
	table.html('');
	for(var i=index ;i<max ; i++){
		var buttons='';
		var row = $('<tr></tr>');
		row.append($("<td></td>").html(options.tabledata[i].Id));
		row.append($("<td></td>").html('<input type="text" name="FileName'+options.tabledata[i].Id+'" value="'+options.tabledata[i].FileName+'" />'));
		row.append($("<td></td>").html('<a href="'+options.tabledata[i].MediaUrl+'">الرابط</a>'));
		row.append($("<td></td>").html(options.tabledata[i].SuraInfo));
		row.append($("<td></td>").html(options.tabledata[i].End+'-'+options.tabledata[i].Start));
		//row.append($("<td></td>").html('<input type="hidden" name="Type'+options.tabledata[i].Id+'" value="'+options.tabledata[i].Type+'" />'));
		row.append($("<td></td>").html(mediaTypes));
		
		//row.append($("<td></td>").html('<input type="hidden" name="Auther'+options.tabledata[i].Id+'" value="'+options.tabledata[i].Auther+'" />'));
		row.append($("<td></td>").html(mediaAuth));
		buttons='<button class="green ui button pop basic" type="submit" name="sub" value="'+options.tabledata[i].Id+'" data-content="قبول"><i class="checkmark icon"></i></button>';
		buttons+='<button class="basic red ui button pop" type="submit" name="delm" value="'+options.tabledata[i].Id+'" data-content="حذف الملف"><i class="remove icon"></i></button>';
		buttons+='<button class="basic red ui button pop" type="submit" name="delt" value="'+options.tabledata[i].Id+'" data-content="حذف التصنيف"><i class="remove icon"></i></button>';
		buttons+='<button class="basic red ui button pop" type="submit" name="dela" value="'+options.tabledata[i].Id+'" data-content="حذف المحاضر"><i class="remove icon"></i></button>';
		buttons+='<button class="basic red ui button pop " type="submit" name="delall" value="'+options.tabledata[i].Id+'" data-content="حذف الجميع"><i class="remove circle icon"></i></button>';
		row.append($("<td></td>").html('<div class="ui small icon buttons">'+buttons+'</div>'));
		
		table.append(row);
		
		var mtype=$('#typetemplate');
		mtype.attr('id','Type'+options.tabledata[i].Id);
		mtype.attr('name','Type'+options.tabledata[i].Id);
		
		var mtypev=$('#typetemplatevalue');
		mtypev.val(options.tabledata[i].TypeFK);
		mtypev.attr('id','TypeValue'+options.tabledata[i].Id);
		mtypev.attr('name','TypeValue'+options.tabledata[i].Id);
		mtype.dropdown({allowAdditions:true});
		
		var mauth=$('#authtemplate');
		mauth.attr('id','Auther'+options.tabledata[i].Id);
		mauth.attr('name','Auther'+options.tabledata[i].Id);
		
		var mauthv=$('#authtemplatevalue');
		mauthv.val(options.tabledata[i].AutherFK);
		mauthv.attr('id','AutherValue'+options.tabledata[i].Id);
		mauthv.attr('name','AutherValue'+options.tabledata[i].Id);
		mauth.dropdown({allowAdditions:true});
		
		$('.pop').popup();
		
	}
	
}
