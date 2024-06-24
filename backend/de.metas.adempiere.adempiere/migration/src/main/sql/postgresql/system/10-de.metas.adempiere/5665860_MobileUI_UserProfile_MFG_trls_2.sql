-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:49:25.892Z
UPDATE AD_Element_Trl SET Name='Mobile UI User Profile - Manufacturing', PrintName='Mobile UI User Profile - Manufacturing',Updated=TO_TIMESTAMP('2022-11-23 16:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='en_US'
;

-- 2022-11-23T14:49:25.897Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'en_US') 
;

-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:49:41.210Z
UPDATE AD_Element_Trl SET Name='Mobile UI Nutzerprofil - Produktion', PrintName='Mobile UI Nutzerprofil - Produktion',Updated=TO_TIMESTAMP('2022-11-23 16:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='de_CH'
;

-- 2022-11-23T14:49:41.211Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'de_CH') 
;

-- Element: MobileUI_UserProfile_MFG_ID
-- 2022-11-23T14:49:44.266Z
UPDATE AD_Element_Trl SET Name='Mobile UI Nutzerprofil - Produktion', PrintName='Mobile UI Nutzerprofil - Produktion',Updated=TO_TIMESTAMP('2022-11-23 16:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581713 AND AD_Language='de_DE'
;

-- 2022-11-23T14:49:44.267Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581713,'de_DE') 
;

-- 2022-11-23T14:49:44.277Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581713,'de_DE') 
;

-- 2022-11-23T14:49:44.278Z
UPDATE AD_Column SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581713
;

-- 2022-11-23T14:49:44.280Z
UPDATE AD_Process_Para SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL, AD_Element_ID=581713 WHERE UPPER(ColumnName)='MOBILEUI_USERPROFILE_MFG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-23T14:49:44.282Z
UPDATE AD_Process_Para SET ColumnName='MobileUI_UserProfile_MFG_ID', Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581713 AND IsCentrallyMaintained='Y'
;

-- 2022-11-23T14:49:44.283Z
UPDATE AD_Field SET Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581713) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581713)
;

-- 2022-11-23T14:49:44.296Z
UPDATE AD_PrintFormatItem pi SET PrintName='Mobile UI Nutzerprofil - Produktion', Name='Mobile UI Nutzerprofil - Produktion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581713)
;

-- 2022-11-23T14:49:44.298Z
UPDATE AD_Tab SET Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581713
;

-- 2022-11-23T14:49:44.299Z
UPDATE AD_WINDOW SET Name='Mobile UI Nutzerprofil - Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID = 581713
;

-- 2022-11-23T14:49:44.300Z
UPDATE AD_Menu SET   Name = 'Mobile UI Nutzerprofil - Produktion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581713
;

