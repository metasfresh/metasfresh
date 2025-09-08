-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:33:38.513Z
UPDATE AD_Element_Trl SET Name='Tolerance Value Type2',Updated=TO_TIMESTAMP('2022-11-29 14:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='en_US'
;

-- 2022-11-29T12:33:38.518Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'en_US') 
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:33:40.728Z
UPDATE AD_Element_Trl SET Name='Tolerance Value Type',Updated=TO_TIMESTAMP('2022-11-29 14:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='en_US'
;

-- 2022-11-29T12:33:40.731Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'en_US') 
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:33:43.278Z
UPDATE AD_Element_Trl SET Name='Toleranzwert Art2',Updated=TO_TIMESTAMP('2022-11-29 14:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_DE'
;

-- 2022-11-29T12:33:43.281Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_DE') 
;

-- 2022-11-29T12:33:43.294Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581752,'de_DE') 
;

-- 2022-11-29T12:33:43.297Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art2', Description=NULL, Help=NULL WHERE AD_Element_ID=581752
;

-- 2022-11-29T12:33:43.300Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art2', Description=NULL, Help=NULL, AD_Element_ID=581752 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_VALUETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-29T12:33:43.302Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art2', Description=NULL, Help=NULL WHERE AD_Element_ID=581752 AND IsCentrallyMaintained='Y'
;

-- 2022-11-29T12:33:43.305Z
UPDATE AD_Field SET Name='Toleranzwert Art2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581752) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581752)
;

-- 2022-11-29T12:33:43.325Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranzwert Art', Name='Toleranzwert Art2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581752)
;

-- 2022-11-29T12:33:43.329Z
UPDATE AD_Tab SET Name='Toleranzwert Art2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581752
;

-- 2022-11-29T12:33:43.333Z
UPDATE AD_WINDOW SET Name='Toleranzwert Art2', Description=NULL, Help=NULL WHERE AD_Element_ID = 581752
;

-- 2022-11-29T12:33:43.336Z
UPDATE AD_Menu SET   Name = 'Toleranzwert Art2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581752
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:33:48.822Z
UPDATE AD_Element_Trl SET Name='Toleranzwert Art',Updated=TO_TIMESTAMP('2022-11-29 14:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_DE'
;

-- 2022-11-29T12:33:48.826Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_DE') 
;

-- 2022-11-29T12:33:48.839Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581752,'de_DE') 
;

-- 2022-11-29T12:33:48.842Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art', Description=NULL, Help=NULL WHERE AD_Element_ID=581752
;

-- 2022-11-29T12:33:48.937Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art', Description=NULL, Help=NULL, AD_Element_ID=581752 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_VALUETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-29T12:33:48.941Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_ValueType', Name='Toleranzwert Art', Description=NULL, Help=NULL WHERE AD_Element_ID=581752 AND IsCentrallyMaintained='Y'
;

-- 2022-11-29T12:33:48.944Z
UPDATE AD_Field SET Name='Toleranzwert Art', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581752) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581752)
;

-- 2022-11-29T12:33:48.964Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranzwert Art', Name='Toleranzwert Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581752)
;

-- 2022-11-29T12:33:48.967Z
UPDATE AD_Tab SET Name='Toleranzwert Art', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581752
;

-- 2022-11-29T12:33:48.970Z
UPDATE AD_WINDOW SET Name='Toleranzwert Art', Description=NULL, Help=NULL WHERE AD_Element_ID = 581752
;

-- 2022-11-29T12:33:48.973Z
UPDATE AD_Menu SET   Name = 'Toleranzwert Art', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581752
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:34:32.539Z
UPDATE AD_Element_Trl SET PrintName='Toleranzwert Art2',Updated=TO_TIMESTAMP('2022-11-29 14:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_DE'
;

-- 2022-11-29T12:34:32.543Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_DE') 
;

-- 2022-11-29T12:34:32.560Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581752,'de_DE') 
;

-- 2022-11-29T12:34:32.562Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranzwert Art2', Name='Toleranzwert Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581752)
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:34:34.328Z
UPDATE AD_Element_Trl SET PrintName='Toleranzwert Art',Updated=TO_TIMESTAMP('2022-11-29 14:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_DE'
;

-- 2022-11-29T12:34:34.330Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_DE') 
;

-- 2022-11-29T12:34:34.342Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581752,'de_DE') 
;

