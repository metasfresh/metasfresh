-- 2019-02-20T09:31:43.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,Type,Classname,IsTranslateExcelHeaders) VALUES (0,'Y',TO_TIMESTAMP('2019-02-20 09:31:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-02-20 09:31:43','YYYY-MM-DD HH24:MI:SS'),'N','N','3','Y','N','N','N',100,541061,'WEBUI_ProductsProposal_Delete','Y','Y','N','N','N',0,0,'Löschen','D','Java','de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_Delete','Y')
;

-- 2019-02-20T09:31:43.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541061 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-02-20T09:32:58.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-20 09:32:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541061 AND AD_Language='de_CH'
;

-- 2019-02-20T09:33:44.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-20 09:33:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Remove' WHERE AD_Process_ID=541061 AND AD_Language='en_US'
;

