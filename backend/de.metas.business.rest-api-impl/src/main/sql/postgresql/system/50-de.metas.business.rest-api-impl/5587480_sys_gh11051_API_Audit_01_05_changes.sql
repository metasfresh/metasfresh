-- 2021-05-05T09:19:50.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='API Antwort Revision', PrintName='API Antwort Revision',Updated=TO_TIMESTAMP('2021-05-05 12:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579126 AND AD_Language='de_CH'
;

-- 2021-05-05T09:19:50.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579126,'de_CH') 
;

-- 2021-05-05T09:19:56.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='API Antwort Revision', PrintName='API Antwort Revision',Updated=TO_TIMESTAMP('2021-05-05 12:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579126 AND AD_Language='de_DE'
;

-- 2021-05-05T09:19:56.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579126,'de_DE') 
;

-- 2021-05-05T09:19:56.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579126,'de_DE') 
;

-- 2021-05-05T09:19:56.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='API_Response_Audit_ID', Name='API Antwort Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579126
;

-- 2021-05-05T09:19:56.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Response_Audit_ID', Name='API Antwort Revision', Description=NULL, Help=NULL, AD_Element_ID=579126 WHERE UPPER(ColumnName)='API_RESPONSE_AUDIT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-05T09:19:56.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Response_Audit_ID', Name='API Antwort Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579126 AND IsCentrallyMaintained='Y'
;

-- 2021-05-05T09:19:56.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='API Antwort Revision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579126) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579126)
;

-- 2021-05-05T09:19:56.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='API Antwort Revision', Name='API Antwort Revision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579126)
;

-- 2021-05-05T09:19:56.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='API Antwort Revision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579126
;

-- 2021-05-05T09:19:56.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='API Antwort Revision', Description=NULL, Help=NULL WHERE AD_Element_ID = 579126
;

-- 2021-05-05T09:19:56.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'API Antwort Revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579126
;

-- 2021-05-05T09:20:03.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_response_audit','API_Response_Audit_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T09:20:36.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='API Revision Einstellung', PrintName='API Revision Einstellung',Updated=TO_TIMESTAMP('2021-05-05 12:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579109 AND AD_Language='de_CH'
;

-- 2021-05-05T09:20:36.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579109,'de_CH') 
;

-- 2021-05-05T09:22:06.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_response_audit','API_Request_Audit_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T09:24:28.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-05-05 12:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579123 AND AD_Language='de_CH'
;

-- 2021-05-05T09:24:28.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579123,'de_CH') 
;

-- 2021-05-05T09:24:34.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-05-05 12:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579123 AND AD_Language='de_DE'
;

-- 2021-05-05T09:24:34.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579123,'de_DE') 
;

-- 2021-05-05T09:24:34.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579123,'de_DE') 
;

-- 2021-05-05T09:24:34.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='API_Request_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579123
;

-- 2021-05-05T09:24:34.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Request_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL, AD_Element_ID=579123 WHERE UPPER(ColumnName)='API_REQUEST_AUDIT_LOG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-05T09:24:34.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Request_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579123 AND IsCentrallyMaintained='Y'
;

-- 2021-05-05T09:24:34.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579123) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579123)
;

-- 2021-05-05T09:24:34.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Log', Name='Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579123)
;

-- 2021-05-05T09:24:34.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579123
;

-- 2021-05-05T09:24:34.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID = 579123
;

-- 2021-05-05T09:24:34.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579123
;

-- 2021-05-05T09:24:39.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-05-05 12:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579123 AND AD_Language='en_US'
;

-- 2021-05-05T09:24:39.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579123,'en_US') 
;

-- 2021-05-05T09:24:44.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-05-05 12:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579123 AND AD_Language='nl_NL'
;

-- 2021-05-05T09:24:44.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579123,'nl_NL') 
;

