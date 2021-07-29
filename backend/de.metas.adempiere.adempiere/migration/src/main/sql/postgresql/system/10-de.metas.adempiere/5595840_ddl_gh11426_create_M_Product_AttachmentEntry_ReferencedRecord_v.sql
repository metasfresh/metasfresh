DROP VIEW IF EXISTS M_Product_AttachmentEntry_ReferencedRecord_v
;

CREATE VIEW M_Product_AttachmentEntry_ReferencedRecord_v AS
    --own attachments
SELECT r.Record_ID                                                               AS M_Product_ID,
       r.AD_Table_ID,
       r.Record_ID,
       r.AD_Attachment_MultiRef_ID                                               AS M_Product_AttachmentEntry_ReferencedRecord_v_ID,
       r.AD_Client_ID,
       r.AD_Org_ID,
       e.AD_AttachmentEntry_ID,
       e.BinaryData,
       e.ContentType,
       LEAST(e.Created, r.Created)                                               AS Created,
       CASE WHEN e.CreatedBy < r.CreatedBy THEN e.CreatedBy ELSE r.CreatedBy END AS CreatedBy,
       e.Description,
       e.FileName,
       CASE WHEN e.IsActive = 'Y' AND r.IsActive = 'Y' THEN 'Y' ELSE 'N' END     AS IsActive,
       e.Type,
       GREATEST(r.Updated, e.Updated)                                            AS Updated,
       CASE WHEN e.Updated > r.Updated THEN e.UpdatedBy ELSE r.UpdatedBy END     AS UpdatedBy,
       e.URL
FROM AD_Attachment_MultiRef r
         JOIN AD_AttachmentEntry e ON e.AD_AttachmentEntry_ID = r.AD_AttachmentEntry_ID
WHERE r.ad_table_id = 208 --M_Product
UNION
--parent attachments
SELECT rel.m_product_id,
       r.AD_Table_ID,
       r.Record_ID,
       r.AD_Attachment_MultiRef_ID                                               AS M_Product_AttachmentEntry_ReferencedRecord_v_ID,
       r.AD_Client_ID,
       r.AD_Org_ID,
       e.AD_AttachmentEntry_ID,
       e.BinaryData,
       e.ContentType,
       LEAST(e.Created, r.Created)                                               AS Created,
       CASE WHEN e.CreatedBy < r.CreatedBy THEN e.CreatedBy ELSE r.CreatedBy END AS CreatedBy,
       e.Description,
       e.FileName,
       CASE WHEN e.IsActive = 'Y' AND r.IsActive = 'Y' THEN 'Y' ELSE 'N' END     AS IsActive,
       e.Type,
       GREATEST(r.Updated, e.Updated)                                            AS Updated,
       CASE WHEN e.Updated > r.Updated THEN e.UpdatedBy ELSE r.UpdatedBy END     AS UpdatedBy,
       e.URL
FROM AD_Attachment_MultiRef r
         JOIN AD_AttachmentEntry e ON e.AD_AttachmentEntry_ID = r.AD_AttachmentEntry_ID
         JOIN M_Product_Relationship rel ON rel.ad_relationtype_id = 'Parent' AND rel.relatedproduct_id = r.record_id
WHERE r.ad_table_id = 208 --M_Product
;
