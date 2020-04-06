-- 2018-09-06T10:20:58.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540999,'N','de.metas.dunning.process.ConcatenateDunningAndInvoicesPDFs','N',TO_TIMESTAMP('2018-09-06 10:20:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','N','N','N','N','N','N','Y',0,'Mahnbeleg mit Rechnungsbelegen','N','Y','Java',TO_TIMESTAMP('2018-09-06 10:20:58','YYYY-MM-DD HH24:MI:SS'),100,'ConcatenateDunningAndInvoicesPDFs')
;

-- 2018-09-06T10:20:58.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540999 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-09-06T10:21:09.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-06 10:21:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540999 AND AD_Language='de_CH'
;

-- 2018-09-06T10:21:20.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-06 10:21:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Concatenate Dunning And Invoices PDFs' WHERE AD_Process_ID=540999 AND AD_Language='en_US'
;

-- 2018-09-06T10:21:27.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-06 10:21:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540999 AND AD_Language='nl_NL'
;

-- 2018-09-06T10:21:40.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540999,540401,TO_TIMESTAMP('2018-09-06 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y',TO_TIMESTAMP('2018-09-06 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-09-06T10:21:45.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2018-09-06 10:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540999 AND AD_Table_ID=540401
;

