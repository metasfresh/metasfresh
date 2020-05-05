-- 2019-07-17T11:55:24.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-17 13:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_CH'
;

-- 2019-07-17T11:55:24.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_CH') 
;

-- 2019-07-17T11:55:57.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Full URL address - e.g. https://metasfresh.com/', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-17 13:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_DE'
;

-- 2019-07-17T11:55:57.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_DE') 
;

-- 2019-07-17T11:55:57.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544091,'de_DE') 
;

-- 2019-07-17T11:55:57.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='URL3', Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='' WHERE AD_Element_ID=544091
;

-- 2019-07-17T11:55:57.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='', AD_Element_ID=544091 WHERE UPPER(ColumnName)='URL3' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-17T11:55:57.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='' WHERE AD_Element_ID=544091 AND IsCentrallyMaintained='Y'
;

-- 2019-07-17T11:55:57.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544091)
;

-- 2019-07-17T11:55:57.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='', CommitWarning = NULL WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:55:57.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL3', Description='Full URL address - e.g. https://metasfresh.com/', Help='' WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:55:57.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL3', Description = 'Full URL address - e.g. https://metasfresh.com/', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:56:14.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/',Updated=TO_TIMESTAMP('2019-07-17 13:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_DE'
;

-- 2019-07-17T11:56:14.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_DE') 
;

-- 2019-07-17T11:56:14.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544091,'de_DE') 
;

-- 2019-07-17T11:56:14.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='URL3', Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='' WHERE AD_Element_ID=544091
;

-- 2019-07-17T11:56:14.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='', AD_Element_ID=544091 WHERE UPPER(ColumnName)='URL3' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-17T11:56:14.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='' WHERE AD_Element_ID=544091 AND IsCentrallyMaintained='Y'
;

-- 2019-07-17T11:56:14.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544091)
;

-- 2019-07-17T11:56:14.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='', CommitWarning = NULL WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:56:14.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL3', Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/', Help='' WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:56:14.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL3', Description = 'Vollständige Web-Addresse, z.B. https://metasfresh.com/', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544091
;

-- 2019-07-17T11:56:21.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Vollständige Web-Addresse, z.B. https://metasfresh.com/',Updated=TO_TIMESTAMP('2019-07-17 13:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_CH'
;

-- 2019-07-17T11:56:21.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_CH') 
;
