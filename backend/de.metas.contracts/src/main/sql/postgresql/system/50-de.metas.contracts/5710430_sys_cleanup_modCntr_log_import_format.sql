-- Run mode: WEBUI

-- 2023-11-14T14:03:07.916Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541938
;

-- 2023-11-14T14:03:11.543Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-11-14T14:03:14.413Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541939
;

-- 2023-11-14T14:03:26.472Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE I_ModCntr_Log DROP COLUMN IF EXISTS ModCntr_Log_ID')
;

-- Column: I_ModCntr_Log.ModCntr_Log_ID
-- 2023-11-14T14:03:26.485Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587523
;

-- 2023-11-14T14:03:26.491Z
DELETE FROM AD_Column WHERE AD_Column_ID=587523
;

-- 2023-11-14T14:04:03.263Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE I_ModCntr_Log DROP COLUMN IF EXISTS Record_ID')
;

-- Column: I_ModCntr_Log.Record_ID
-- 2023-11-14T14:04:03.276Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587524
;

-- 2023-11-14T14:04:03.282Z
DELETE FROM AD_Column WHERE AD_Column_ID=587524
;

-- 2023-11-14T14:04:17.251Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720722
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-11-14T14:04:17.314Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=720722
;

-- 2023-11-14T14:04:17.320Z
DELETE FROM AD_Field WHERE AD_Field_ID=720722
;

-- 2023-11-14T14:04:17.323Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE I_ModCntr_Log DROP COLUMN IF EXISTS AD_Table_ID')
;

-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-11-14T14:04:17.336Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587508
;

-- 2023-11-14T14:04:17.340Z
DELETE FROM AD_Column WHERE AD_Column_ID=587508
;

-- 2023-11-14T14:53:23.722Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541950
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-11-14T14:55:29.410Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620900
;

-- 2023-11-14T14:55:29.422Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721035
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-11-14T14:55:29.427Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=721035
;

-- 2023-11-14T14:55:29.432Z
DELETE FROM AD_Field WHERE AD_Field_ID=721035
;

-- 2023-11-14T14:55:29.477Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE I_ModCntr_Log DROP COLUMN IF EXISTS ContractModuleName')
;

-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-11-14T14:55:29.500Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587525
;

-- 2023-11-14T14:55:29.505Z
DELETE FROM AD_Column WHERE AD_Column_ID=587525
;
