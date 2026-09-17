-- 2026-06-05T00:00:00Z
-- me03#30066 — Fix EdiSettingBPartner tab (549287) in standard BPartner window (123):
-- - Clear AD_Column_ID (was set to 2893/C_BPartner_ID, but Parent_Column_ID is the correct field for child tabs)
-- - Set Parent_Column_ID=563682 (C_BPartner_ID on C_BPartner_EDI_Setting) to link child records to the parent BPartner

UPDATE AD_Tab
SET AD_Column_ID=NULL,
    Parent_Column_ID=563682,
    Updated=TO_TIMESTAMP('2026-06-05 00:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Tab_ID=549287
;
