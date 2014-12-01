function isMatch(str1,str2)
{ 
	var index = str1.indexOf(str2);
	if(index==-1) return false;
	return true;
}
function ResumeError() {
	return true;
}
window.onerror = ResumeError;
function $(id) {
    return document.getElementByIdx(id);
}
function showMenu (baseID, divID) {
    baseID = $(baseID);
    divID  = $(divID);
    if (showMenu.timer) clearTimeout(showMenu.timer);
    hideCur();
    divID.style.display = 'block';
    showMenu.cur = divID;
    if (! divID.isCreate) {
        divID.isCreate = true;
        divID.onmouseover = function () {
            if (showMenu.timer) clearTimeout(showMenu.timer);
            hideCur();
            divID.style.display = 'block';
        };
        function hide () {
            showMenu.timer = setTimeout(function () {divID.style.display = 'none';}, 1000);
        }
        divID.onmouseout = hide;
        baseID.onmouseout = hide;
    }
    function hideCur () {
		showMenu.cur && (showMenu.cur.style.display = 'none');
	}
}