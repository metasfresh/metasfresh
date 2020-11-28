
-- 2019-01-09T17:54:23.556
-- #298 changing anz. stellen
UPDATE AD_Process SET Classname='de.metas.contracts.process.C_Customer_Retention_CreateMissing', Name='C_Customer_Retention_CreateMissing', Value='C_Customer_Retention_CreateMissing',Updated=TO_TIMESTAMP('2019-01-09 17:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541037
;

-- 2019-01-09T17:54:23.574
-- #298 changing anz. stellen
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='C_Customer_Retention_CreateMissing',Updated=TO_TIMESTAMP('2019-01-09 17:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541170
;

-- 2019-01-09T17:55:14.002
-- #298 changing anz. stellen
UPDATE AD_Process SET Classname='de.metas.contracts.process.C_Customer_Retention_Update', Name='C_Customer_Retention_Update', Value='C_Customer_Retention_Update',Updated=TO_TIMESTAMP('2019-01-09 17:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541036
;

-- 2019-01-09T17:55:14.011
-- #298 changing anz. stellen
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='C_Customer_Retention_Update',Updated=TO_TIMESTAMP('2019-01-09 17:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541169
;

-- 2019-01-09T17:56:43.810
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:56:43','YYYY-MM-DD HH24:MI:SS'),Name='Create Missing Customer Retention',PrintName='Create Missing Customer Retention' WHERE AD_Element_ID=575921 AND AD_Language='de_CH'
;

-- 2019-01-09T17:56:43.888
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'de_CH') 
;

-- 2019-01-09T17:56:52.345
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:56:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create Missing Customer Retention',PrintName='Create Missing Customer Retention' WHERE AD_Element_ID=575921 AND AD_Language='de_DE'
;

-- 2019-01-09T17:56:52.352
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'de_DE') 
;

-- 2019-01-09T17:56:52.436
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(575921,'de_DE') 
;

-- 2019-01-09T17:56:52.442
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName=NULL, Name='Create Missing Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575921
;

-- 2019-01-09T17:56:52.445
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Create Missing Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575921 AND IsCentrallyMaintained='Y'
;

-- 2019-01-09T17:56:52.448
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Create Missing Customer Retention', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575921) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575921)
;

-- 2019-01-09T17:56:52.456
-- #298 changing anz. stellen
UPDATE AD_PrintFormatItem pi SET PrintName='Create Missing Customer Retention', Name='Create Missing Customer Retention' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575921)
;

-- 2019-01-09T17:56:52.461
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='Create Missing Customer Retention', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575921
;

-- 2019-01-09T17:56:52.465
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='Create Missing Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID = 575921
;

-- 2019-01-09T17:56:52.467
-- #298 changing anz. stellen
UPDATE AD_Menu SET Name='Create Missing Customer Retention', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575921
;

-- 2019-01-09T17:57:45.442
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:57:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Update Customer Retention',PrintName='Update Customer Retention' WHERE AD_Element_ID=575920 AND AD_Language='de_DE'
;

-- 2019-01-09T17:57:45.447
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575920,'de_DE') 
;

-- 2019-01-09T17:57:45.542
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(575920,'de_DE') 
;

-- 2019-01-09T17:57:45.546
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName=NULL, Name='Update Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575920
;

-- 2019-01-09T17:57:45.549
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Update Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID=575920 AND IsCentrallyMaintained='Y'
;

-- 2019-01-09T17:57:45.551
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='Update Customer Retention', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575920) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575920)
;

-- 2019-01-09T17:57:45.560
-- #298 changing anz. stellen
UPDATE AD_PrintFormatItem pi SET PrintName='Update Customer Retention', Name='Update Customer Retention' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575920)
;

-- 2019-01-09T17:57:45.563
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='Update Customer Retention', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575920
;

-- 2019-01-09T17:57:45.566
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='Update Customer Retention', Description=NULL, Help=NULL WHERE AD_Element_ID = 575920
;

-- 2019-01-09T17:57:45.568
-- #298 changing anz. stellen
UPDATE AD_Menu SET Name='Update Customer Retention', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575920
;

-- 2019-01-09T17:58:24.236
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:58:24','YYYY-MM-DD HH24:MI:SS'),Name='Create Missing Customer Retention',PrintName='Create Missing Customer Retention' WHERE AD_Element_ID=575921 AND AD_Language='en_US'
;

-- 2019-01-09T17:58:24.245
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'en_US') 
;

-- 2019-01-09T17:58:27.601
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:58:27','YYYY-MM-DD HH24:MI:SS'),Name='Create Missing Customer Retention',PrintName='Create Missing Customer Retention' WHERE AD_Element_ID=575921 AND AD_Language='nl_NL'
;

-- 2019-01-09T17:58:27.609
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575921,'nl_NL') 
;

-- 2019-01-09T17:58:44.441
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:58:44','YYYY-MM-DD HH24:MI:SS'),Name='Update Customer Retention',PrintName='Update Customer Retention' WHERE AD_Element_ID=575920 AND AD_Language='en_US'
;

-- 2019-01-09T17:58:44.448
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575920,'en_US') 
;

-- 2019-01-09T17:58:47.953
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:58:47','YYYY-MM-DD HH24:MI:SS'),Name='Update Customer Retention',PrintName='Update Customer Retention' WHERE AD_Element_ID=575920 AND AD_Language='nl_NL'
;

-- 2019-01-09T17:58:47.961
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575920,'nl_NL') 
;

-- 2019-01-09T17:58:53.153
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-09 17:58:53','YYYY-MM-DD HH24:MI:SS'),Name='Update Customer Retention',PrintName='Update Customer Retention' WHERE AD_Element_ID=575920 AND AD_Language='de_CH'
;

-- 2019-01-09T17:58:53.156
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575920,'de_CH') 
;