-- 2021-05-05T09:24:48.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit_log','API_Request_Audit_Log_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T10:50:42.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645166,0,543895,584488,545768,'F',TO_TIMESTAMP('2021-05-05 13:50:42','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',50,0,0,TO_TIMESTAMP('2021-05-05 13:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-05T10:50:53.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-05 13:50:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584488
;

-- 2021-05-05T10:57:00.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-05-05 13:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573800
;

-- 2021-05-05T10:58:02.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 13:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T10:58:09.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 13:58:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T10:58:12.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','API_Audit_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T10:58:37.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','AD_User_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T10:58:50.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 13:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T10:58:50.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','API_Audit_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T11:00:48.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 14:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T11:01:21.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 14:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T11:01:27.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 14:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T11:02:03.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2021-05-05 14:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573745
;

-- 2021-05-05T11:02:08.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','API_Audit_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T11:02:54.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2021-05-05 14:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573728
;

-- 2021-05-05T11:06:32.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='API Aufruf Revision', PrintName='API Aufruf Revision',Updated=TO_TIMESTAMP('2021-05-05 14:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579109 AND AD_Language='de_CH'
;

-- 2021-05-05T11:06:32.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579109,'de_CH') 
;

-- 2021-05-05T11:06:40.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='API Aufruf Revision', PrintName='API Aufruf Revision',Updated=TO_TIMESTAMP('2021-05-05 14:06:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579109 AND AD_Language='de_DE'
;

-- 2021-05-05T11:06:40.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579109,'de_DE') 
;

-- 2021-05-05T11:06:40.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579109,'de_DE') 
;

-- 2021-05-05T11:06:40.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='API_Request_Audit_ID', Name='API Aufruf Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579109
;

-- 2021-05-05T11:06:40.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Request_Audit_ID', Name='API Aufruf Revision', Description=NULL, Help=NULL, AD_Element_ID=579109 WHERE UPPER(ColumnName)='API_REQUEST_AUDIT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-05T11:06:40.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='API_Request_Audit_ID', Name='API Aufruf Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579109 AND IsCentrallyMaintained='Y'
;

-- 2021-05-05T11:06:40.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='API Aufruf Revision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579109) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579109)
;

-- 2021-05-05T11:06:40.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='API Aufruf Revision', Name='API Aufruf Revision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579109)
;

-- 2021-05-05T11:06:40.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='API Aufruf Revision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579109
;

-- 2021-05-05T11:06:40.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='API Aufruf Revision', Description=NULL, Help=NULL WHERE AD_Element_ID = 579109
;

-- 2021-05-05T11:06:40.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'API Aufruf Revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579109
;

-- 2021-05-05T11:07:12.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_request_audit','API_Request_Audit_ID','NUMERIC(10)',null,null)
;

-- 2021-05-05T14:50:15.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='DELETE', Value='DELETE', ValueName='DELETE',Updated=TO_TIMESTAMP('2021-05-05 17:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542485
;

-- 2021-05-05T14:50:31.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='DELETE',Updated=TO_TIMESTAMP('2021-05-05 17:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542485
;

-- 2021-05-05T14:50:37.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='DELETE',Updated=TO_TIMESTAMP('2021-05-05 17:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542485
;

-- 2021-05-05T15:02:05.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=542485
;

-- 2021-05-05T15:02:05.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=542485
;

-- 2021-05-05T15:04:40.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542490,541306,TO_TIMESTAMP('2021-05-05 18:04:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','DELETE',TO_TIMESTAMP('2021-05-05 18:04:40','YYYY-MM-DD HH24:MI:SS'),100,'DELETE','DELETE')
;

-- 2021-05-05T15:04:40.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542490 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-05T15:19:57.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-05-05 18:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573733
;

-- 2021-05-05T15:20:03.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-05-05 18:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573732
;

-- 2021-05-05T15:20:07.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-05-05 18:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573735
;

-- 2021-05-05T15:20:10.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-05-05 18:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573734
;

-- 2021-05-05T15:24:05.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseDays','VARCHAR(255)',null,null)
;

-- 2021-05-05T15:24:05.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseDays',null,'NULL',null)
;

-- 2021-05-05T15:24:10.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseBodyDays','VARCHAR(255)',null,null)
;

-- 2021-05-05T15:24:10.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepResponseBodyDays',null,'NULL',null)
;

-- 2021-05-05T15:24:13.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestDays','VARCHAR(255)',null,null)
;

-- 2021-05-05T15:24:13.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestDays',null,'NULL',null)
;

-- 2021-05-05T15:24:17.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestBodyDays','VARCHAR(255)',null,null)
;

-- 2021-05-05T15:24:17.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepRequestBodyDays',null,'NULL',null)
;

