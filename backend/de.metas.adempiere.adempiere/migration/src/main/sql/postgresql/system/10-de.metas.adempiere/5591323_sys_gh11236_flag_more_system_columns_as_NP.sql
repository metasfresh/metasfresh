
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_ConversionType'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('LineAggregationKey_Suffix', 'HeaderAggregationKey','LineAggregationKey','HeaderAggregationKey_Calc','InvoiceScheduleAmtStatus','SchedulerResult')
  and ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Invoice_Candidate'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('TransactionIdAPI');

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('SinglePriceTag_ID','Status','ProductDescription','POReference')
  and ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('M_ShipmentSchedule'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('Note')
  and ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('C_Invoice_Line_Alloc'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('R_RequestProcessor','R_RequestProcessor_Route','R_RequestType','R_RequestType_Trl','R_StandardResponse','R_StatusCategory','R_Status_Trl'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE columnname in ('R_RequestType_InternalName')
  and ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('R_Request'));


update ad_table set isview='Y', updated='2021-06-11 08:44', updatedby=99 where tablename='RV_Umsatz_Netto_VP';

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('EXP_Format','EXP_FormatLine'));

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id IN (SELECT ad_table_id FROM ad_table WHERE tablename IN ('ES_FTS_Index','ES_FTS_IndexInclude','ES_FTS_Template'));
