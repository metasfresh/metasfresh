-- UI Element: Belegart -> Belegart.Benachrichtigungstext für Belegstatus "Fertiggestellt"
-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-10-26T19:35:09.938Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=618295
;

-- 2023-10-26T19:35:09.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716719
;

-- Field: Belegart -> Belegart -> Benachrichtigungstext für Belegstatus "Fertiggestellt"
-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-10-26T19:35:09.951Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716719
;

-- 2023-10-26T19:35:09.954Z
DELETE FROM AD_Field WHERE AD_Field_ID=716719
;

-- 2023-10-26T19:35:09.983Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE C_DocType DROP COLUMN IF EXISTS CompletedNotification_BoilerPlate_ID')
;

-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-10-26T19:35:10.743Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587149
;

-- 2023-10-26T19:35:10.750Z
DELETE FROM AD_Column WHERE AD_Column_ID=587149
;

