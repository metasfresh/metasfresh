-- 2018-03-27T14:02:41.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-27 14:02:41','YYYY-MM-DD HH24:MI:SS'),Name='Add/Remove Credit Stop Status' WHERE AD_Process_ID=540938 AND AD_Language='de_CH'
;

-- 2018-03-27T14:02:46.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-27 14:02:46','YYYY-MM-DD HH24:MI:SS'),Name='Add/Remove Credit Stop Status',Help='Add/Remove Credit Stop Status' WHERE AD_Process_ID=540938 AND AD_Language='en_US'
;

-- 2018-03-27T14:02:50.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-27 14:02:50','YYYY-MM-DD HH24:MI:SS'),Name='Add/Remove Credit Stop Status',Help='Add/Remove Credit Stop Status' WHERE AD_Process_ID=540938 AND AD_Language='nl_NL'
;

-- 2018-03-27T14:02:54.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-27 14:02:54','YYYY-MM-DD HH24:MI:SS'),Help='Add/Remove Credit Stop Status' WHERE AD_Process_ID=540938 AND AD_Language='de_CH'
;

-- 2018-03-27T14:03:36.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540947,'Y','org.adempiere.bpartner.process.BPartnerStats_ComputeBPartnerStats','N',TO_TIMESTAMP('2018-03-27 14:03:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Add/Remove Credit Stop Status','Y','N','N','N','N','N','N','Y',0,'Compute BPartnerStats','N','Y','Java',TO_TIMESTAMP('2018-03-27 14:03:36','YYYY-MM-DD HH24:MI:SS'),100,'BPartnerStats_ComputeBPartnerStats')
;

-- 2018-03-27T14:03:36.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540947 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-27T14:03:55.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540947,540763,TO_TIMESTAMP('2018-03-27 14:03:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2018-03-27 14:03:55','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;


-- 2018-03-28T14:55:07.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.adempiere.bpartner.process.C_BPartner_Stats_ComputeBPartnerStats', Help='', Value='C_BPartner_Stats_ComputeBPartnerStats',Updated=TO_TIMESTAMP('2018-03-28 14:55:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540947
;


