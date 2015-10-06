function ad_deferRenderBorderLayout(uuid, timeout) {
	var meta = zkau.getMeta($e(uuid));
	if (meta) {
		setTimeout("_ad_deferBDL('"+uuid+"')", timeout);				
	}
}		

function _ad_deferBDL(uuid) {			
	zk.beforeSizeAt();
	zk.onSizeAt();
	zkau.getMeta($e(uuid)).render();
}

function ad_closeBuble(uuid) {
	var cmp = $e(uuid); 
	for(i=0;i<cmp.bandInfos.length;i++){
		cmp.instance.getBand(i).closeBubble();
	}
}

function scrollToRow(uuid){  
	var cmp = $e(uuid);  
	if (!(typeof cmp == "undefined") && !(cmp == null)) {
		cmp.style.display="inline";
		cmp.focus();
		cmp.style.display="none";
	}
}
 