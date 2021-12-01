-- 2021-12-01T14:25:06.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,DisableCommand,EnableCommand,ExternalSystem_Service_ID,ExternalSystemValue,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-01 16:25:06','YYYY-MM-DD HH24:MI:SS'),100,'/grs','disableRestAPI','enableRestAPI',540000,'defaultRestAPIGRS','Y','REST API','GRS',TO_TIMESTAMP('2021-12-01 16:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-01T14:26:25.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,DisableCommand,EnableCommand,ExternalSystem_Service_ID,ExternalSystemValue,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-01 16:26:25','YYYY-MM-DD HH24:MI:SS'),100,'/woocommerce','disableRestAPI','enableRestAPI',540001,'defaultRestAPIWOO','Y','REST API','WOO',TO_TIMESTAMP('2021-12-01 16:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-01T14:28:18.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-12-01 16:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544882
;

-- 2021-12-01T14:30:03.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service Instance', PrintName='External System Service Instance',Updated=TO_TIMESTAMP('2021-12-01 16:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_CH'
;

-- 2021-12-01T14:30:03.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_CH')
;

-- 2021-12-01T14:30:05.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service Instance', PrintName='External System Service Instance',Updated=TO_TIMESTAMP('2021-12-01 16:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_DE'
;

-- 2021-12-01T14:30:05.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_DE')
;

-- 2021-12-01T14:30:05.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580257,'de_DE')
;

-- 2021-12-01T14:30:05.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='External System Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580257
;

-- 2021-12-01T14:30:05.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='External System Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580257 AND IsCentrallyMaintained='Y'
;

-- 2021-12-01T14:30:05.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External System Service Instance', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580257) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580257)
;

-- 2021-12-01T14:30:05.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External System Service Instance', Name='External System Service Instance' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580257)
;

-- 2021-12-01T14:30:05.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External System Service Instance', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580257
;

-- 2021-12-01T14:30:05.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External System Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID = 580257
;

-- 2021-12-01T14:30:05.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External System Service Instance', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580257
;

-- 2021-12-01T14:30:08.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service Instance', PrintName='External System Service Instance',Updated=TO_TIMESTAMP('2021-12-01 16:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='en_US'
;

-- 2021-12-01T14:30:08.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'en_US')
;

-- 2021-12-01T14:30:11.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service Instance', PrintName='External System Service Instance',Updated=TO_TIMESTAMP('2021-12-01 16:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='nl_NL'
;

-- 2021-12-01T14:30:11.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'nl_NL')
;

