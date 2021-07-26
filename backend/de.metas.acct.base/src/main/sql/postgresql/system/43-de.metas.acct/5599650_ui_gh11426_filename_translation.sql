-- 2021-07-26T09:36:52.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='de_DE'
;

-- 2021-07-26T09:36:52.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'de_DE') 
;

-- 2021-07-26T09:36:52.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2295,'de_DE') 
;

-- 2021-07-26T09:36:52.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dateiname', Name='File Name' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2295)
;

-- 2021-07-26T09:36:57.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='nl_NL'
;

-- 2021-07-26T09:36:57.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'nl_NL') 
;

-- 2021-07-26T09:37:04.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='de_CH'
;

-- 2021-07-26T09:37:04.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'de_CH') 
;

-- 2021-07-26T11:32:52.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 14:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='de_DE'
;

-- 2021-07-26T11:32:52.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'de_DE') 
;

-- 2021-07-26T11:32:52.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2295,'de_DE') 
;

-- 2021-07-26T11:32:52.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FileName', Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)' WHERE AD_Element_ID=2295
;

-- 2021-07-26T11:32:52.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FileName', Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)', AD_Element_ID=2295 WHERE UPPER(ColumnName)='FILENAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-26T11:32:52.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FileName', Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)' WHERE AD_Element_ID=2295 AND IsCentrallyMaintained='Y'
;

-- 2021-07-26T11:32:52.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2295) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2295)
;

-- 2021-07-26T11:32:52.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dateiname', Name='Dateiname' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2295)
;

-- 2021-07-26T11:32:52.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)', CommitWarning = NULL WHERE AD_Element_ID = 2295
;

-- 2021-07-26T11:32:52.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Dateiname', Description='Name of the local file or URL', Help='Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)' WHERE AD_Element_ID = 2295
;

-- 2021-07-26T11:32:52.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Dateiname', Description = 'Name of the local file or URL', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2295
;
 