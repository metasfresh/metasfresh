-- 2021-11-19T10:51:30.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='ExternalSystem_Service', TableName='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 12:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541940
;

-- 2021-11-19T10:51:30.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 12:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555645
;

-- 2021-11-19T10:51:30.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE ExternalSystem_WebService_SEQ RENAME TO ExternalSystem_Service_SEQ
;

ALTER TABLE externalsystem_webservice RENAME TO externalsystem_service
;

-- 2021-11-19T10:54:24.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='ExternalSystem_Service_Instance', TableName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 12:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541941
;

-- 2021-11-19T10:54:24.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 12:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555646
;

-- 2021-11-19T10:54:24.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE ExternalSystem_WebService_Instance_SEQ RENAME TO ExternalSystem_Service_Instance_SEQ
;

ALTER TABLE ExternalSystem_WebService_Instance RENAME TO ExternalSystem_Service_Instance
;

-- 2021-11-19T10:56:32.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExternalSystem_Service_ID',Updated=TO_TIMESTAMP('2021-11-19 12:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580248
;

-- 2021-11-19T10:56:32.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_WebService', Description=NULL, Help=NULL WHERE AD_Element_ID=580248
;

-- 2021-11-19T10:56:32.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_WebService', Description=NULL, Help=NULL, AD_Element_ID=580248 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_SERVICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-19T10:56:32.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_WebService', Description=NULL, Help=NULL WHERE AD_Element_ID=580248 AND IsCentrallyMaintained='Y'
;

SELECT public.db_alter_table('ExternalSystem_Service_Instance','ALTER TABLE ExternalSystem_Service_Instance RENAME COLUMN externalsystem_webservice_id TO externalsystem_service_id')
;

SELECT public.db_alter_table('ExternalSystem_Service','ALTER TABLE ExternalSystem_Service RENAME COLUMN externalsystem_webservice_id TO externalsystem_service_id')
;

-- 2021-11-19T11:00:28.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service', PrintName='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 13:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580248 AND AD_Language='de_CH'
;

-- 2021-11-19T11:00:28.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580248,'de_CH') 
;

-- 2021-11-19T11:00:33.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service', PrintName='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 13:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580248 AND AD_Language='de_DE'
;

-- 2021-11-19T11:00:33.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580248,'de_DE') 
;

-- 2021-11-19T11:00:33.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580248,'de_DE') 
;

-- 2021-11-19T11:00:33.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580248
;

-- 2021-11-19T11:00:33.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_Service', Description=NULL, Help=NULL, AD_Element_ID=580248 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_SERVICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-19T11:00:33.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_ID', Name='ExternalSystem_Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580248 AND IsCentrallyMaintained='Y'
;

-- 2021-11-19T11:00:33.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ExternalSystem_Service', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580248) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580248)
;

-- 2021-11-19T11:00:33.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ExternalSystem_Service', Name='ExternalSystem_Service' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580248)
;

-- 2021-11-19T11:00:33.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='ExternalSystem_Service', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580248
;

-- 2021-11-19T11:00:33.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='ExternalSystem_Service', Description=NULL, Help=NULL WHERE AD_Element_ID = 580248
;

-- 2021-11-19T11:00:33.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'ExternalSystem_Service', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580248
;

-- 2021-11-19T11:00:36.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service', PrintName='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 13:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580248 AND AD_Language='en_US'
;

-- 2021-11-19T11:00:36.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580248,'en_US') 
;

-- 2021-11-19T11:00:40.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service', PrintName='ExternalSystem_Service',Updated=TO_TIMESTAMP('2021-11-19 13:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580248 AND AD_Language='nl_NL'
;

-- 2021-11-19T11:00:40.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580248,'nl_NL') 
;

-- 2021-11-19T11:01:44.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExternalSystem_Service_Instance_ID',Updated=TO_TIMESTAMP('2021-11-19 13:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580251
;

-- 2021-11-19T11:01:44.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_WebService_Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580251
;

-- 2021-11-19T11:01:44.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_WebService_Instance', Description=NULL, Help=NULL, AD_Element_ID=580251 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_SERVICE_INSTANCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-19T11:01:44.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_WebService_Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580251 AND IsCentrallyMaintained='Y'
;


SELECT public.db_alter_table('ExternalSystem_Service_Instance','ALTER TABLE ExternalSystem_Service_Instance RENAME COLUMN ExternalSystem_WebService_Instance_ID TO ExternalSystem_Service_Instance_ID')
;


SELECT public.db_alter_table('ExternalSystem_Status','ALTER TABLE ExternalSystem_Status RENAME COLUMN ExternalSystem_WebService_Instance_ID TO ExternalSystem_Service_Instance_ID')
;


-- 2021-11-19T11:03:05.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service_Instance', PrintName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 13:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580251 AND AD_Language='de_CH'
;

-- 2021-11-19T11:03:05.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580251,'de_CH') 
;

-- 2021-11-19T11:03:07.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service_Instance', PrintName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 13:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580251 AND AD_Language='de_DE'
;

-- 2021-11-19T11:03:07.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580251,'de_DE') 
;

-- 2021-11-19T11:03:07.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580251,'de_DE') 
;

-- 2021-11-19T11:03:07.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580251
;

-- 2021-11-19T11:03:07.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL, AD_Element_ID=580251 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_SERVICE_INSTANCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-19T11:03:07.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Service_Instance_ID', Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580251 AND IsCentrallyMaintained='Y'
;

-- 2021-11-19T11:03:07.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580251) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580251)
;

-- 2021-11-19T11:03:07.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ExternalSystem_Service_Instance', Name='ExternalSystem_Service_Instance' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580251)
;

-- 2021-11-19T11:03:07.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580251
;

