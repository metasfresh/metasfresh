-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:19:28.849Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='mobile UI Nutzerprofil - Produktion', PrintName='mobile UI Nutzerprofil - Produktion',Updated=TO_TIMESTAMP('2022-11-23 16:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='de_CH'
;

-- 2022-11-23T14:19:28.862Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'de_CH') 
;

-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:19:34.215Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='mobile UI Nutzerprofil - Produktion', PrintName='mobile UI Nutzerprofil - Produktion',Updated=TO_TIMESTAMP('2022-11-23 16:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='de_DE'
;

-- 2022-11-23T14:19:34.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'de_DE') 
;

-- 2022-11-23T14:19:34.224Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581713,'de_DE') 
;

-- 2022-11-23T14:19:34.237Z
UPDATE AD_Column SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581713
;

-- 2022-11-23T14:19:34.239Z
UPDATE AD_Process_Para SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL, AD_Element_ID=581713 WHERE UPPER(ColumnName)='MOBILEUI_USERPROFILE_MFG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-23T14:19:34.241Z
UPDATE AD_Process_Para SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581713 AND IsCentrallyMaintained='Y'
;

-- 2022-11-23T14:19:34.242Z
UPDATE AD_Field SET Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581713) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581713)
;

-- 2022-11-23T14:19:34.257Z
UPDATE AD_PrintFormatItem pi SET PrintName='mobile UI Nutzerprofil - Produktion', Name='mobile UI Nutzerprofil - Produktion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581713)
;

-- 2022-11-23T14:19:34.259Z
UPDATE AD_Tab SET Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581713
;

-- 2022-11-23T14:19:34.261Z
UPDATE AD_WINDOW SET Name='mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID = 581713
;

-- 2022-11-23T14:19:34.262Z
UPDATE AD_Menu SET   Name = 'mobile UI Nutzerprofil - Produktion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581713
;

-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:25:49.418Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-23 16:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='en_US'
;

-- 2022-11-23T14:25:49.419Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'en_US') 
;

-- Element: IsScanResourceRequired
-- 2022-11-23T14:26:42.562Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Scan der Ressource erforderlich', PrintName='Scan der Ressource erforderlich',Updated=TO_TIMESTAMP('2022-11-23 16:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581714 AND AD_Language='de_CH'
;

-- 2022-11-23T14:26:42.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581714,'de_CH') 
;

-- Element: IsScanResourceRequired
-- 2022-11-23T14:26:46.880Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Scan der Ressource erforderlich', PrintName='Scan der Ressource erforderlich',Updated=TO_TIMESTAMP('2022-11-23 16:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581714 AND AD_Language='de_DE'
;

-- 2022-11-23T14:26:46.881Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581714,'de_DE') 
;

-- 2022-11-23T14:26:46.888Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581714,'de_DE') 
;

-- 2022-11-23T14:26:46.889Z
UPDATE AD_Column SET ColumnName='IsScanResourceRequired', Name='Scan der Ressource erforderlich', Description='', Help=NULL WHERE AD_Element_ID=581714
;

-- 2022-11-23T14:26:46.890Z
UPDATE AD_Process_Para SET ColumnName='IsScanResourceRequired', Name='Scan der Ressource erforderlich', Description='', Help=NULL, AD_Element_ID=581714 WHERE UPPER(ColumnName)='ISSCANRESOURCEREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-23T14:26:46.891Z
UPDATE AD_Process_Para SET ColumnName='IsScanResourceRequired', Name='Scan der Ressource erforderlich', Description='', Help=NULL WHERE AD_Element_ID=581714 AND IsCentrallyMaintained='Y'
;

-- 2022-11-23T14:26:46.892Z
UPDATE AD_Field SET Name='Scan der Ressource erforderlich', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581714) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581714)
;

-- 2022-11-23T14:26:46.903Z
UPDATE AD_PrintFormatItem pi SET PrintName='Scan der Ressource erforderlich', Name='Scan der Ressource erforderlich' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581714)
;

-- 2022-11-23T14:26:46.904Z
UPDATE AD_Tab SET Name='Scan der Ressource erforderlich', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581714
;

-- 2022-11-23T14:26:46.905Z
UPDATE AD_WINDOW SET Name='Scan der Ressource erforderlich', Description='', Help=NULL WHERE AD_Element_ID = 581714
;

-- 2022-11-23T14:26:46.906Z
UPDATE AD_Menu SET   Name = 'Scan der Ressource erforderlich', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581714
;

-- Element: IsScanResourceRequired
-- 2022-11-23T14:26:49.006Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-23 16:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581714 AND AD_Language='en_US'
;

-- 2022-11-23T14:26:49.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581714,'en_US') 
;

