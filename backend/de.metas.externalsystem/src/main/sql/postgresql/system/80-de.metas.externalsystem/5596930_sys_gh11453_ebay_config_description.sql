-- 2021-07-06T12:37:59.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client ID',Updated=TO_TIMESTAMP('2021-07-06 15:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='de_CH'
;

-- 2021-07-06T12:37:59.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'de_CH') 
;

-- 2021-07-06T12:38:07.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client ID',Updated=TO_TIMESTAMP('2021-07-06 15:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='de_DE'
;

-- 2021-07-06T12:38:07.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'de_DE') 
;

-- 2021-07-06T12:38:07.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579445,'de_DE') 
;

-- 2021-07-06T12:38:07.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AppId', Name='App ID', Description='Client ID', Help=NULL WHERE AD_Element_ID=579445
;

-- 2021-07-06T12:38:07.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AppId', Name='App ID', Description='Client ID', Help=NULL, AD_Element_ID=579445 WHERE UPPER(ColumnName)='APPID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T12:38:07.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AppId', Name='App ID', Description='Client ID', Help=NULL WHERE AD_Element_ID=579445 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T12:38:07.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='App ID', Description='Client ID', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579445) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579445)
;

-- 2021-07-06T12:38:07.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='App ID', Description='Client ID', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-06T12:38:07.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='App ID', Description='Client ID', Help=NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-06T12:38:07.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'App ID', Description = 'Client ID', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579445
;

-- 2021-07-06T12:38:10.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client ID',Updated=TO_TIMESTAMP('2021-07-06 15:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='en_US'
;

-- 2021-07-06T12:38:10.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'en_US') 
;

-- 2021-07-06T12:38:12.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client ID',Updated=TO_TIMESTAMP('2021-07-06 15:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579445 AND AD_Language='nl_NL'
;

-- 2021-07-06T12:38:12.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579445,'nl_NL') 
;

-- 2021-07-06T12:39:13.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client Secret',Updated=TO_TIMESTAMP('2021-07-06 15:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579446 AND AD_Language='de_CH'
;

-- 2021-07-06T12:39:13.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579446,'de_CH') 
;

-- 2021-07-06T12:39:16.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client Secret',Updated=TO_TIMESTAMP('2021-07-06 15:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579446 AND AD_Language='de_DE'
;

-- 2021-07-06T12:39:16.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579446,'de_DE') 
;

-- 2021-07-06T12:39:16.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579446,'de_DE') 
;

-- 2021-07-06T12:39:16.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CertId', Name='Cert ID', Description='Client Secret', Help='' WHERE AD_Element_ID=579446
;

-- 2021-07-06T12:39:16.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CertId', Name='Cert ID', Description='Client Secret', Help='', AD_Element_ID=579446 WHERE UPPER(ColumnName)='CERTID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T12:39:16.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CertId', Name='Cert ID', Description='Client Secret', Help='' WHERE AD_Element_ID=579446 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T12:39:16.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Cert ID', Description='Client Secret', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579446) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579446)
;

-- 2021-07-06T12:39:16.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Cert ID', Description='Client Secret', Help='', CommitWarning = NULL WHERE AD_Element_ID = 579446
;

-- 2021-07-06T12:39:16.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Cert ID', Description='Client Secret', Help='' WHERE AD_Element_ID = 579446
;

-- 2021-07-06T12:39:16.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Cert ID', Description = 'Client Secret', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579446
;

-- 2021-07-06T12:39:18.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client Secret',Updated=TO_TIMESTAMP('2021-07-06 15:39:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579446 AND AD_Language='en_US'
;

-- 2021-07-06T12:39:18.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579446,'en_US') 
;

-- 2021-07-06T12:39:20.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Client Secret',Updated=TO_TIMESTAMP('2021-07-06 15:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579446 AND AD_Language='nl_NL'
;

-- 2021-07-06T12:39:20.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579446,'nl_NL') 
;

