-- 2019-01-28T13:39:42.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540666
;

-- 2019-01-28T13:40:44.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,Type,Classname) VALUES (0,'Y',TO_TIMESTAMP('2019-01-28 13:40:44','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-28 13:40:44','YYYY-MM-DD HH24:MI:SS'),'N','N','3','Y','N','N','N',100,541039,'WEBUI_BPartner_ProductsProposal_Launcher','Y','Y','N','N','N',0,0,'Products Proposal','D','Java','de.metas.ui.web.order.products_proposal.process.WEBUI_BPartner_ProductsProposal_Launcher')
;

-- 2019-01-28T13:40:44.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541039 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-01-28T13:41:09.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541039,291,540669,TO_TIMESTAMP('2019-01-28 13:41:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-01-28 13:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

