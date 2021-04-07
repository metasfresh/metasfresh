-- 2021-02-11T15:01:58.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584802,'Y','de.metas.externalsystem.process.InvokeAlbertaAction','N',TO_TIMESTAMP('2021-02-11 17:01:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','N','N','N','N','N','N','Y','Y',0,'Call External System','json','N','N','Java',TO_TIMESTAMP('2021-02-11 17:01:58','YYYY-MM-DD HH24:MI:SS'),100,'Call_External_System_Alberta')
;

-- 2021-02-11T15:01:58.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584802 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-02-11T15:06:46.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584802,541931,17,'Request',TO_TIMESTAMP('2021-02-11 17:06:46','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.externalreference',0,'Y','N','Y','N','N','N','Request',10,TO_TIMESTAMP('2021-02-11 17:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-11T15:06:46.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541931 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-11T15:10:01.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541256,TO_TIMESTAMP('2021-02-11 17:10:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Alberta accepted operations',TO_TIMESTAMP('2021-02-11 17:10:01','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-02-11T15:10:01.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541256 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-11T15:10:37.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2021-02-11 17:10:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541256
;

-- 2021-02-11T15:11:28.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541256,542269,TO_TIMESTAMP('2021-02-11 17:11:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','Patienten bekommen',TO_TIMESTAMP('2021-02-11 17:11:28','YYYY-MM-DD HH24:MI:SS'),100,'getPatients','Patienten bekommen')
;

-- 2021-02-11T15:11:28.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542269 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-11T15:11:44.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Get patients',Updated=TO_TIMESTAMP('2021-02-11 17:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542269
;

-- 2021-02-11T15:20:54.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541256, FieldLength=30,Updated=TO_TIMESTAMP('2021-02-11 17:20:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541931
;

-- 2021-02-11T15:22:18.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578757,0,'External_Request',TO_TIMESTAMP('2021-02-11 17:22:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','External Request','External Request',TO_TIMESTAMP('2021-02-11 17:22:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-11T15:22:18.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578757 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-11T15:22:58.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578757, ColumnName='External_Request', IsMandatory='Y', Name='External Request',Updated=TO_TIMESTAMP('2021-02-11 17:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541931
;

-- 2021-02-11T15:23:50.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584802,541577,540900,TO_TIMESTAMP('2021-02-11 17:23:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',TO_TIMESTAMP('2021-02-11 17:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-02-11T15:36:56.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584802,541576,540901,TO_TIMESTAMP('2021-02-11 17:36:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',TO_TIMESTAMP('2021-02-11 17:36:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-02-11T15:56:08.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Invoke External System',Updated=TO_TIMESTAMP('2021-02-11 17:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584802
;

-- 2021-02-11T15:56:23.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Name='Invoke External System',Updated=TO_TIMESTAMP('2021-02-11 17:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584802
;

-- 2021-02-11T16:05:13.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Request',Updated=TO_TIMESTAMP('2021-02-11 18:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='en_US'
;

-- 2021-02-11T16:05:13.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'en_US') 
;

-- 2021-02-11T16:09:39.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584802,541932,10,'ConfigId',TO_TIMESTAMP('2021-02-11 18:09:38','YYYY-MM-DD HH24:MI:SS'),100,'0=1','U',0,'Y','N','Y','N','N','N','configId',20,TO_TIMESTAMP('2021-02-11 18:09:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-11T16:09:39.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541932 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-12T09:30:52.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545023,0,TO_TIMESTAMP('2021-02-12 11:30:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','No record selection for the {0} tab.','E',TO_TIMESTAMP('2021-02-12 11:30:52','YYYY-MM-DD HH24:MI:SS'),100,'NoExternalSelection')
;

-- 2021-02-12T09:30:52.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545023 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-02-12T09:31:41.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545024,0,TO_TIMESTAMP('2021-02-12 11:31:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','Multiple records selected for the {0} tab.','E',TO_TIMESTAMP('2021-02-12 11:31:41','YYYY-MM-DD HH24:MI:SS'),100,'MultipleExternalSelection')
;

-- 2021-02-12T09:31:41.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545024 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-02-12T12:21:30.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584802,541933,24,'Since',TO_TIMESTAMP('2021-02-12 14:21:29','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','since',30,TO_TIMESTAMP('2021-02-12 14:21:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-12T12:21:30.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541933 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-02-12T12:21:46.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2021-02-12 14:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541933
;

-- 2021-02-12T12:22:58.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2021-02-12 14:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541933
;

-- 2021-02-12T13:50:37.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='since',Updated=TO_TIMESTAMP('2021-02-12 15:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541933
;

-- 2021-02-12T14:34:52.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='External Request',Updated=TO_TIMESTAMP('2021-02-12 16:34:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541931
;

-- 2021-02-15T10:20:34.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Externes System aufrufen',Updated=TO_TIMESTAMP('2021-02-15 12:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584802
;

-- 2021-02-15T10:20:36.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Externes System aufrufen',Updated=TO_TIMESTAMP('2021-02-15 12:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584802
;

-- 2021-02-15T10:20:36.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Externes System aufrufen',Updated=TO_TIMESTAMP('2021-02-15 12:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584802
;

-- 2021-02-15T10:20:50.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Externes System aufrufen',Updated=TO_TIMESTAMP('2021-02-15 12:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584802
;

-- 2021-02-15T10:21:27.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anfrage', PrintName='Externes System aufrufen',Updated=TO_TIMESTAMP('2021-02-15 12:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='de_CH'
;

-- 2021-02-15T10:21:28.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'de_CH') 
;

-- 2021-02-15T10:21:29.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anfrage',Updated=TO_TIMESTAMP('2021-02-15 12:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='de_DE'
;

-- 2021-02-15T10:21:29.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'de_DE') 
;

-- 2021-02-15T10:21:29.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578757,'de_DE') 
;

-- 2021-02-15T10:21:29.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='External_Request', Name='Anfrage', Description=NULL, Help=NULL WHERE AD_Element_ID=578757
;

-- 2021-02-15T10:21:29.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='External_Request', Name='Anfrage', Description=NULL, Help=NULL, AD_Element_ID=578757 WHERE UPPER(ColumnName)='EXTERNAL_REQUEST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-15T10:21:29.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='External_Request', Name='Anfrage', Description=NULL, Help=NULL WHERE AD_Element_ID=578757 AND IsCentrallyMaintained='Y'
;

-- 2021-02-15T10:21:29.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anfrage', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578757) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578757)
;

-- 2021-02-15T10:21:29.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External Request', Name='Anfrage' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578757)
;

-- 2021-02-15T10:21:29.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anfrage', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578757
;

-- 2021-02-15T10:21:29.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anfrage', Description=NULL, Help=NULL WHERE AD_Element_ID = 578757
;

-- 2021-02-15T10:21:29.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anfrage', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578757
;

-- 2021-02-15T10:21:32.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anfrage', PrintName='Anfrage',Updated=TO_TIMESTAMP('2021-02-15 12:21:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='nl_NL'
;

-- 2021-02-15T10:21:32.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'nl_NL') 
;

-- 2021-02-15T10:21:34.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Anfrage',Updated=TO_TIMESTAMP('2021-02-15 12:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='de_DE'
;

-- 2021-02-15T10:21:34.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'de_DE') 
;

-- 2021-02-15T10:21:34.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578757,'de_DE') 
;

-- 2021-02-15T10:21:34.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anfrage', Name='Anfrage' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578757)
;

-- 2021-02-15T10:21:38.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Anfrage',Updated=TO_TIMESTAMP('2021-02-15 12:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='de_CH'
;

-- 2021-02-15T10:21:38.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'de_CH') 
;

-- 2021-02-15T10:21:45.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Request',Updated=TO_TIMESTAMP('2021-02-15 12:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578757 AND AD_Language='en_US'
;

-- 2021-02-15T10:21:45.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578757,'en_US') 
;

-- 2021-02-15T10:22:50.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Patiente abrufen',Updated=TO_TIMESTAMP('2021-02-15 12:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542269
;

-- 2021-02-15T10:22:52.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Patiente abrufen',Updated=TO_TIMESTAMP('2021-02-15 12:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542269
;

-- 2021-02-15T10:23:01.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Patiente abrufen',Updated=TO_TIMESTAMP('2021-02-15 12:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542269
;

-- 2021-02-15T10:23:13.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Ruft alle Alberta Patienten mit den zugehörigen Institutionen wie Krankenkasse, Pflegeheim usw. ab und legt sie als Geschäftspartner in metasfresh an',Updated=TO_TIMESTAMP('2021-02-15 12:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541256
;

-- 2021-02-15T10:23:22.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='',Updated=TO_TIMESTAMP('2021-02-15 12:23:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541256
;

-- 2021-02-15T10:23:32.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Ruft alle Alberta Patienten mit den zugehörigen Institutionen wie Krankenkasse, Pflegeheim usw. ab und legt sie als Geschäftspartner in metasfresh an',Updated=TO_TIMESTAMP('2021-02-15 12:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542269
;

-- 2021-02-15T10:23:39.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Ruft alle Alberta Patienten mit den zugehörigen Institutionen wie Krankenkasse, Pflegeheim usw. ab und legt sie als Geschäftspartner in metasfresh an',Updated=TO_TIMESTAMP('2021-02-15 12:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542269
;

-- 2021-02-15T10:25:06.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nur ein {0} Konfig-Datensatz darf gewählt werden',Updated=TO_TIMESTAMP('2021-02-15 12:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545024
;

-- 2021-02-15T10:25:07.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nur ein {0} Konfig-Datensatz darf gewählt werden',Updated=TO_TIMESTAMP('2021-02-15 12:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545024
;

-- 2021-02-15T10:25:10.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nur ein {0} Konfig-Datensatz darf gewählt werden',Updated=TO_TIMESTAMP('2021-02-15 12:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545024
;

-- 2021-02-15T10:25:12.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-15 12:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545024
;

-- 2021-02-15T10:25:33.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Kein {0} Konfig-Datensatz gewählt',Updated=TO_TIMESTAMP('2021-02-15 12:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545023
;

-- 2021-02-15T10:25:34.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Kein {0} Konfig-Datensatz gewählt',Updated=TO_TIMESTAMP('2021-02-15 12:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545023
;

-- 2021-02-15T10:25:36.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Kein {0} Konfig-Datensatz gewählt',Updated=TO_TIMESTAMP('2021-02-15 12:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545023
;

-- 2021-02-15T10:25:42.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No record selection for the {0} tab',Updated=TO_TIMESTAMP('2021-02-15 12:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545023
;

-- 2021-02-15T10:26:00.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Kein {0} Konfig-Datensatz gewählt',Updated=TO_TIMESTAMP('2021-02-15 12:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545023
;

-- 2021-02-15T10:26:41.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='Zeitpunkt der letzten abfrage. Es werden nur Patienten und Institutionen abgerufen, die seit diesem Zeitpunkt erstellt oder geändert wurden',Updated=TO_TIMESTAMP('2021-02-15 12:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541933
;

-- 2021-02-15T10:26:54.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Zeitpunkt der letzten abfrage. Es werden nur Patienten und Institutionen abgerufen, die seit diesem Zeitpunkt erstellt oder geändert wurden', Name='Seit',Updated=TO_TIMESTAMP('2021-02-15 12:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=541933
;

-- 2021-02-15T10:26:55.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Seit',Updated=TO_TIMESTAMP('2021-02-15 12:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=541933
;

-- 2021-02-15T10:26:58.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Seit',Updated=TO_TIMESTAMP('2021-02-15 12:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=541933
;

-- 2021-02-15T10:27:03.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='Y', Name='Since',Updated=TO_TIMESTAMP('2021-02-15 12:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=541933
;

-- 2021-02-15T10:29:11.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Name='Seit',Updated=TO_TIMESTAMP('2021-02-15 12:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541933
;
