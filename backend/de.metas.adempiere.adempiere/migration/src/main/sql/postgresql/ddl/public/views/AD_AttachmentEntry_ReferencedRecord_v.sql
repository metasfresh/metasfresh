
DROP VIEW IF EXISTS AD_AttachmentEntry_ReferencedRecord_v;
CREATE VIEW AD_AttachmentEntry_ReferencedRecord_v AS
SELECT 
	r.AD_Table_ID, 
	r.Record_ID,
	r.AD_Attachment_MultiRef_ID AS AD_AttachmentEntry_ReferencedRecord_v_ID,
	r.AD_Client_ID,
	r.AD_Org_ID,
	e.AD_AttachmentEntry_ID,
	e.BinaryData,
	e.ContentType,
	LEAST(e.Created,r.Created) AS Created,
	CASE WHEN e.CreatedBy < r.CreatedBy THEN e.CreatedBy ELSE r.CreatedBy END AS CreatedBy,
	e.Description,
	e.FileName,
	CASE WHEN e.IsActive='Y' AND r.IsActive='Y' THEN 'Y' ELSE 'N' END AS IsActive,
	e.Type,
	GREATEST(r.Updated, e.Updated) AS Updated,
	CASE WHEN e.Updated > r.Updated THEN e.UpdatedBy ELSE r.UpdatedBy END AS UpdatedBy,
	e.URL
FROM AD_Attachment_MultiRef r
	JOIN AD_AttachmentEntry e ON e.AD_AttachmentEntry_ID=r.AD_AttachmentEntry_ID
;
