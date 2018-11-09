-- 2018-10-26T16:12:08.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 16:12:08','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541023 AND AD_Language='de_CH'
;

-- 2018-10-26T16:12:17.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 16:12:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create export data' WHERE AD_Process_ID=541023 AND AD_Language='en_US'
;

-- 2018-10-26T16:12:23.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,Classname,Type) VALUES (0,'Y',TO_TIMESTAMP('2018-10-26 16:12:23','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-26 16:12:23','YYYY-MM-DD HH24:MI:SS'),'N','N','3','Y','N','N','N',100,541031,'C_DunningDoc_CreateExportData','Y','Y','N','N','N',0,0,'Exportdaten erstellen','de.metas.dunning','de.metas.dunning.export.process.C_DunningDoc_CreateExportData','Java')
;

-- 2018-10-26T16:12:23.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541031 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-10-26T16:12:26.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 16:12:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541031 AND AD_Language='de_CH'
;

-- 2018-10-26T16:12:30.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 16:12:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Create export data' WHERE AD_Process_ID=541031 AND AD_Language='en_US'
;

-- 2018-10-26T16:13:38.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID) VALUES (TO_TIMESTAMP('2018-10-26 16:13:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',540401,'de.metas.dunning',TO_TIMESTAMP('2018-10-26 16:13:38','YYYY-MM-DD HH24:MI:SS'),100,0,'N','N',541031,0)
;


-- 2018-10-26T17:11:40.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (Updated,Created,CreatedBy,IsActive,UpdatedBy,Classname,C_Queue_PackageProcessor_ID,AD_Client_ID,InternalName,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2018-10-26 17:11:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-26 17:11:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,'de.metas.dunning.export.async.C_DunningDoc_CreateExportData',540063,0,'C_DunningDoc_CreateExportData',0,'de.metas.dunning')
;

-- 2018-10-26T17:12:10.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (Updated,Created,CreatedBy,IsActive,UpdatedBy,KeepAliveTimeMillis,PoolSize,C_Queue_Processor_ID,AD_Client_ID,AD_Org_ID,Name) VALUES (TO_TIMESTAMP('2018-10-26 17:12:10','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-26 17:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,1000,1,540052,0,0,'C_DunningDoc_CreateExportData')
;


-- 2018-10-29T18:55:25.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (C_Queue_Processor_ID,C_Queue_PackageProcessor_ID,Updated,Created,CreatedBy,IsActive,UpdatedBy,C_Queue_Processor_Assign_ID,AD_Client_ID,AD_Org_ID) VALUES (540052,540063,TO_TIMESTAMP('2018-10-29 18:55:25','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-29 18:55:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,540084,0,0)
;

UPDATE ad_attachmententry
SET tags=replace(tags, 'InvoiceExportProviderId=forum-datenaustausch.ch', 'InvoiceExportProviderId=forum-datenaustausch.ch-invoice') where tags IS NOT NULL
;