

DROP VIEW IF EXISTS RV_Prepared_And_Drafted_Documents;


CREATE VIEW RV_Prepared_And_Drafted_Documents
AS
SELECT
x.AD_Org_ID, x.AD_Client_ID, x.Created, x.IsActive, x.Updated, x.UpdatedBy,
x.C_doctype_ID, x.documentno, x.DateDoc, x.docstatus, x.createdBy, x.ad_table_id, x.record_id
FROM
(

	-- C_Invoice
	(
		select 
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			(case when doc.C_docType_ID > 0 then doc.C_docType_ID else doc.C_doctypeTarget_ID end) as  C_doctype_ID,
			doc.documentno, 
			doc.dateInvoiced as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Invoice') as ad_table_id, 
			doc.C_Invoice_ID as record_id
		from C_Invoice doc
		where doc.docstatus  IN ('DR', 'PR')
	)


	UNION
	-- C_Order
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			(case when doc.C_docType_ID > 0 then doc.C_docType_ID else doc.C_doctypeTarget_ID end) as  C_doctype_ID,
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Order') as ad_table_id, 
			doc.C_Order_ID as record_id
		from C_Order doc
		where doc.docstatus  IN ('DR', 'PR')
		
	)
	
/* 	-- C_AdvComDoc
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.C_DocTypeTarget_ID, 
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_AdvComDoc') as ad_table_id, 
			doc.C_AdvComDoc_ID as record_id
		from C_AdvComDoc doc
		
	) */
	

	--C_AllocationHdr
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.dateTrx as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_AllocationHdr') as ad_table_id, 
			doc.C_AllocationHdr_ID as record_id
		from C_AllocationHdr doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_BankStatement	
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.statementDate as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_BankStatement') as ad_table_id, 
			doc.C_BankStatement_ID as record_id
		from C_BankStatement doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Cash
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.statementDate as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Cash') as ad_table_id, 
			doc.C_Cash_ID as record_id
		from C_Cash doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	/* --C_Contract_Term_Alloc SQL DOCSTATUS COLUMN!!!!!!!
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.C_DocTypeTarget_ID, 
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Contract_Term_Alloc') as ad_table_id, 
			doc.C_Contract_Term_Alloc_ID as record_id
		from C_Contract_Term_Alloc doc
		
	) */
	--C_Doc_Outbound_Log
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.created as DateDoc,  -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Doc_Outbound_Log') as ad_table_id, 
			doc.C_Doc_Outbound_Log_ID as record_id
		from C_Doc_Outbound_Log doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Doc_Outbound_Log_Line
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.created as DateDoc, -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Doc_Outbound_Log_Line') as ad_table_id, 
			doc.C_Doc_Outbound_Log_Line_ID as record_id
		from C_Doc_Outbound_Log_Line doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Flatrate_Conditions
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.created as DateDoc, -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Flatrate_Conditions') as ad_table_id, 
			doc.C_Flatrate_Conditions_ID as record_id
		from C_Flatrate_Conditions doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Flatrate_DataEntry
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.created as DateDoc, -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Flatrate_DataEntry') as ad_table_id, 
			doc.C_Flatrate_DataEntry_ID as record_id
		from C_Flatrate_DataEntry doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Flatrate_Term 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.created as DateDoc, -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Flatrate_Term') as ad_table_id, 
			doc.C_Flatrate_Term_ID as record_id
		from C_Flatrate_Term doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--C_Flatrate_Transition 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.created as DateDoc, -- there was no other date
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Flatrate_Transition') as ad_table_id, 
			doc.C_Flatrate_Transition_ID as record_id
		from C_Flatrate_Transition doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	/* --C_Contract_Term_Alloc TO CHECK WHY IT SAID IT HAD A DOCSTATUS COLUMN!!!!!!!
	--C_Invoice_Line_Alloc 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Invoice_Line_Alloc') as ad_table_id, 
			doc.C_Invoice_Line_Alloc_ID as record_id
		from C_Invoice_Line_Alloc doc
		
	)
	*/
	--C_Order_Line_Alloc  COLUIUMN SQL !

	/*
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.C_DocTypeTarget_ID, 
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Order_Line_Alloc') as ad_table_id, 
			doc.C_Order_Line_Alloc_ID as record_id
		from C_Order_Line_Alloc doc
		
	)

	*/
	--C_Payment 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateTrx as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('C_Payment') as ad_table_id, 
			doc.C_Payment_ID as record_id
		from C_Payment doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--DD_Order 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('DD_Order') as ad_table_id, 
			doc.DD_Order_ID as record_id
		from DD_Order doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--Fact_Acct  I THINK THIS IS NOT NEEDED> FactAcct is not a doc
	/*
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 asC_doctype_ID, 
			'' as documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('Fact_Acct') as ad_table_id, 
			doc.Fact_Acct_ID as record_id
		from Fact_Acct doc
		
	)
	*/ 
	--GL_Journal 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateDoc as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('GL_Journal') as ad_table_id, 
			doc.GL_Journal_ID as record_id
		from GL_Journal doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--GL_JournalBatch 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateDoc as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('GL_JournalBatch') as ad_table_id, 
			doc.GL_JournalBatch_ID as record_id
		from GL_JournalBatch doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--HR_Process 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			(case when doc.C_docType_ID > 0 then doc.C_docType_ID else doc.C_doctypeTarget_ID end) as  C_doctype_ID,
			doc.documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('HR_Process') as ad_table_id, 
			doc.HR_Process_ID as record_id
		from HR_Process doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_InOut 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_InOut') as ad_table_id, 
			doc.M_InOut_ID as record_id
		from M_InOut doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_InOutConfirm 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_InOutConfirm') as ad_table_id, 
			doc.M_InOutConfirm_ID as record_id
		from M_InOutConfirm doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_Inventory 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.movementDate as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_Inventory') as ad_table_id, 
			doc.M_Inventory_ID as record_id
		from M_Inventory doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_Movement 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.movementDate as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_Movement') as ad_table_id, 
			doc.M_Movement_ID as record_id
		from M_Movement doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_MovementConfirm 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_MovementConfirm') as ad_table_id, 
			doc.M_MovementConfirm_ID as record_id
		from M_MovementConfirm doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_ReceiptSchedule_Alloc COLUMN SQL 
	/*
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.C_DocTypeTarget_ID, 
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_ReceiptSchedule_Alloc') as ad_table_id, 
			doc.M_ReceiptSchedule_Alloc_ID as record_id
		from M_ReceiptSchedule_Alloc doc
		
	)
	*/ 
	--M_Requisition 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_Requisition') as ad_table_id, 
			doc.M_Requisition_ID as record_id
		from M_Requisition doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_RMA 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_RMA') as ad_table_id, 
			doc.M_RMA_ID as record_id
		from M_RMA doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--M_ShipperTransportation 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			doc.C_DocType_ID,
			doc.documentno, 
			doc.dateDoc as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('M_ShipperTransportation') as ad_table_id, 
			doc.M_ShipperTransportation_ID as record_id
		from M_ShipperTransportation doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--PP_Cost_Collector 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			(case when doc.C_docType_ID > 0 then doc.C_docType_ID else doc.C_doctypeTarget_ID end) as  C_doctype_ID,
			doc.documentno, 
			doc.movementDate as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('PP_Cost_Collector') as ad_table_id, 
			doc.PP_Cost_Collector_ID as record_id
		from PP_Cost_Collector doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--PP_MRP 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('PP_MRP') as ad_table_id, 
			doc.PP_MRP_ID as record_id
		from PP_MRP doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--PP_Order 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			(case when doc.C_docType_ID > 0 then doc.C_docType_ID else doc.C_doctypeTarget_ID end) as  C_doctype_ID,
			doc.documentno, 
			doc.dateOrdered as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('PP_Order') as ad_table_id, 
			doc.PP_Order_ID as record_id
		from PP_Order doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--PP_Order_Node 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			'' as documentno, 
			doc.created as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('PP_Order_Node') as ad_table_id, 
			doc.PP_Order_Node_ID as record_id
		from PP_Order_Node doc
		where doc.docstatus  IN ('DR', 'PR')
	)
	--S_TimeExpense 
	UNION
	(
		select
			doc.AD_Org_ID, doc.AD_Client_ID, doc.Created, doc.IsActive, doc.Updated, doc.UpdatedBy, 
			-1 as C_doctype_ID, 
			doc.documentno, 
			doc.datereport as DateDoc, 
			doc.docstatus, 
			doc.createdBy, 
			get_Table_ID('S_TimeExpense') as ad_table_id, 
			doc.S_TimeExpense_ID as record_id
		from S_TimeExpense doc
		where doc.docstatus  IN ('DR', 'PR')
	)


) x
;

