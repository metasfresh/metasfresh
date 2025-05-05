delete from c_aggregationitem where  AD_Column_ID=575016;

-- Run mode: SWING_CLIENT

-- 2023-11-24T10:14:52.203Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716137
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Async Batch
-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-11-24T10:14:52.207Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716137
;

-- 2023-11-24T10:14:52.214Z
DELETE FROM AD_Field WHERE AD_Field_ID=716137
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Async Batch
-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-11-24T10:14:52.231Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=587064
;

-- 2023-11-24T10:14:52.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650292
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Async Batch
-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-11-24T10:14:52.237Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=650292
;

-- 2023-11-24T10:14:52.241Z
DELETE FROM AD_Field WHERE AD_Field_ID=650292
;

-- 2023-11-24T10:14:52.244Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE C_Invoice_Candidate DROP COLUMN IF EXISTS C_Async_Batch_ID')
;

-- Column: C_Invoice_Candidate.C_Async_Batch_ID
-- 2023-11-24T10:14:52.508Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575016
;


DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=591959;
;

-- 2023-11-24T10:14:52.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661204
;

-- 2023-11-24T10:14:52.237Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=661204
;

-- 2023-11-24T10:14:52.241Z
DELETE FROM AD_Field WHERE AD_Field_ID=661204
;

-- 2023-11-24T10:14:52.513Z
DELETE FROM AD_Column WHERE AD_Column_ID=575016
;

-- 2023-11-24T10:15:41.942Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate_Recompute','ALTER TABLE C_Invoice_Candidate_Recompute DROP COLUMN IF EXISTS C_Async_Batch_ID')
;

-- Column: C_Invoice_Candidate_Recompute.C_Async_Batch_ID
-- 2023-11-24T10:15:42.050Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575625
;

-- 2023-11-24T10:15:42.054Z
DELETE FROM AD_Column WHERE AD_Column_ID=575625
;

