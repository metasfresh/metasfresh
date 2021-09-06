-- 2021-06-14T10:55:57.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Alberta', PrintName='External System Alberta',Updated=TO_TIMESTAMP('2021-06-14 13:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578730 AND AD_Language='de_CH'
;

-- 2021-06-14T10:55:57.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578730,'de_CH') 
;

-- 2021-06-14T10:56:13.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Alberta', PrintName='External System Alberta',Updated=TO_TIMESTAMP('2021-06-14 13:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578730 AND AD_Language='de_DE'
;

-- 2021-06-14T10:56:13.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578730,'de_DE') 
;

-- 2021-06-14T10:56:13.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578730,'de_DE') 
;

-- 2021-06-14T10:56:13.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_Alberta_ID', Name='External System Alberta', Description=NULL, Help=NULL WHERE AD_Element_ID=578730
;

-- 2021-06-14T10:56:13.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Alberta_ID', Name='External System Alberta', Description=NULL, Help=NULL, AD_Element_ID=578730 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_ALBERTA_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-14T10:56:13.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Alberta_ID', Name='External System Alberta', Description=NULL, Help=NULL WHERE AD_Element_ID=578730 AND IsCentrallyMaintained='Y'
;

-- 2021-06-14T10:56:13.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External System Alberta', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578730) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578730)
;

-- 2021-06-14T10:56:14.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External System Alberta', Name='External System Alberta' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578730)
;

-- 2021-06-14T10:56:14.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External System Alberta', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578730
;

-- 2021-06-14T10:56:14.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External System Alberta', Description=NULL, Help=NULL WHERE AD_Element_ID = 578730
;

-- 2021-06-14T10:56:14.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External System Alberta', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578730
;

-- 2021-06-14T10:56:17.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Alberta', PrintName='External System Alberta',Updated=TO_TIMESTAMP('2021-06-14 13:56:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578730 AND AD_Language='en_US'
;

-- 2021-06-14T10:56:17.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578730,'en_US') 
;

-- 2021-06-14T10:56:21.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Alberta', PrintName='External System Alberta',Updated=TO_TIMESTAMP('2021-06-14 13:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578730 AND AD_Language='nl_NL'
;

-- 2021-06-14T10:56:21.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578730,'nl_NL') 
;

