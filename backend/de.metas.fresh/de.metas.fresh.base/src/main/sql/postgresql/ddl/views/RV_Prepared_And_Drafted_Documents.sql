

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
	


) x
;