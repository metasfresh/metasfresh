-- 2023-11-01T16:59:12.419Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541367
;

-- 2023-11-01T17:04:41.024Z
UPDATE AD_Index_Table SET Name='AD_Printer_Config_UC_Workspace',Updated=TO_TIMESTAMP('2023-11-01 18:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540772
;

-- 2023-11-01T17:01:30.817Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one record is permitted per Workplace',Updated=TO_TIMESTAMP('2023-11-01 18:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540772 AND AD_Language='en_US'
;

-- 2023-11-01T17:01:30.817Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Pro Arbeitsplatz ist nur ein Datensatz erlaubt',Updated=TO_TIMESTAMP('2023-11-01 18:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540772 AND AD_Language='de_DE'
;

-- 2023-11-01T17:01:45.694Z
UPDATE AD_Index_Table SET ErrorMsg='Pro Arbeitsplatz ist nur ein Datensatz erlaubt',Updated=TO_TIMESTAMP('2023-11-01 18:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540772
;

-- 2023-11-01T16:59:18.786Z
DROP INDEX IF EXISTS ad_printer_config_uc_workspace_hostkey
;

-- 2023-11-01T17:04:42.787Z
CREATE UNIQUE INDEX AD_Printer_Config_UC_Workspace ON AD_Printer_Config (C_Workplace_ID) WHERE IsActive='Y'
;

