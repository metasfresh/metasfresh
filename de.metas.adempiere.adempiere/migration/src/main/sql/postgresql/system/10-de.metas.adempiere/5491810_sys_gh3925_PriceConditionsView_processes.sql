-- 2018-04-25T01:05:42.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_SalesOrder_PricingConditionsView_Launcher','3','D','N','Y','N','N',540953,'N','Y','N','Java','N','N',0,0,'Pricing Conditions','de.metas.ui.web.order.sales.pricingConditions.process.WEBUI_SalesOrder_PricingConditionsView_Launcher',100,TO_TIMESTAMP('2018-04-25 01:05:42','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-25 01:05:42','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-25T01:05:42.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540953 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-04-25T01:06:45.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Window_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('D',100,'Y',259,0,'N','N',540953,143,0,100,TO_TIMESTAMP('2018-04-25 01:06:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-25 01:06:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-25T16:32:40.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','PricingConditionsView_CopyRowToEditable','3','D','Y','N','N','N',540954,'N','Y','N','Java','N','N',0,0,'Copy to editable','de.metas.ui.web.order.sales.pricingConditions.process.PricingConditionsView_CopyRowToEditable',100,TO_TIMESTAMP('2018-04-25 16:32:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-25 16:32:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-25T16:32:40.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540954 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-04-25T16:33:17.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','PricingConditionsView_SaveEditableRow','3','D','Y','N','N','N',540955,'N','Y','N','Java','N','N',0,0,'Save','de.metas.ui.web.order.sales.pricingConditions.process.PricingConditionsView_SaveEditableRow',100,TO_TIMESTAMP('2018-04-25 16:33:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-25 16:33:17','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-25T16:33:17.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540955 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

