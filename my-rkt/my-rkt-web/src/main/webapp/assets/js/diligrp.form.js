$("#form").submit(function(){
	return true;
});

function page(currentPage){
	$("#form input[name='currPage']").val(currentPage);
	$("#form").submit();
}