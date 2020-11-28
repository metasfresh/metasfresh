-- 2018-05-17T14:11:22.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540969,'Y','de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueCuQty','N',TO_TIMESTAMP('2018-05-17 14:11:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'TUs aus Quell-HUs hinzufügen','N','Y','Java',TO_TIMESTAMP('2018-05-17 14:11:22','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_PP_Order_M_Source_HU_IssueCuQty')
;

-- 2018-05-17T14:11:22.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540969 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-05-17T14:11:28.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='CUs aus Quell-HUs hinzufügen',Updated=TO_TIMESTAMP('2018-05-17 14:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540969
;

-- 2018-05-17T14:11:52.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='WEBUI_PP_Order_M_Source_HU_IssueCUQty',Updated=TO_TIMESTAMP('2018-05-17 14:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540969
;

-- 2018-05-17T14:11:56.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueCUQty',Updated=TO_TIMESTAMP('2018-05-17 14:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540969
;

-- 2018-05-17T14:12:20.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,542492,0,540969,541302,11,'QtyCU',TO_TIMESTAMP('2018-05-17 14:12:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Menge CU',10,TO_TIMESTAMP('2018-05-17 14:12:20','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- 2018-05-17T14:12:20.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541302 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-05-17T15:52:41.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-17 15:52:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Issue CUs From Source HUs' WHERE AD_Process_ID=540969 AND AD_Language='en_US'
;

-- 2018-05-17T15:52:46.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-17 15:52:46','YYYY-MM-DD HH24:MI:SS'),Name='CUs aus Quell-HUs hinzufügen' WHERE AD_Process_ID=540969 AND AD_Language='nl_NL'
;

-- 2018-05-17T15:52:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-17 15:52:51','YYYY-MM-DD HH24:MI:SS'),Name='CUs aus Quell-HUs hinzufügen' WHERE AD_Process_ID=540969 AND AD_Language='de_CH'
;

-- 2018-05-17T15:54:24.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-17 15:54:24','YYYY-MM-DD HH24:MI:SS'),Name='Issue CUs from source HUs' WHERE AD_Process_ID=540969 AND AD_Language='en_US'
;




-- 2018-05-21T15:38:40.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2018-05-21 15:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541302
;

