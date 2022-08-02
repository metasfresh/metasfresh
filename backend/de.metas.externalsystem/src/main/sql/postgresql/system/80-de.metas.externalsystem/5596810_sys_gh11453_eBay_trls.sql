-- 2021-07-06T08:20:10.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Tab_ID=544131
;

-- 2021-07-06T08:20:13.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Tab_ID=544131
;

-- 2021-07-06T08:20:15.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Tab_ID=544131
;

-- 2021-07-06T08:20:17.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Tab_ID=544131
;

-- 2021-07-06T08:20:39.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='eBay', PrintName='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444
;

-- 2021-07-06T08:20:39.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444
;

-- 2021-07-06T08:20:39.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL, AD_Element_ID=579444 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_EBAY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T08:20:39.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T08:20:39.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eBay', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579444) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579444)
;

-- 2021-07-06T08:20:39.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='eBay', Name='eBay' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579444)
;

-- 2021-07-06T08:20:39.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='eBay', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:39.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:39.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'eBay', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:48.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='eBay', PrintName='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='de_CH'
;

-- 2021-07-06T08:20:48.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'de_CH') 
;

-- 2021-07-06T08:20:51.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='eBay', PrintName='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='de_DE'
;

-- 2021-07-06T08:20:51.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'de_DE') 
;

-- 2021-07-06T08:20:51.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579444,'de_DE') 
;

-- 2021-07-06T08:20:51.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444
;

-- 2021-07-06T08:20:51.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL, AD_Element_ID=579444 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_EBAY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T08:20:51.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_Ebay_ID', Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID=579444 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T08:20:51.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eBay', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579444) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579444)
;

-- 2021-07-06T08:20:51.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='eBay', Name='eBay' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579444)
;

-- 2021-07-06T08:20:51.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='eBay', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:51.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='eBay', Description=NULL, Help=NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:51.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'eBay', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579444
;

-- 2021-07-06T08:20:54.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='eBay', PrintName='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='en_US'
;

-- 2021-07-06T08:20:54.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'en_US') 
;

-- 2021-07-06T08:20:57.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='eBay', PrintName='eBay',Updated=TO_TIMESTAMP('2021-07-06 11:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579444 AND AD_Language='nl_NL'
;

-- 2021-07-06T08:20:57.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579444,'nl_NL') 
;

-- 2021-07-06T08:24:12.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='eBay Aufrufen',Updated=TO_TIMESTAMP('2021-07-06 11:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584853
;

-- 2021-07-06T08:24:22.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='eBay Aufrufen',Updated=TO_TIMESTAMP('2021-07-06 11:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584853
;

-- 2021-07-06T08:24:25.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='eBay Aufrufen',Updated=TO_TIMESTAMP('2021-07-06 11:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584853
;

-- 2021-07-06T08:24:29.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='eBay Aufrufen',Updated=TO_TIMESTAMP('2021-07-06 11:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584853
;

-- 2021-07-06T08:24:37.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Invoke eBay',Updated=TO_TIMESTAMP('2021-07-06 11:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584853
;

-- 2021-07-06T09:34:35.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542709
;

-- 2021-07-06T09:34:55.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542709
;

-- 2021-07-06T09:34:57.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542709
;

-- 2021-07-06T09:35:00.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542709
;

-- 2021-07-06T09:35:02.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542709
;

-- 2021-07-06T09:36:14.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542708
;

-- 2021-07-06T09:36:22.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:36:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542708
;

-- 2021-07-06T09:36:25.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542708
;

-- 2021-07-06T09:36:28.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542708
;

-- 2021-07-06T09:36:31.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='eBay',Updated=TO_TIMESTAMP('2021-07-06 12:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542708
;

