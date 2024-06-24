-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T11:52:02.762Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit', PrintName='Toleranz Einheit',Updated=TO_TIMESTAMP('2022-11-29 13:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_CH'
;

-- 2022-11-29T11:52:02.765Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_CH') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T11:52:06.323Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit', PrintName='Toleranz Einheit',Updated=TO_TIMESTAMP('2022-11-29 13:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_DE'
;

-- 2022-11-29T11:52:06.325Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_DE') 
;

-- 2022-11-29T11:52:06.352Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581757,'de_DE') 
;

-- 2022-11-29T11:52:06.355Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID=581757
;

-- 2022-11-29T11:52:06.358Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL, AD_Element_ID=581757 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-29T11:52:06.360Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID=581757 AND IsCentrallyMaintained='Y'
;

-- 2022-11-29T11:52:06.363Z
UPDATE AD_Field SET Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581757) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581757)
;

-- 2022-11-29T11:52:06.383Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranz Einheit', Name='Toleranz Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581757)
;

-- 2022-11-29T11:52:06.386Z
UPDATE AD_Tab SET Name='Toleranz Einheit', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T11:52:06.389Z
UPDATE AD_WINDOW SET Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T11:52:06.392Z
UPDATE AD_Menu SET   Name = 'Toleranz Einheit', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581757
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T11:52:16.557Z
UPDATE AD_Element_Trl SET Name='Tolerance Unit', PrintName='Tolerance Unit',Updated=TO_TIMESTAMP('2022-11-29 13:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='en_US'
;

-- 2022-11-29T11:52:16.559Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'en_US') 
;

