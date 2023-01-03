DROP VIEW IF EXISTS AD_Product_Certification_v
;

CREATE VIEW AD_Product_Certification_v AS
SELECT ae.ad_attachmententry_referencedrecord_v_id                                                                  AS AD_Product_Certification_v_ID,
       ae.created,
       ae.createdby,
       ae.isactive,
       ae.updated,
       ae.updatedby,
       ae.filename                                                                                                  AS DocumentName,
       ae.record_id                                                                                                 AS M_Product_Id,
       -- tags are in the form of: 'Tag1=Value1\nTag2=Value2\nTag3=Value3'
       REGEXP_REPLACE(SPLIT_PART(ae.tags, 'documenttype' || '=', 2), '\n.*$', '')                                   AS DocumentType,
       REGEXP_REPLACE(SPLIT_PART(ae.tags, 'documentgroup' || '=', 2), '\n.*$', '')                                  AS DocumentGroup,
       TO_TIMESTAMP(REGEXP_REPLACE(SPLIT_PART(ae.tags, 'validto' || '=', 2), '\n.*$', ''), 'YYYY-MM-DD HH24:MI:SS') AS ValidTo,
       ae.url,
       ae.ad_org_id,
       ae.ad_client_id
FROM ad_attachmententry_referencedrecord_v ae
         -- ad_table_id for M_Product
         INNER JOIN m_product p ON p.m_product_id = ae.record_id AND ae.ad_table_id = 208
WHERE REGEXP_REPLACE(SPLIT_PART(ae.tags, 'documenttype' || '=', 2), '\n.*$', '') != ''
;
