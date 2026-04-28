-- 2017-09-14T14:47:19.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540829,'N','de.metas.payment.esr.process.ESR_Import_LoadFromAttachmentEntry','N',TO_TIMESTAMP('2017-09-14 14:47:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','Y','N','N','N','N','Y',0,'1. V11-Datei importieren (attachment)','N','Y','Java',TO_TIMESTAMP('2017-09-14 14:47:19','YYYY-MM-DD HH24:MI:SS'),100,'ESR_Import_LoadFromAttachmentEntry')
;

-- 2017-09-14T14:47:19.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540829 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-14T14:47:29.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2017-09-14 14:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540829
;

-- 2017-09-14T14:47:54.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3', AD_Client_ID=0, AD_Form_ID=NULL, AD_Org_ID=0, AD_PrintFormat_ID=NULL, AD_ReportView_ID=NULL, AD_Workflow_ID=NULL, AllowProcessReRun='N', Classname='de.metas.payment.esr.process.ESR_Import_LoadFromFile', CopyFromProcess='N', Description=NULL, EntityType='de.metas.payment.esr', Help=NULL, IsActive='Y', IsApplySecuritySettings='N', IsBetaFunctionality='Y', IsDirectPrint='N', IsOneInstanceOnly='N', IsReport='N', IsServerProcess='N', IsUseBPartnerLanguage='Y', JasperReport=NULL, JasperReport_Tabular=NULL, LockWaitTimeout=0, ProcedureName=NULL, RefreshAllAfterExecution='N', ShowHelp='Y', SQLStatement=NULL, Type='Java', WorkflowValue=NULL,Updated=TO_TIMESTAMP('2017-09-14 14:47:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540829
;

-- 2017-09-14T14:47:54.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2295,0,540829,541207,39,'FileName',TO_TIMESTAMP('2017-09-14 14:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL','de.metas.payment.esr',0,'Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','Y','Y','N','Datei öffnen',10,TO_TIMESTAMP('2017-09-14 14:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T14:47:54.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541207 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-09-14T14:47:54.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541207
;

-- 2017-09-14T14:47:54.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541207
;

-- 2017-09-14T14:47:55.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540829,541208,10,'Name',TO_TIMESTAMP('2017-09-14 14:47:54','YYYY-MM-DD HH24:MI:SS'),100,'ESR Import','de.metas.payment.esr',60,'Y','N','Y','Y','N','Name',20,TO_TIMESTAMP('2017-09-14 14:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T14:47:55.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541208 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-09-14T14:47:55.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541208
;

-- 2017-09-14T14:47:55.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541208
;

-- 2017-09-14T14:47:55.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,275,0,540829,541209,10,'Description',TO_TIMESTAMP('2017-09-14 14:47:55','YYYY-MM-DD HH24:MI:SS'),100,'ESR Import Prozess','de.metas.payment.esr',100,'Y','N','Y','Y','N','Beschreibung',30,TO_TIMESTAMP('2017-09-14 14:47:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T14:47:55.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541209 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-09-14T14:47:55.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541209
;

-- 2017-09-14T14:47:55.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541209
;

-- 2017-09-14T14:48:10.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.payment.esr.process.ESR_Import_LoadFromAttachmentEntry', IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2017-09-14 14:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540829
;

-- 2017-09-14T14:50:38.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540370,'AD_Table_ID=get_Table_ID(''ESR_Import'') and Record_ID=@ESR_Import_ID@',TO_TIMESTAMP('2017-09-14 14:50:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','ESR_Import_AD_AttachmentEntry','S',TO_TIMESTAMP('2017-09-14 14:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T14:51:00.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=543390, AD_Reference_ID=19, AD_Val_Rule_ID=540370, ColumnName='AD_AttachmentEntry_ID', Description=NULL, Help=NULL, Name='Attachment entry',Updated=TO_TIMESTAMP('2017-09-14 14:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541207
;

-- 2017-09-14T14:51:21.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540829,540409,TO_TIMESTAMP('2017-09-14 14:51:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',TO_TIMESTAMP('2017-09-14 14:51:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

