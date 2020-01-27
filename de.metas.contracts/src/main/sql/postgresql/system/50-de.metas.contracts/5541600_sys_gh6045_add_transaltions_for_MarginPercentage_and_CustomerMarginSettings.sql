-- 2020-01-15T11:51:24.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2020-01-15 13:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577418 AND AD_Language='de_CH'
;

-- 2020-01-15T11:51:24.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577418,'de_CH') 
;

-- 2020-01-15T11:51:37.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2020-01-15 13:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577418 AND AD_Language='de_DE'
;

-- 2020-01-15T11:51:37.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577418,'de_DE') 
;

-- 2020-01-15T11:51:37.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577418,'de_DE') 
;

-- 2020-01-15T11:51:37.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID=577418
;

-- 2020-01-15T11:51:37.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID=577418 AND IsCentrallyMaintained='Y'
;

-- 2020-01-15T11:51:37.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577418) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577418)
;

-- 2020-01-15T11:51:37.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Margeneinstellungen Kunde', Name='Margeneinstellungen Kunde' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577418)
;

-- 2020-01-15T11:51:37.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577418
;

-- 2020-01-15T11:51:37.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Margeneinstellungen Kunde', Description=NULL, Help=NULL WHERE AD_Element_ID = 577418
;

-- 2020-01-15T11:51:37.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Margeneinstellungen Kunde', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577418
;

-- 2020-01-15T11:51:44.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margeneinstellungen Kunde', PrintName='Margeneinstellungen Kunde',Updated=TO_TIMESTAMP('2020-01-15 13:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577418 AND AD_Language='nl_NL'
;

-- 2020-01-15T11:51:44.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577418,'nl_NL') 
;

-- 2020-01-15T11:52:04.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Customer Margin Settings', PrintName='Customer Margin Settings',Updated=TO_TIMESTAMP('2020-01-15 13:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577418 AND AD_Language='en_US'
;

-- 2020-01-15T11:52:04.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577418,'en_US') 
;

-- 2020-01-15T11:53:08.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Marge %', PrintName='Marge %',Updated=TO_TIMESTAMP('2020-01-15 13:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='de_CH'
;

-- 2020-01-15T11:53:08.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'de_CH') 
;

-- 2020-01-15T11:53:13.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Marge %', PrintName='Marge %',Updated=TO_TIMESTAMP('2020-01-15 13:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='de_DE'
;

-- 2020-01-15T11:53:13.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'de_DE') 
;

-- 2020-01-15T11:53:13.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577415,'de_DE') 
;

-- 2020-01-15T11:53:13.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Margin_Percent', Name='Marge %', Description=NULL, Help=NULL WHERE AD_Element_ID=577415
;

-- 2020-01-15T11:53:13.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Margin_Percent', Name='Marge %', Description=NULL, Help=NULL, AD_Element_ID=577415 WHERE UPPER(ColumnName)='MARGIN_PERCENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-15T11:53:13.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Margin_Percent', Name='Marge %', Description=NULL, Help=NULL WHERE AD_Element_ID=577415 AND IsCentrallyMaintained='Y'
;

-- 2020-01-15T11:53:13.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Marge %', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577415) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577415)
;

-- 2020-01-15T11:53:13.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Marge %', Name='Marge %' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577415)
;

-- 2020-01-15T11:53:13.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Marge %', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577415
;

-- 2020-01-15T11:53:13.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Marge %', Description=NULL, Help=NULL WHERE AD_Element_ID = 577415
;

-- 2020-01-15T11:53:13.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Marge %', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577415
;

-- 2020-01-15T11:53:22.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Margin %', PrintName='Margin %',Updated=TO_TIMESTAMP('2020-01-15 13:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='en_US'
;

-- 2020-01-15T11:53:22.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'en_US') 
;

-- 2020-01-15T11:53:27.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Marge %', PrintName='Marge %',Updated=TO_TIMESTAMP('2020-01-15 13:53:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577415 AND AD_Language='nl_NL'
;

-- 2020-01-15T11:53:27.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577415,'nl_NL') 
;

