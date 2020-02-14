update c_bankstatement
set c_doctype_id = (select dt.c_docType_id 
					from c_doctype dt 
					where (dt.ad_org_id=c_bankstatement.ad_org_id or dt.ad_org_id = 0)
					and dt.DocbaseType = 'CMB' 
					and dt.DocSubType= 'BS' 
					limit 1),
	updatedBy = 99,
	Updated=(select now())
where c_doctype_id = null;