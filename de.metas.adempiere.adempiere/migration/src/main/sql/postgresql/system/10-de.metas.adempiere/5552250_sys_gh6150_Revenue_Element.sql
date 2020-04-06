-- 2020-02-14T15:27:45.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Revenue',Updated=TO_TIMESTAMP('2020-02-14 17:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528
;

-- 2020-02-14T15:27:45.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Revenue', Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577528
;

-- 2020-02-14T15:27:45.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Revenue', Name='Verkauf (MW)', Description=NULL, Help=NULL, AD_Element_ID=577528 WHERE UPPER(ColumnName)='REVENUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-14T15:27:45.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Revenue', Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577528 AND IsCentrallyMaintained='Y'
;

-- 2020-02-14T15:28:05.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verkauf', PrintName='Verkauf',Updated=TO_TIMESTAMP('2020-02-14 17:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='de_CH'
;

-- 2020-02-14T15:28:05.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'de_CH') 
;

-- 2020-02-14T15:28:14.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verkauf', PrintName='Verkauf',Updated=TO_TIMESTAMP('2020-02-14 17:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='de_DE'
;

-- 2020-02-14T15:28:14.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'de_DE') 
;

-- 2020-02-14T15:28:14.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577528,'de_DE') 
;

-- 2020-02-14T15:28:14.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Revenue', Name='Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=577528
;

-- 2020-02-14T15:28:14.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Revenue', Name='Verkauf', Description=NULL, Help=NULL, AD_Element_ID=577528 WHERE UPPER(ColumnName)='REVENUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-14T15:28:14.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Revenue', Name='Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=577528 AND IsCentrallyMaintained='Y'
;

-- 2020-02-14T15:28:14.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577528) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577528)
;

-- 2020-02-14T15:28:14.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verkauf', Name='Verkauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577528)
;

-- 2020-02-14T15:28:14.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verkauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-14T15:28:14.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-14T15:28:14.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verkauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-14T15:28:21.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Revenue', PrintName='Revenue',Updated=TO_TIMESTAMP('2020-02-14 17:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='en_US'
;

-- 2020-02-14T15:28:21.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'en_US') 
;

-- 2020-02-14T15:28:26.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Revenue', PrintName='Revenue',Updated=TO_TIMESTAMP('2020-02-14 17:28:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='nl_NL'
;

-- 2020-02-14T15:28:26.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'nl_NL') 
;

