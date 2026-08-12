-- 2021-05-04T13:37:48.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584826,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveToGarbage_Partial','N',TO_TIMESTAMP('2021-05-04 16:37:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y','Y',0,'Materialentnahme (partial)','json','Y','N','Java',TO_TIMESTAMP('2021-05-04 16:37:47','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_MoveToGarbage_Partial')
;

-- 2021-05-04T13:37:48.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584826 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-05-04T13:37:52.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 16:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584826
;

-- 2021-05-04T13:37:53.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 16:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584826
;

-- 2021-05-04T13:39:02.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542490,0,584826,541968,11,'QtyTU',TO_TIMESTAMP('2021-05-04 16:39:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','TU Anzahl',10,TO_TIMESTAMP('2021-05-04 16:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:39:02.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541968 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-04T13:39:14.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ValueMin='1',Updated=TO_TIMESTAMP('2021-05-04 16:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541968
;

-- 2021-05-04T13:40:00.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,196,0,584826,541969,19,540539,'C_DocType_ID',TO_TIMESTAMP('2021-05-04 16:40:00','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','de.metas.handlingunits',0,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','Belegart',20,TO_TIMESTAMP('2021-05-04 16:40:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:40:00.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541969 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-04T13:40:22.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,275,0,584826,541970,14,'Description',TO_TIMESTAMP('2021-05-04 16:40:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Beschreibung',30,TO_TIMESTAMP('2021-05-04 16:40:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:40:22.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541970 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-04T13:40:49.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584826,540516,540930,TO_TIMESTAMP('2021-05-04 16:40:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2021-05-04 16:40:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-05-04T13:41:02.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2021-05-04 16:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540930
;

-- 2021-05-06T11:21:55.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='541012',Updated=TO_TIMESTAMP('2021-05-06 14:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541969
;

-- 2021-05-06T11:22:15.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='541012',Updated=TO_TIMESTAMP('2021-05-06 14:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541959
;

-- 2021-05-06T11:22:26.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-06 14:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541969
;

-- 2021-05-06T11:35:15.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='@C_DocType_ID/0@ > 0',Updated=TO_TIMESTAMP('2021-05-06 14:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541969
;

-- 2021-05-06T11:35:27.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ReadOnlyLogic='@C_DocType_ID/0@ > 0',Updated=TO_TIMESTAMP('2021-05-06 14:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541959
;

