-- 2020-02-13T21:59:22.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y',Updated=TO_TIMESTAMP('2020-02-13 22:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570024
;

-- 2020-02-13T22:00:06.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Provisionsauslöser Zeitpunkt', PrintName='Provisionsauslöser Zeitpunkt',Updated=TO_TIMESTAMP('2020-02-13 23:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577089 AND AD_Language='de_CH'
;

-- 2020-02-13T22:00:06.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577089,'de_CH') 
;

-- 2020-02-13T22:00:12.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Provisionsauslöser Zeitpunkt', PrintName='Provisionsauslöser Zeitpunkt',Updated=TO_TIMESTAMP('2020-02-13 23:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577089 AND AD_Language='de_DE'
;

-- 2020-02-13T22:00:12.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577089,'de_DE') 
;

-- 2020-02-13T22:00:12.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577089,'de_DE') 
;

-- 2020-02-13T22:00:12.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MostRecentTriggerTimestamp', Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577089
;

-- 2020-02-13T22:00:12.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MostRecentTriggerTimestamp', Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL, AD_Element_ID=577089 WHERE UPPER(ColumnName)='MOSTRECENTTRIGGERTIMESTAMP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-13T22:00:12.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MostRecentTriggerTimestamp', Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577089 AND IsCentrallyMaintained='Y'
;

-- 2020-02-13T22:00:12.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577089) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577089)
;

-- 2020-02-13T22:00:12.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Provisionsauslöser Zeitpunkt', Name='Provisionsauslöser Zeitpunkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577089)
;

-- 2020-02-13T22:00:12.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577089
;

-- 2020-02-13T22:00:12.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provisionsauslöser Zeitpunkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577089
;

-- 2020-02-13T22:00:12.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provisionsauslöser Zeitpunkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577089
;

-- 2020-02-13T22:00:27.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission trigger time', PrintName='Commission trigger time',Updated=TO_TIMESTAMP('2020-02-13 23:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577089 AND AD_Language='en_US'
;

-- 2020-02-13T22:00:27.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577089,'en_US') 
;

