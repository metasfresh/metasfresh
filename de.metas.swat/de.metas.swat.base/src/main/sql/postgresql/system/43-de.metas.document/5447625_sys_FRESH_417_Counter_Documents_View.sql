

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

	
	doc.AD_Table_ID AS AD_Table_ID,
	doc.Record_ID AS Record_ID,
	doc.DocumentNo AS DocumentNo,
	doc.C_DocType_ID,
	counter_doc.Counter_AD_Table_ID,
	counter_doc.Counter_Record_ID,
	counter_doc.Counter_C_DocType_ID,
	counter_doc.Counter_DocumentNo
	
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
	ol.Ref_OrderLine_ID IS NULL AND
	dt.IsCreateCounter = 'Y' AND
	-- counter-partner
	EXISTS	
		(
			select 1 from C_BPartner where AD_OrgBP_ID = o.AD_Org_ID
		) AND
	bp.AD_OrgBP_ID > 0

	
) 
doc

 LEFT JOIN

(
	SELECT
		
		get_table_id('C_Order') AS Counter_AD_Table_ID,
		o.C_Order_ID AS Counter_Record_ID,
		o.C_DocType_ID AS Counter_C_DocType_ID,
		o.DocumentNo AS Counter_DocumentNo,
	
		dt.DocBaseType AS Counter_DocBaseType,
		ol.Ref_OrderLine_ID as Counter_Ref_ID
		
	FROM
		C_Order o
		JOIN C_DocType dt ON  o.C_DocType_ID = dt.C_DocType_ID
		LEFT JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
		
)
counter_doc ON doc.Record_ID = counter_doc.Counter_Ref_ID


WHERE 
	(
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