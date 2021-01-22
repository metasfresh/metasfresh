-- 2021-01-22T17:12:00.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,Name,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,IsNotifyUserAfterExecution,PostgrestResponseFormat,AD_Org_ID,Value,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2021-01-22 19:12:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-22 19:12:00','YYYY-MM-DD HH24:MI:SS'),'N','N','Record Work','3','N','N','N',100,584792,'Y','Y','N','N','N',0,'Java','Y','N','json',0,'PP_Order_RecordWork','de.metas.ui.web.pporder.process.PP_Order_RecordWork','EE01')
;

-- 2021-01-22T17:12:00.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Name,Help,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Name,t.Help,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584792 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-01-22T17:17:36.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540530,'PP_Order_Node.PP_Order_ID=@PP_Order_ID@',TO_TIMESTAMP('2021-01-22 19:17:36','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','PP_Order_Node of PP_Order','S',TO_TIMESTAMP('2021-01-22 19:17:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T17:17:51.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53285,0,584792,541903,18,540530,'PP_Order_Node_ID',TO_TIMESTAMP('2021-01-22 19:17:50','YYYY-MM-DD HH24:MI:SS'),100,'Workflow Node (activity), step or process','EE01',0,'The Workflow Node indicates a unique step or process in a Workflow.','Y','N','Y','N','Y','N','Manufacturing Order Activity',10,TO_TIMESTAMP('2021-01-22 19:17:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T17:17:51.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541903 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-01-22T17:22:59.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2321,0,584792,541904,17,299,'DurationUnit',TO_TIMESTAMP('2021-01-22 19:22:59','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration','EE01',0,'Unit to define the length of time for the execution','Y','N','Y','N','Y','N','Duration Unit',20,TO_TIMESTAMP('2021-01-22 19:22:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T17:22:59.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541904 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-01-22T17:23:22.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2320,0,584792,541905,11,'Duration',TO_TIMESTAMP('2021-01-22 19:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Normal Duration in Duration Unit','EE01',0,'Expected (normal) Length of time for the execution','Y','N','Y','N','Y','N','Duration',30,TO_TIMESTAMP('2021-01-22 19:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T17:23:22.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541905 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-01-22T18:22:23.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584792,53027,540891,TO_TIMESTAMP('2021-01-22 20:22:23','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2021-01-22 20:22:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;






























-- 2021-01-22T21:18:06.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578666,0,'DurationBooked',TO_TIMESTAMP('2021-01-22 23:18:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Duration Already Booked','Duration Already Booked',TO_TIMESTAMP('2021-01-22 23:18:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T21:18:06.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578666 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-01-22T21:18:36.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,578666,0,584792,541906,11,'DurationBooked',TO_TIMESTAMP('2021-01-22 23:18:36','YYYY-MM-DD HH24:MI:SS'),100,'EE01',0,'Y','N','Y','N','N','N','Duration Already Booked','1=1',40,TO_TIMESTAMP('2021-01-22 23:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T21:18:36.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541906 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-01-22T21:23:46.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='1=2',Updated=TO_TIMESTAMP('2021-01-22 23:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541904
;

-- 2021-01-22T21:24:52.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=41,Updated=TO_TIMESTAMP('2021-01-22 23:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541905
;

-- 2021-01-22T21:24:56.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2021-01-22 23:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541906
;

-- 2021-01-22T21:24:59.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=40,Updated=TO_TIMESTAMP('2021-01-22 23:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541905
;

-- 2021-01-22T21:25:29.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-01-22 23:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541904
;

