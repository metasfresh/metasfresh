-- Process: Lagerwert (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: AD_Org_ID
-- 2025-01-17T13:46:59.124Z
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2025-01-17 15:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541863
;

-- Value: Lagerwert (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-01-17T13:55:00.834Z
UPDATE AD_Process SET SQLStatement='select * from report.M_Cost_CostPrice_Function(p_DateAcct:=''@keydate@''::date, p_M_Product_ID:=@Parameter_M_Product_ID/-1@, p_M_Warehouse_ID:=@Parameter_M_Warehouse_ID/-1@, p_ad_language:=''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-01-17 15:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- Element: ActivityName
-- 2025-01-17T14:06:33.046Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kostenstelle', PrintName='Kostenstelle',Updated=TO_TIMESTAMP('2025-01-17 16:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543886 AND AD_Language='de_CH'
;

-- 2025-01-17T14:06:33.282Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543886,'de_CH') 
;

-- Element: ActivityName
-- 2025-01-17T14:06:45.831Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Activity', PrintName='Activity',Updated=TO_TIMESTAMP('2025-01-17 16:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543886 AND AD_Language='en_US'
;

-- 2025-01-17T14:06:45.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543886,'en_US') 
;

-- Element: ActivityName
-- 2025-01-17T14:06:51.625Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kostenstelle', PrintName='Kostenstelle',Updated=TO_TIMESTAMP('2025-01-17 16:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543886 AND AD_Language='de_DE'
;

-- 2025-01-17T14:06:51.713Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543886,'de_DE') 
;

-- 2025-01-17T14:06:51.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543886,'de_DE') 
;

