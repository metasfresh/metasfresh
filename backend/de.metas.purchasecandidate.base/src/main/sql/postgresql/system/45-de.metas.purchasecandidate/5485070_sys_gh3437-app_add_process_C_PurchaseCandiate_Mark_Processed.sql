-- 2018-02-09T16:48:02.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540920,'Y','de.metas.purchasecandidate.process.C_PurchaseCandiate_Mark_Processed','N',TO_TIMESTAMP('2018-02-09 16:48:02','YYYY-MM-DD HH24:MI:SS'),100,'Markiert den betreffenden Datensatz als verarbeitet, auch wenn noch nicht die komplette benötigte Menge bestellt wurde.','de.metas.purchasecandidate','Y','N','N','N','N','N','N','Y',0,'Abschließen','N','Y','Java',TO_TIMESTAMP('2018-02-09 16:48:02','YYYY-MM-DD HH24:MI:SS'),100,'C_PurchaseCandiate_Mark_Processed')
;

-- 2018-02-09T16:48:02.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540920 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-02-09T17:01:45.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540920,540861,TO_TIMESTAMP('2018-02-09 17:01:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y',TO_TIMESTAMP('2018-02-09 17:01:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N')
;

