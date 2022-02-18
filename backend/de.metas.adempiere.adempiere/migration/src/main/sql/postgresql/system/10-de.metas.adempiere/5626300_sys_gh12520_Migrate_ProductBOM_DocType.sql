-- dev-note: set the `PP_Product_BOM` document type to all `pp_product_bom` records
UPDATE pp_product_bom
SET c_doctype_id=541027, datedoc=created, updatedBy=99, updated=TO_TIMESTAMP('2021-02-16 15:53:44', 'YYYY-MM-DD HH24:MI:SS')
WHERE c_doctype_id IS NULL
;

-- dev-note: set the `DocumentNo` where missing
CREATE SEQUENCE DocumentNo_PP_Product_BOM_SEQ INCREMENT 1
;

SELECT SETVAL('DocumentNo_PP_Product_BOM_SEQ', (SELECT (currentnext::bigint) - 1 FROM ad_sequence WHERE ad_sequence_id = 545394))
;

UPDATE pp_product_bom
SET documentno = NEXTVAL('DocumentNo_PP_Product_BOM_SEQ'), updatedBy=99, updated=TO_TIMESTAMP('2021-02-16 15:53:44', 'YYYY-MM-DD HH24:MI:SS')
WHERE documentno IS NULL
;

UPDATE ad_sequence
SET currentnext = NEXTVAL('DocumentNo_PP_Product_BOM_SEQ'), updatedBy=99, updated=TO_TIMESTAMP('2021-02-16 15:53:44', 'YYYY-MM-DD HH24:MI:SS')
WHERE ad_sequence_id = 545394
;

-- dev-note: mark all existing bom versions as completed
UPDATE pp_product_bom
SET docstatus='CO', docaction ='RE', processed = 'Y', updatedBy=99, updated=TO_TIMESTAMP('2021-02-16 15:53:44', 'YYYY-MM-DD HH24:MI:SS')
WHERE docstatus = 'DR'
;

DROP SEQUENCE DocumentNo_PP_Product_BOM_SEQ
;