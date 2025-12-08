-- Run mode: SWING_CLIENT

-- Value: Lagerwert (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-02-17T19:15:57.222Z
UPDATE AD_Process SET EntityType='de.metas.acct', SQLStatement='select * from de_metas_acct.report_InventoryValue(p_DateAcct=>''@keydate@''::date, p_M_Product_ID=>@Parameter_M_Product_ID/-1@, p_M_Warehouse_ID=>@Parameter_M_Warehouse_ID/-1@, p_ad_language=>''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-02-17 19:15:57.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: AD_Org_ID
-- 2025-02-17T19:16:13.008Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541863
;

-- 2025-02-17T19:16:13.032Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541863
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: keydate
-- 2025-02-17T19:16:26.655Z
UPDATE AD_Process_Para SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2025-02-17 19:16:26.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541862
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Parameter_M_Product_ID
-- 2025-02-17T19:16:33.852Z
UPDATE AD_Process_Para SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2025-02-17 19:16:33.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541860
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Parameter_M_Warehouse_ID
-- 2025-02-17T19:16:36.517Z
UPDATE AD_Process_Para SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2025-02-17 19:16:36.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541861
;

-- Name: Lagerwert (Excel)
-- Action Type: P
-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T19:17:01.080Z
UPDATE AD_Menu SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2025-02-17 19:17:01.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=541511
;

-- Run mode: SWING_CLIENT

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T19:43:34.280Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-17 19:43:34.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584744
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T19:43:36.021Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-17 19:43:36.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584744
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T19:43:49.194Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Inventory Value (Excel)',Updated=TO_TIMESTAMP('2025-02-17 19:43:49.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584744
;

-- 2025-02-17T19:43:49.196Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Element: null
-- 2025-02-17T19:44:40.438Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-17 19:44:40.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578114 AND AD_Language='de_DE'
;

-- 2025-02-17T19:44:40.645Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(578114,'de_DE')
;

-- 2025-02-17T19:44:40.654Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578114,'de_DE')
;

-- Element: null
-- 2025-02-17T19:44:41.831Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-17 19:44:41.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578114 AND AD_Language='de_CH'
;

-- 2025-02-17T19:44:41.834Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578114,'de_CH')
;

-- Element: null
-- 2025-02-17T19:44:45.382Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventory Value (Excel)', PrintName='Inventory Value (Excel)',Updated=TO_TIMESTAMP('2025-02-17 19:44:45.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578114 AND AD_Language='en_US'
;

-- 2025-02-17T19:44:45.384Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-17T19:44:45.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578114,'en_US')
;

-- Element: null
-- 2025-02-17T19:59:44.831Z
UPDATE AD_Element_Trl SET Name='Inventory Valuation (Excel)', PrintName='Inventory Valuation (Excel)',Updated=TO_TIMESTAMP('2025-02-17 19:59:44.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578114 AND AD_Language='en_US'
;

-- 2025-02-17T19:59:44.832Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-17T19:59:45.202Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578114,'en_US')
;

-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T19:59:56.691Z
UPDATE AD_Process_Trl SET Name='Inventory Valuation (Excel)',Updated=TO_TIMESTAMP('2025-02-17 19:59:56.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584744
;

-- 2025-02-17T19:59:56.692Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: Lagerwert (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-02-17T20:01:10.540Z
UPDATE AD_Process SET IsReport='Y',Updated=TO_TIMESTAMP('2025-02-17 20:01:10.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- Name: Lagerwert (Excel)
-- Action Type: R
-- Report: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-02-17T20:01:56.879Z
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2025-02-17 20:01:56.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=541511
;

