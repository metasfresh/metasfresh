-- Run mode: SWING_CLIENT

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20 -> flags.Freigabe erforderlich
-- Column: C_OLCand.IsImportedWithIssues
-- 2025-02-21T13:25:45.577Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547261
;

DELETE
FROM AD_UI_Element
WHERE ad_field_id IN (SELECT ad_field_id FROM ad_field WHERE ad_column_id = 550426)
;

-- 2025-02-21T13:25:45.589Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554017
;

DELETE
FROM AD_Element_Link
WHERE AD_Field_ID IN (SELECT ad_field_id FROM ad_field WHERE ad_column_id = 550426)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Freig.
-- Column: C_OLCand.IsImportedWithIssues
-- 2025-02-21T13:25:45.601Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554017
;

DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID IN (SELECT ad_field_id FROM ad_field WHERE ad_column_id = 550426)
;

-- 2025-02-21T13:25:45.602Z
DELETE FROM AD_Field WHERE AD_Field_ID=554017
;

DELETE
FROM AD_Field
WHERE ad_column_id = 550426
;

-- 2025-02-21T13:25:45.643Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE C_OLCand DROP COLUMN IF EXISTS IsImportedWithIssues')
;

-- Column: C_OLCand.IsImportedWithIssues
-- 2025-02-21T13:25:45.889Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=550426
;

-- 2025-02-21T13:25:45.891Z
DELETE FROM AD_Column WHERE AD_Column_ID=550426
;

-- Run mode: SWING_CLIENT

-- Process: C_OLCand_Update_IsImportedWithIssues_Selection(org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_Selection)
-- Table: C_OLCand
-- EntityType: de.metas.fresh
-- 2025-02-21T16:44:42.217Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540848
;

-- Value: C_OLCand_Update_IsImportedWithIssues_Selection
-- Classname: org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_Selection
-- 2025-02-21T16:44:46.094Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=584741
;

-- 2025-02-21T16:44:46.096Z
DELETE FROM AD_Process WHERE AD_Process_ID=584741
;

-- Process: C_OLCand_Update_IsImportedWithIssues_SingleRplTrx(org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_SingleRplTrx)
-- Table: C_OLCand
-- EntityType: de.metas.fresh
-- 2025-02-21T16:45:50.229Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540434
;

-- Value: C_OLCand_Update_IsImportedWithIssues_SingleRplTrx
-- Classname: org.adempiere.server.rpl.trx.process.C_OLCand_Update_IsImportedWithIssues_SingleRplTrx
-- 2025-02-21T16:45:58.925Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540429
;

-- 2025-02-21T16:45:58.928Z
DELETE FROM AD_Process WHERE AD_Process_ID=540429
;

