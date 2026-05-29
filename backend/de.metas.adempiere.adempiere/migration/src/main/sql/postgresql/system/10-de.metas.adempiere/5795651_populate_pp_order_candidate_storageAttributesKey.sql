SELECT backup_table('pp_order_candidate')
;

UPDATE pp_order_candidate
SET storageattributeskey = GenerateASIStorageAttributesKey(m_attributesetinstance_id),
    updated              = TO_TIMESTAMP('2026-03-25 11:11:11.000000', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby            = 99
WHERE storageattributeskey IS NULL
;

