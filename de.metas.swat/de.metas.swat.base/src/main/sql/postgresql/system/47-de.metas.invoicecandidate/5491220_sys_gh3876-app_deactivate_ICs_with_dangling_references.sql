
/*
SELECT distinct on (AD_Table_ID) 
AD_Table_ID, (select TableName from AD_Table t where t.AD_Table_ID=ic.AD_Table_ID)
from C_Invoice_Candidate ic
260;"C_OrderLine"
320;"M_InOutLine"
322;"M_InventoryLine"
53027;"PP_Order"
540320;"C_Flatrate_Term"
*/
UPDATE C_Invoice_Candidate
SET IsActive='N', Description='Deactivated since the referenced C_OrderLine doesn''t exist; see https://github.com/metasfresh/metasfresh/issues/3876', Updated=now(), UpdatedBy=99
WHERE C_Invoice_Candidate_ID IN (
	select ic.C_Invoice_Candidate_ID
	from C_Invoice_Candidate ic
		left join C_OrderLine ol ON ol.C_OrderLine_ID=ic.Record_ID
	where ic.AD_table_ID=get_table_id('C_OrderLine')
		and ol.C_OrderLine_ID is null
		and ic.isactive='Y' 
);
UPDATE C_Invoice_Candidate
SET IsActive='N', Description='Deactivated since the referenced M_InOutLine doesn''t exist; see https://github.com/metasfresh/metasfresh/issues/3876', Updated=now(), UpdatedBy=99
WHERE C_Invoice_Candidate_ID IN (
	select ic.C_Invoice_Candidate_ID
	from C_Invoice_Candidate ic
		left join M_InoutLine iol ON iol.M_InoutLine_ID=ic.Record_ID
	where ic.AD_table_ID=get_table_id('M_InOutLine')
		and iol.M_InoutLine_ID is null
		and ic.isactive='Y' 
);
UPDATE C_Invoice_Candidate
SET IsActive='N', Description='Deactivated since the referenced M_InventoryLine doesn''t exist; see https://github.com/metasfresh/metasfresh/issues/3876', Updated=now(), UpdatedBy=99
WHERE C_Invoice_Candidate_ID IN (
	select ic.C_Invoice_Candidate_ID
	from C_Invoice_Candidate ic
		left join M_InventoryLine il ON il.M_InventoryLine_ID=ic.Record_ID
	where ic.AD_table_ID=get_table_id('M_InventoryLine')
		and il.M_InventoryLine_ID is null
		and ic.isactive='Y' 
);
UPDATE C_Invoice_Candidate
SET IsActive='N', Description='Deactivated since the referenced 'PP_Order' doesn''t exist; see https://github.com/metasfresh/metasfresh/issues/3876', Updated=now(), UpdatedBy=99
WHERE C_Invoice_Candidate_ID IN (
	select ic.C_Invoice_Candidate_ID
	from C_Invoice_Candidate ic
		left join PP_Order ppo ON ppo.PP_Order_ID=ic.Record_ID
	where ic.AD_table_ID=get_table_id('PP_Order')
		and ppo.PP_Order_ID is null
		and ic.isactive='Y' 
);
UPDATE C_Invoice_Candidate
SET IsActive='N', Description='Deactivated since the referenced C_Flatrate_Term doesn''t exist; see https://github.com/metasfresh/metasfresh/issues/3876', Updated=now(), UpdatedBy=99
WHERE C_Invoice_Candidate_ID IN (
	select ic.C_Invoice_Candidate_ID
	from C_Invoice_Candidate ic
		left join C_Flatrate_Term ft ON ft.C_Flatrate_Term_ID=ic.Record_ID
	where ic.AD_table_ID=get_table_id('C_Flatrate_Term')
		and ft.C_Flatrate_Term_ID is null
		and ic.isactive='Y'
);
