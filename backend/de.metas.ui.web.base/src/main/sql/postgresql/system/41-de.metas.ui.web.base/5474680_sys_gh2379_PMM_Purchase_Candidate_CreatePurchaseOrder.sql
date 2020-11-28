-- 2017-10-18T16:35:41.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540876,'N','de.metas.ui.web.procurement.process.PMM_Purchase_Candidate_CreatePurchaseOrder','N',TO_TIMESTAMP('2017-10-18 16:35:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','N','N','N','N','N','N','Y',0,'Create Purchase Orders','N','Y','Java',TO_TIMESTAMP('2017-10-18 16:35:40','YYYY-MM-DD HH24:MI:SS'),100,'PMM_Purchase_Candidate_CreatePurchaseOrder')
;

-- 2017-10-18T16:35:41.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540876 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-10-18T16:37:09.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540876,540745,TO_TIMESTAMP('2017-10-18 16:37:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',TO_TIMESTAMP('2017-10-18 16:37:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y')
;

