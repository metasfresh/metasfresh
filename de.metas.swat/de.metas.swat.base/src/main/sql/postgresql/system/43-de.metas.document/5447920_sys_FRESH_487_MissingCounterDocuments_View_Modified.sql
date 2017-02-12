

DROP VIEW IF EXISTS RV_Missing_Counter_Documents;


-- This view is made for C_Order entries and their counter-documents
-- If we start supporting other kind of counter documents in the fututre, this view will have to be updated with the logic on those tables, using unions

CREATE VIEW RV_Missing_Counter_Documents
AS


SELECT 
	doc.AD_Org_ID,
	doc.AD_Client_ID,
	doc.Created,
	doc.CreatedBy,
	doc.Updated,
	doc.UpdatedBy,
	doc.IsActive,
	doc.DocStatus,

	
	doc.AD_Table_ID AS AD_Table_ID,
	doc.Record_ID AS Record_ID,
	doc.DocumentNo AS DocumentNo,
	doc.C_DocType_ID,
	counter_doc.Counter_AD_Table_ID,
	counter_doc.Counter_Record_ID,
	counter_doc.Counter_C_DocType_ID,
	counter_doc.Counter_DocumentNo,
	
	--needed for the transactional window
	doc.Processed
	
	FROM
	
(
SELECT
	o.AD_Org_ID as AD_Org_ID,
	o.AD_Client_ID as AD_Client_ID,
	o.Created as Created,
	o.CreatedBy as CreatedBy,
	o.Updated as Updated,
	o.UpdatedBy as UpdatedBy,
	'Y' :: varchar(1) as IsActive,
	o.DocStatus,
	o.Processed,

	-- This view is considered to have an entry for each document line of the original document that doesn't have a counterpart
	-- For a document with n lines and with no counter document we will actually see n entries, with diferent record_IDs, each pointing to a particular line of the original document
	get_table_id('C_OrderLine') AS AD_Table_ID,
	ol.C_OrderLine_ID AS Record_ID,
	o.C_DocType_ID AS C_DocType_ID,
	dt.DocBaseType,
	o.DocumentNo AS DocumentNo


FROM
	C_Order o
	JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
	
	JOIN C_DocType dt ON o.C_DocType_ID = dt.C_DocType_ID
	
	JOIN AD_Org org ON o.AD_Org_ID = org.AD_Org_ID
	
	JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID 
	
WHERE 
-- The view is defined to not display the already created counter document lines because they are not "Missing".
-- Instead, only create entries for the docuemnt lines that have no referenced document lines.
	ol.Ref_OrderLine_ID IS NULL AND
	dt.IsCreateCounter = 'Y' AND
	-- counter-partner
	EXISTS	
		(
			select 1 from C_BPartner where AD_OrgBP_ID = o.AD_Org_ID
		) AND
	bp.AD_OrgBP_ID > 0 AND
	-- If someone creates an order for its own org's BPartner, the system won't create a counter-doc, but it's not an error
	bp.AD_OrgBP_ID != o.AD_Org_ID

	
) 
doc

 LEFT JOIN

(
	SELECT
		-- On the counter side, generally, there is no document created so everything will be empty.
		-- There is, however, a posibility that the referenced document is created but has no lines or has only a part of the lines created too.
		-- In case the counter line exists, it will be not displayed in this view. That's why the counter document part has the document as record, as opposed to the first part which points to the docuemnt line
		get_table_id('C_Order') AS Counter_AD_Table_ID,
		o.C_Order_ID AS Counter_Record_ID,
		o.C_DocType_ID AS Counter_C_DocType_ID,
		o.DocumentNo AS Counter_DocumentNo,
	
		dt.DocBaseType AS Counter_DocBaseType,
		ol.Ref_OrderLine_ID as Counter_Ref_ID
		
	FROM
		C_Order o
		JOIN C_DocType dt ON  o.C_DocType_ID = dt.C_DocType_ID
		-- left join because the counter document might exist but may not have all the lines created
		LEFT JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
		
)
counter_doc ON doc.Record_ID = counter_doc.Counter_Ref_ID


WHERE 
	(
	-- A condition for counter document to be created is to have a counter doc type defined for the original doc type.
	-- There is, however, the posibility to have counter docBaseType entries that more generally link the doctypes and their counter doc types
		exists
		(
			select 1 from C_DocTypeCounter where isActive = 'Y' and C_DocType_ID = doc.C_DocType_ID 
		)
		or
		exists
		(
			select 1 from C_DocBaseType_Counter where DocBaseType = doc.DocBaseType
		)
	)

;