-- 2022-11-29T12:34:34.346Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranzwert Art', Name='Toleranzwert Art' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581752)
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:34:42.110Z
UPDATE AD_Element_Trl SET Name='Toleranzwert Art2', PrintName='Toleranzwert Art2',Updated=TO_TIMESTAMP('2022-11-29 14:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_CH'
;

-- 2022-11-29T12:34:42.113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_CH') 
;

-- Element: IssuingTolerance_ValueType
-- 2022-11-29T12:34:44.799Z
UPDATE AD_Element_Trl SET Name='Toleranzwert Art', PrintName='Toleranzwert Art',Updated=TO_TIMESTAMP('2022-11-29 14:34:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581752 AND AD_Language='de_CH'
;

-- 2022-11-29T12:34:44.802Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581752,'de_CH') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:34:58.928Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit2', PrintName='Toleranz Einheit2',Updated=TO_TIMESTAMP('2022-11-29 14:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_CH'
;

-- 2022-11-29T12:34:58.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_CH') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:52:50.185Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit', PrintName='Toleranz Einheit',Updated=TO_TIMESTAMP('2022-11-29 14:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_CH'
;

-- 2022-11-29T12:52:50.187Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_CH') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:53:03.990Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit2', PrintName='Toleranz Einheit2',Updated=TO_TIMESTAMP('2022-11-29 14:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_DE'
;

-- 2022-11-29T12:53:03.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_DE') 
;

-- 2022-11-29T12:53:04.001Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581757,'de_DE') 
;

-- 2022-11-29T12:53:04.003Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit2', Description=NULL, Help=NULL WHERE AD_Element_ID=581757
;

-- 2022-11-29T12:53:04.005Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit2', Description=NULL, Help=NULL, AD_Element_ID=581757 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-29T12:53:04.006Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit2', Description=NULL, Help=NULL WHERE AD_Element_ID=581757 AND IsCentrallyMaintained='Y'
;

-- 2022-11-29T12:53:04.007Z
UPDATE AD_Field SET Name='Toleranz Einheit2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581757) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581757)
;

-- 2022-11-29T12:53:04.018Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranz Einheit2', Name='Toleranz Einheit2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581757)
;

-- 2022-11-29T12:53:04.019Z
UPDATE AD_Tab SET Name='Toleranz Einheit2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T12:53:04.021Z
UPDATE AD_WINDOW SET Name='Toleranz Einheit2', Description=NULL, Help=NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T12:53:04.022Z
UPDATE AD_Menu SET   Name = 'Toleranz Einheit2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581757
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:53:08.194Z
UPDATE AD_Element_Trl SET Name='Toleranz Einheit', PrintName='Toleranz Einheit',Updated=TO_TIMESTAMP('2022-11-29 14:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='de_DE'
;

-- 2022-11-29T12:53:08.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'de_DE') 
;

-- 2022-11-29T12:53:08.201Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581757,'de_DE') 
;

-- 2022-11-29T12:53:08.202Z
UPDATE AD_Column SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID=581757
;

-- 2022-11-29T12:53:08.203Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL, AD_Element_ID=581757 WHERE UPPER(ColumnName)='ISSUINGTOLERANCE_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-29T12:53:08.204Z
UPDATE AD_Process_Para SET ColumnName='IssuingTolerance_UOM_ID', Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID=581757 AND IsCentrallyMaintained='Y'
;

-- 2022-11-29T12:53:08.205Z
UPDATE AD_Field SET Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581757) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581757)
;

-- 2022-11-29T12:53:08.214Z
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranz Einheit', Name='Toleranz Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581757)
;

-- 2022-11-29T12:53:08.216Z
UPDATE AD_Tab SET Name='Toleranz Einheit', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T12:53:08.217Z
UPDATE AD_WINDOW SET Name='Toleranz Einheit', Description=NULL, Help=NULL WHERE AD_Element_ID = 581757
;

-- 2022-11-29T12:53:08.218Z
UPDATE AD_Menu SET   Name = 'Toleranz Einheit', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581757
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:53:13.895Z
UPDATE AD_Element_Trl SET Name='Tolerance Unit2', PrintName='Tolerance Unit2',Updated=TO_TIMESTAMP('2022-11-29 14:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='en_US'
;

-- 2022-11-29T12:53:13.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'en_US') 
;

-- Element: IssuingTolerance_UOM_ID
-- 2022-11-29T12:53:16.634Z
UPDATE AD_Element_Trl SET Name='Tolerance Unit', PrintName='Tolerance Unit',Updated=TO_TIMESTAMP('2022-11-29 14:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581757 AND AD_Language='en_US'
;

-- 2022-11-29T12:53:16.635Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581757,'en_US') 
;

