-- 2017-12-18T12:19:33.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToHU','3','Y','N','N','N',540901,'Y','Y','N','Java','N','N',0,0,'Take Out and add to HU','de.metas.ui.web.pickingslotsClearing.process.WEBUI_PickingSlotsClearingView_TakeOutHUAndAddToHU','de.metas.picking',100,TO_TIMESTAMP('2017-12-18 12:19:32','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-18 12:19:32','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-18T12:19:33.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540901 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-18T12:19:40.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.picking',Updated=TO_TIMESTAMP('2017-12-18 12:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540874
;

-- 2017-12-18T12:19:47.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2017-12-18 12:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540901
;

-- 2017-12-18T12:35:03.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540901,29,'N',541253,'Y',542492,'N','QtyCU','Y','N',0,'Menge CU','de.metas.handlingunits',100,TO_TIMESTAMP('2017-12-18 12:35:03','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-18 12:35:03','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2017-12-18T12:35:03.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541253 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-18T12:35:13.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.picking',Updated=TO_TIMESTAMP('2017-12-18 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541253
;

