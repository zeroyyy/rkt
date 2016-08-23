function activeParent(top) {
	var parent = $(top).parent().parent();
	if (parent.is("li")) {
		$(parent).addClass("open").addClass("active");
		activeParent(parent);
	}
}

$(function() {
	var url = window.location.href;
	var domain = window.location.protocol + "//" + window.location.host;
	var uri = url.replace(domain, "");
	var top = $("#page-navigation a[href='" + uri + "']").parent();
	$(top).addClass("active");
	activeParent(top);
})
