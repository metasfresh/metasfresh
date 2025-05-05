-- 2022-10-06T18:30:21.098Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Detail data', PrintName='Detail data',Updated=TO_TIMESTAMP('2022-10-06 21:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573726 AND AD_Language='en_US'
;

-- 2022-10-06T18:30:21.176Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573726,'en_US') 
;

-- 2022-10-06T18:30:21.260Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(573726,'en_US') 
;

-- 2022-10-06T18:30:21.291Z
UPDATE AD_Column SET ColumnName=NULL, Name='Detail data', Description=NULL, Help=NULL WHERE AD_Element_ID=573726
;

-- 2022-10-06T18:30:21.321Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Detail data', Description=NULL, Help=NULL WHERE AD_Element_ID=573726 AND IsCentrallyMaintained='Y'
;

-- 2022-10-06T18:30:21.351Z
UPDATE AD_Field SET Name='Detail data', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573726) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573726)
;

-- 2022-10-06T18:30:21.395Z
UPDATE AD_PrintFormatItem pi SET PrintName='Detail data', Name='Detail data' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=573726)
;

-- 2022-10-06T18:30:21.426Z
UPDATE AD_Tab SET Name='Detail data', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573726
;

-- 2022-10-06T18:30:21.458Z
UPDATE AD_WINDOW SET Name='Detail data', Description=NULL, Help=NULL WHERE AD_Element_ID = 573726
;

-- 2022-10-06T18:30:21.488Z
UPDATE AD_Menu SET   Name = 'Detail data', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573726
;
