-- 2021-12-21T13:36:06.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwingen', PrintName='Asynchrone Verarbeitung erzwingen',Updated=TO_TIMESTAMP('2021-12-21 15:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_CH'
;

-- 2021-12-21T13:36:06.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_CH') 
;

-- 2021-12-21T13:36:09.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwingen', PrintName='Asynchrone Verarbeitung erzwingen',Updated=TO_TIMESTAMP('2021-12-21 15:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_DE'
;

-- 2021-12-21T13:36:09.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_DE') 
;

-- 2021-12-21T13:36:09.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580404,'de_DE') 
;

-- 2021-12-21T13:36:09.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=580404
;

-- 2021-12-21T13:36:09.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL, AD_Element_ID=580404 WHERE UPPER(ColumnName)='ISFORCEPROCESSEDASYNC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T13:36:09.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=580404 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T13:36:09.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580404)
;

-- 2021-12-21T13:36:09.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Asynchrone Verarbeitung erzwingen', Name='Asynchrone Verarbeitung erzwingen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580404)
;

-- 2021-12-21T13:36:09.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:36:09.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Verarbeitung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:36:09.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Verarbeitung erzwingen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:36:15.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Verarbeitung erzwingen', PrintName='Asynchrone Verarbeitung erzwingen',Updated=TO_TIMESTAMP('2021-12-21 15:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='nl_NL'
;

-- 2021-12-21T13:36:15.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'nl_NL') 
;

-- 2021-12-21T13:42:27.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If "true", the HTTP call will be processed in an async manner and the response will consist only in a "requestId".',Updated=TO_TIMESTAMP('2021-12-21 15:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='en_US'
;

-- 2021-12-21T13:42:27.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'en_US') 
;

-- 2021-12-21T13:43:24.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 15:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='nl_NL'
;

-- 2021-12-21T13:43:24.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'nl_NL') 
;

-- 2021-12-21T13:43:27.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 15:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_DE'
;

-- 2021-12-21T13:43:27.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_DE') 
;

-- 2021-12-21T13:43:27.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580404,'de_DE') 
;

-- 2021-12-21T13:43:27.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID=580404
;

-- 2021-12-21T13:43:27.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL, AD_Element_ID=580404 WHERE UPPER(ColumnName)='ISFORCEPROCESSEDASYNC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T13:43:27.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID=580404 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T13:43:27.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580404)
;

-- 2021-12-21T13:43:27.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:43:27.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:43:27.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Verarbeitung erzwingen', Description = 'Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T13:43:30.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn "true", wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 15:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_CH'
;

-- 2021-12-21T13:43:30.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_CH') 
;

-- 2021-12-21T21:00:40.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, the HTTP call will be processed asynchronously and the response will consist only of a "requestId".',Updated=TO_TIMESTAMP('2021-12-21 23:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='en_US'
;

-- 2021-12-21T21:00:40.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'en_US')
;

-- 2021-12-21T21:01:10.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 23:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='nl_NL'
;

-- 2021-12-21T21:01:10.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'nl_NL')
;

-- 2021-12-21T21:01:17.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 23:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_DE'
;

-- 2021-12-21T21:01:17.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_DE')
;

-- 2021-12-21T21:01:17.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580404,'de_DE')
;

-- 2021-12-21T21:01:17.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID=580404
;

-- 2021-12-21T21:01:17.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL, AD_Element_ID=580404 WHERE UPPER(ColumnName)='ISFORCEPROCESSEDASYNC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-21T21:01:17.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsForceProcessedAsync', Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID=580404 AND IsCentrallyMaintained='Y'
;

-- 2021-12-21T21:01:17.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580404)
;

-- 2021-12-21T21:01:17.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T21:01:17.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Verarbeitung erzwingen', Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', Help=NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T21:01:17.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Verarbeitung erzwingen', Description = 'Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580404
;

-- 2021-12-21T21:01:23.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird der HTTP-Aufruf asynchron verarbeitet und die Antwort besteht nur aus einer "requestId".',Updated=TO_TIMESTAMP('2021-12-21 23:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580404 AND AD_Language='de_CH'
;

-- 2021-12-21T21:01:23.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580404,'de_CH')
;

