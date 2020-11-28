-- 2019-01-23T15:56:55.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541038,'Y','de.metas.ui.web.order.products_proposal.process.WEBUI_Order_ProductsProposal_Launcher','N',TO_TIMESTAMP('2019-01-23 15:56:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N','N','N','N','N','Y',0,'Products Proposal','N','Y','Java',TO_TIMESTAMP('2019-01-23 15:56:54','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Order_ProductsProposal_Launcher')
;

-- 2019-01-23T15:56:55.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541038 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-01-23T16:10:08.844
-- Assign to C_Order
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,541038,259,TO_TIMESTAMP('2019-01-23 16:10:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-01-23 16:10:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2019-01-23T16:10:21.456
-- Assign to C_BPartner
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,541038,291,TO_TIMESTAMP('2019-01-23 16:10:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-01-23 16:10:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

