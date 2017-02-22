
/*
Signaling the crawler to stop! The records are:
IsSOTrx=true, seen at Thu Jan 19 11:15:45 CET 2017: TableRecordReference{tableName=C_Invoice_Candidate, recordId=3648207,  (SoftReference-)model=X_C_Invoice_Candidate[C_Invoice_Candidate_ID=3648207, trxName=<<ThreadInherited>>]}
IsSOTrx=false, seen at Thu Jan 19 11:15:48 CET 2017: TableRecordReference{tableName=C_Order, recordId=1164468,  (SoftReference-)model=C_Order[ID=1164468-DocumentNo=865367,IsSOTrx=false,C_DocType_ID=0,GrandTotal=246.0000]}
HU-record, seen at null: null

0: TableRecordReference{tableName=C_Invoice_Candidate, recordId=3648207, (SoftReference-)model=X_C_Invoice_Candidate[C_Invoice_Candidate_ID=3648207, trxName=TrxRun_1ad04ee8-17ff-4333-b0ab-49ec08a2d91e]}
1: TableRecordReference{tableName=C_Order, recordId=1164467, (SoftReference-)model=C_Order[ID=1164467-DocumentNo=10753,IsSOTrx=true,C_DocType_ID=1000030,GrandTotal=0.00]}
2: TableRecordReference{tableName=C_OrderLine, recordId=2059810, (SoftReference-)model=MOrderLine[2059810, Line=20, Ordered=6, Delivered=0, Invoiced=0, Reserved=6, LineNet=0]}
3: TableRecordReference{tableName=C_OrderLine, recordId=2059811, (SoftReference-)model=MOrderLine[2059811, Line=10, Ordered=60.000, Delivered=0, Invoiced=0, Reserved=0, LineNet=240.0000]}
4: TableRecordReference{tableName=C_Order, recordId=1164468, (SoftReference-)model=C_Order[ID=1164468-DocumentNo=865367,IsSOTrx=false,C_DocType_ID=0,GrandTotal=246.0000]}
*/
/*
-- references ol_2 via ol_1.C_PackingMaterial_OrderLine_ID, but ol_2 belongs to a different C_Order
SELECT 
	ol_1.C_Order_ID, ol_1.C_OrderLine_ID, ol_1.C_PackingMaterial_OrderLine_ID, ol_1.updated,
	ol_2.C_Order_ID, ol_2.C_OrderLine_ID, ol_2.C_PackingMaterial_OrderLine_ID, ol_2.updated
FROM C_OrderLine ol_1
	JOIN C_OrderLine ol_2 ON ol_1.C_PackingMaterial_OrderLine_ID = ol_2.C_OrderLine_ID AND ol_1.C_Order_ID!=ol_2.C_Order_ID
ORDER BY ol_1.C_OrderLine_ID DESC
--localhost: 29 rows, the last one from "2016-10-12 14:08:20+02" (C_OrderLine_ID=2059811)
--erpprod: 7 rows, the last one from ""2016-07-05 07:38:00+02"" (C_OrderLine_ID=2015748)
*/

--C_OrderLine.C_PackingMaterial_OrderLine_ID
UPDATE AD_Column SET IsCalculated='Y', Updated=now() WHERE AD_Column_ID=550242;
--C_OrderLine.IsPackagingMaterial
UPDATE AD_Column SET IsCalculated='Y', Updated=now() WHERE AD_Column_ID=549803;

UPDATE C_OrderLine
SET UpdatedBy=99, Updated=now(), C_PackingMaterial_OrderLine_ID=null
WHERE C_OrderLine_ID IN (
SELECT 
	ol_1.C_OrderLine_ID
FROM C_OrderLine ol_1 -- references ol_2 via ol_1.C_PackingMaterial_OrderLine_ID, but ol_2 belongs to a different C_Order
	JOIN C_OrderLine ol_2 ON ol_1.C_PackingMaterial_OrderLine_ID = ol_2.C_OrderLine_ID AND ol_1.C_Order_ID!=ol_2.C_Order_ID
);
-- tested on localhost
