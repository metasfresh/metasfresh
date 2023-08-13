
-- 2021-09-24T05:23:14.705Z
-- URL zum Konzept
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-09-24 07:23:14','YYYY-MM-DD HH24:MI:SS'),100,540005,'Y','RabbitMQ REST API','RabbitMQ',TO_TIMESTAMP('2021-09-24 07:23:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-24T05:24:27.127Z
-- URL zum Konzept
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2021-09-24 07:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540005
;


-- also update the url field description
------------

-- 2021-09-24T06:08:58.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-24 08:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='de_CH'
;

-- 2021-09-24T06:08:58.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'de_CH')
;

-- 2021-09-24T06:09:04.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-24 08:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='de_DE'
;

-- 2021-09-24T06:09:04.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'de_DE')
;

-- 2021-09-24T06:09:04.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579645,'de_DE')
;

-- 2021-09-24T06:09:04.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL WHERE AD_Element_ID=579645
;

-- 2021-09-24T06:09:04.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL, AD_Element_ID=579645 WHERE UPPER(ColumnName)='REMOTEURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-24T06:09:04.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL WHERE AD_Element_ID=579645 AND IsCentrallyMaintained='Y'
;

-- 2021-09-24T06:09:04.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579645) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579645)
;

-- 2021-09-24T06:09:04.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-24T06:09:04.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', Help=NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-24T06:09:04.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Remote-URL', Description = 'URL des RabbitMQ HTTP API Servers. Beispiel: "https://example.com/messages/publish"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-24T06:10:10.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL of the RabbitMQ HTTP API server. Example: "https://example.com/messages/publish"', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-24 08:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='en_US'
;

-- 2021-09-24T06:10:10.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'en_US')
;

