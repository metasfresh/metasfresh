-- 2020-04-23T12:02:46.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577683,0,'OffsetDays',TO_TIMESTAMP('2020-04-23 15:02:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Offset days','Offset days',TO_TIMESTAMP('2020-04-23 15:02:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T12:02:46.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577683 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-04-23T12:03:23.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577683,0,584679,541811,11,'OffsetDays',TO_TIMESTAMP('2020-04-23 15:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider',5,'Y','N','Y','N','N','N','Offset days',30,TO_TIMESTAMP('2020-04-23 15:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T12:03:23.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541811 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-23T12:04:00.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=541811
;

-- 2020-04-23T12:04:00.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=541811
;

-- 2020-04-23T12:04:46.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584679,541812,15,'DateFrom',TO_TIMESTAMP('2020-04-23 15:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','de.metas.serviceprovider',7,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',30,TO_TIMESTAMP('2020-04-23 15:04:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T12:04:46.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541812 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-23T12:05:28.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=5,Updated=TO_TIMESTAMP('2020-04-23 15:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541812
;

-- 2020-04-23T12:06:03.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External project reference ID', PrintName='External project reference ID',Updated=TO_TIMESTAMP('2020-04-23 15:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577601 AND AD_Language='de_CH'
;

-- 2020-04-23T12:06:03.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577601,'de_CH') 
;

-- 2020-04-23T12:06:08.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External project reference ID', PrintName='External project reference ID',Updated=TO_TIMESTAMP('2020-04-23 15:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577601 AND AD_Language='de_DE'
;

-- 2020-04-23T12:06:08.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577601,'de_DE') 
;

-- 2020-04-23T12:06:08.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577601,'de_DE') 
;

-- 2020-04-23T12:06:08.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='S_ExternalProjectReference_ID', Name='External project reference ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577601
;

-- 2020-04-23T12:06:08.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='S_ExternalProjectReference_ID', Name='External project reference ID', Description=NULL, Help=NULL, AD_Element_ID=577601 WHERE UPPER(ColumnName)='S_EXTERNALPROJECTREFERENCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-23T12:06:08.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='S_ExternalProjectReference_ID', Name='External project reference ID', Description=NULL, Help=NULL WHERE AD_Element_ID=577601 AND IsCentrallyMaintained='Y'
;

-- 2020-04-23T12:06:08.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External project reference ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577601) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577601)
;

-- 2020-04-23T12:06:08.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External project reference ID', Name='External project reference ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577601)
;

-- 2020-04-23T12:06:08.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External project reference ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577601
;

-- 2020-04-23T12:06:08.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External project reference ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 577601
;

-- 2020-04-23T12:06:08.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External project reference ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577601
;

-- 2020-04-23T12:06:14.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External project reference ID', PrintName='External project reference ID',Updated=TO_TIMESTAMP('2020-04-23 15:06:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577601 AND AD_Language='en_US'
;

-- 2020-04-23T12:06:14.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577601,'en_US') 
;

-- 2020-04-23T12:06:20.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External project reference ID', PrintName='External project reference ID',Updated=TO_TIMESTAMP('2020-04-23 15:06:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577601 AND AD_Language='nl_NL'
;

-- 2020-04-23T12:06:20.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577601,'nl_NL') 
;

-- 2020-04-23T12:13:17.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,584688,'Y','de.metas.serviceprovider.github.ScheduledGithubImport','N',TO_TIMESTAMP('2020-04-23 15:13:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Y','Y',0,'Scheduled Github import','N','N','Java',TO_TIMESTAMP('2020-04-23 15:13:17','YYYY-MM-DD HH24:MI:SS'),100,'Scheduled Github import')
;

-- 2020-04-23T12:13:17.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584688 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-04-23T12:14:27.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577683,0,584688,541813,11,'OffsetDays',TO_TIMESTAMP('2020-04-23 15:14:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider',5,'Y','N','Y','N','N','N','Offset days',10,TO_TIMESTAMP('2020-04-23 15:14:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T12:14:27.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541813 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-23T12:26:26.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584688,0,550061,TO_TIMESTAMP('2020-04-23 15:26:26','YYYY-MM-DD HH24:MI:SS'),100,'00 12/12 * * *','de.metas.serviceprovider',0,'D','Y','N',7,'N','Github Import scheduler','N','P','C','NEW',100,TO_TIMESTAMP('2020-04-23 15:26:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T12:26:50.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,541813,550061,540010,TO_TIMESTAMP('2020-04-23 15:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','1',TO_TIMESTAMP('2020-04-23 15:26:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T13:44:52.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='00 00,12 * * *',Updated=TO_TIMESTAMP('2020-04-23 16:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550061
;

-- 2020-04-23T13:47:27.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='0 0,12 * * *',Updated=TO_TIMESTAMP('2020-04-23 16:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550061
;

-- 2020-04-23T13:52:38.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SchedulerRecipient (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Scheduler_ID,AD_SchedulerRecipient_ID,AD_User_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,0,550061,540003,100,TO_TIMESTAMP('2020-04-23 16:52:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2020-04-23 16:52:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T13:55:45.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,584689,'Y','de.metas.serviceprovider.everhour.ScheduledEverhourImport','N',TO_TIMESTAMP('2020-04-23 16:55:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Y','Y',0,'Scheduled Everhour import','N','N','Java',TO_TIMESTAMP('2020-04-23 16:55:45','YYYY-MM-DD HH24:MI:SS'),100,'Scheduled Everhour import')
;

-- 2020-04-23T13:55:45.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584689 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-04-23T13:56:35.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577683,0,584689,541814,11,'OffsetDays',TO_TIMESTAMP('2020-04-23 16:56:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider',5,'Y','N','Y','N','N','N','Offset days',10,TO_TIMESTAMP('2020-04-23 16:56:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T13:56:35.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541814 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-04-23T13:57:50.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,584689,0,550062,TO_TIMESTAMP('2020-04-23 16:57:50','YYYY-MM-DD HH24:MI:SS'),100,'0 1,13 * * *','de.metas.swat',0,'D','Y','N',7,'N','Everhour import scheduler','N','P','C','NEW',100,TO_TIMESTAMP('2020-04-23 16:57:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T13:58:35.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,541814,550062,540011,TO_TIMESTAMP('2020-04-23 16:58:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','7',TO_TIMESTAMP('2020-04-23 16:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-23T14:03:57.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='0 1,14 * * *',Updated=TO_TIMESTAMP('2020-04-23 17:03:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

-- 2020-04-23T14:04:33.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='0 1,13 * * *',Updated=TO_TIMESTAMP('2020-04-23 17:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

-- 2020-04-23T14:04:42.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='00 1,13 * * *',Updated=TO_TIMESTAMP('2020-04-23 17:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

-- 2020-04-23T14:05:07.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='00 13 * * *',Updated=TO_TIMESTAMP('2020-04-23 17:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

-- 2020-04-23T14:06:55.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern='0 1,13 * * *',Updated=TO_TIMESTAMP('2020-04-23 17:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;