-- 2021-11-19T11:03:07.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='ExternalSystem_Service_Instance', Description=NULL, Help=NULL WHERE AD_Element_ID = 580251
;

-- 2021-11-19T11:03:07.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'ExternalSystem_Service_Instance', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580251
;

-- 2021-11-19T11:03:10.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service_Instance', PrintName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 13:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580251 AND AD_Language='en_US'
;

-- 2021-11-19T11:03:10.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580251,'en_US') 
;

-- 2021-11-19T11:03:13.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem_Service_Instance', PrintName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 13:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580251 AND AD_Language='nl_NL'
;

-- 2021-11-19T11:03:13.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580251,'nl_NL') 
;

-- 2021-11-19T11:15:30.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_CH'
;

-- 2021-11-19T11:15:30.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_CH') 
;

-- 2021-11-19T11:15:33.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_DE'
;

-- 2021-11-19T11:15:33.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_DE') 
;

-- 2021-11-19T11:15:33.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580257,'de_DE') 
;

-- 2021-11-19T11:15:33.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='ExternalSystem Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580257
;

-- 2021-11-19T11:15:33.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='ExternalSystem Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID=580257 AND IsCentrallyMaintained='Y'
;

-- 2021-11-19T11:15:33.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='ExternalSystem Service Instance', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580257) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580257)
;

-- 2021-11-19T11:15:33.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ExternalSystem WebService Instance', Name='ExternalSystem Service Instance' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580257)
;

-- 2021-11-19T11:15:33.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='ExternalSystem Service Instance', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580257
;

-- 2021-11-19T11:15:33.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='ExternalSystem Service Instance', Description=NULL, Help=NULL WHERE AD_Element_ID = 580257
;

-- 2021-11-19T11:15:33.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'ExternalSystem Service Instance', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580257
;

-- 2021-11-19T11:15:36.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='en_US'
;

-- 2021-11-19T11:15:36.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'en_US') 
;

-- 2021-11-19T11:15:42.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='ExternalSystem Service Instance', PrintName='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='nl_NL'
;

-- 2021-11-19T11:15:42.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'nl_NL') 
;

-- 2021-11-19T11:15:45.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='en_US'
;

-- 2021-11-19T11:15:45.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'en_US') 
;

-- 2021-11-19T11:15:48.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_DE'
;

-- 2021-11-19T11:15:48.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_DE') 
;

-- 2021-11-19T11:15:48.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580257,'de_DE') 
;

-- 2021-11-19T11:15:48.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='ExternalSystem Service Instance', Name='ExternalSystem Service Instance' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580257)
;

-- 2021-11-19T11:15:54.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='ExternalSystem Service Instance',Updated=TO_TIMESTAMP('2021-11-19 13:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580257 AND AD_Language='de_CH'
;

-- 2021-11-19T11:15:54.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580257,'de_CH') 
;

-- 2021-11-19T11:16:50.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:16:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580250 AND AD_Language='de_CH'
;

-- 2021-11-19T11:16:50.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580250,'de_CH') 
;

-- 2021-11-19T11:16:55.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580250 AND AD_Language='de_DE'
;

-- 2021-11-19T11:16:55.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580250,'de_DE') 
;

-- 2021-11-19T11:16:55.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580250,'de_DE') 
;

-- 2021-11-19T11:16:55.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580250
;

-- 2021-11-19T11:16:55.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580250 AND IsCentrallyMaintained='Y'
;

-- 2021-11-19T11:16:55.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External System Service', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580250) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580250)
;

-- 2021-11-19T11:16:55.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External System Service', Name='External System Service' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580250)
;

-- 2021-11-19T11:16:55.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External System Service', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580250
;

-- 2021-11-19T11:16:55.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID = 580250
;

-- 2021-11-19T11:16:55.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External System Service', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580250
;

-- 2021-11-19T11:16:59.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580250 AND AD_Language='en_US'
;

-- 2021-11-19T11:16:59.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580250,'en_US') 
;

-- 2021-11-19T11:17:05.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580250 AND AD_Language='nl_NL'
;

-- 2021-11-19T11:17:05.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580250,'nl_NL') 
;

-- 2021-11-19T11:19:10.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='ExternalSystem_Service_Instance',Updated=TO_TIMESTAMP('2021-11-19 13:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544883
;

-- 2021-11-19T11:20:12.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580258 AND AD_Language='de_CH'
;

-- 2021-11-19T11:20:12.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580258,'de_CH') 
;

-- 2021-11-19T11:20:16.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:20:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580258 AND AD_Language='de_DE'
;

-- 2021-11-19T11:20:16.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580258,'de_DE') 
;

-- 2021-11-19T11:20:16.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580258,'de_DE') 
;

-- 2021-11-19T11:20:16.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580258
;

-- 2021-11-19T11:20:16.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID=580258 AND IsCentrallyMaintained='Y'
;

-- 2021-11-19T11:20:16.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External System Service', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580258) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580258)
;

-- 2021-11-19T11:20:16.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External System Service', Name='External System Service' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580258)
;

-- 2021-11-19T11:20:16.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External System Service', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580258
;

-- 2021-11-19T11:20:16.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External System Service', Description=NULL, Help=NULL WHERE AD_Element_ID = 580258
;

-- 2021-11-19T11:20:16.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External System Service', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580258
;

-- 2021-11-19T11:20:22.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580258 AND AD_Language='en_US'
;

-- 2021-11-19T11:20:22.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580258,'en_US') 
;

-- 2021-11-19T11:20:29Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External System Service', PrintName='External System Service',Updated=TO_TIMESTAMP('2021-11-19 13:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580258 AND AD_Language='nl_NL'
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2021-11-19T11:20:29.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580258,'nl_NL') 
;



