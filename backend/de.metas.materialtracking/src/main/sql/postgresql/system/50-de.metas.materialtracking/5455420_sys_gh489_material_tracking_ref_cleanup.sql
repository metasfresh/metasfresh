/*
Starting at start record=TableRecordReference{tableName=C_Invoice, recordId=1030471}, searching for goal record=TableRecordReference{tableName=M_CostDetail, recordId=1581108}
Search done. Found goal record: true
Records that make up the path from start to goal (size=7):
0: TableRecordReference{tableName=C_Invoice, recordId=1030471, (SoftReference-)model=MInvoice[1030471-146952,GrandTotal=153.47]}
1: TableRecordReference{tableName=C_AllocationLine, recordId=1017000, (SoftReference-)model=MAllocationLine[1017000,C_Payment_ID=1007600,C_Invoice_ID=1030471,C_BPartner_ID=2000650, Amount=153.470,Discount=0.000,WriteOff=0,OverUnder=0.000]}
2: TableRecordReference{tableName=M_Material_Tracking_Ref, recordId=1022178, (SoftReference-)model=X_M_Material_Tracking_Ref[M_Material_Tracking_Ref_ID=1022178, trxName=TrxRun_53eb9578-a51b-45d1-bb2a-deaaf32ad532]}
3: TableRecordReference{tableName=M_Material_Tracking, recordId=1000121, (SoftReference-)model=X_M_Material_Tracking[M_Material_Tracking_ID=1000121, trxName=TrxRun_53eb9578-a51b-45d1-bb2a-deaaf32ad532]}
4: TableRecordReference{tableName=M_Material_Tracking_Ref, recordId=1016488, (SoftReference-)model=X_M_Material_Tracking_Ref[M_Material_Tracking_Ref_ID=1016488, trxName=TrxRun_53eb9578-a51b-45d1-bb2a-deaaf32ad532]}
5: TableRecordReference{tableName=C_OrderLine, recordId=1358670, (SoftReference-)model=MOrderLine[1358670, Line=10, Ordered=18000.000, Delivered=22502.000, Invoiced=0, Reserved=0.000, LineNet=18000.00]}
6: TableRecordReference{tableName=M_CostDetail, recordId=1581108, (SoftReference-)model=MCostDetail[1581108,C_OrderLine_ID=1358670,Amt=437.00000,Qty=437.000]}
*/

/*
select mtr_al.*
from M_Material_tracking_Ref mtr_al
	JOIN C_AllocationLine al ON mtr_al.Record_ID=al.C_AllocationLine_ID and mtr_al.AD_Table_ID=get_table_id('C_AllocationLine') -- and i.IsSOTrx='Y'
		JOIN C_Invoice i ON i.C_Invoice_ID=al.C_Invoice_ID and i.IsSOTrx='Y'
ORDER BY mtr_al.Created desc
--139 records, the latest from from "2015-09-07 17:33:10+02" (M_Material_Tracking_Ref_ID=1023293)
--checked on erpprod
*/

-- tested on localhost: 139 deleted
DELETE FROM M_Material_tracking_Ref
WHERE M_Material_tracking_Ref_ID IN (
	select mtr_al.M_Material_tracking_Ref_ID
	from M_Material_tracking_Ref mtr_al
		JOIN C_AllocationLine al ON mtr_al.Record_ID=al.C_AllocationLine_ID and mtr_al.AD_Table_ID=get_table_id('C_AllocationLine') -- and i.IsSOTrx='Y'
			JOIN C_Invoice i ON i.C_Invoice_ID=al.C_Invoice_ID and i.IsSOTrx='Y'
	ORDER BY mtr_al.Created desc
);
