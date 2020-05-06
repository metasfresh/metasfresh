-- 2019-02-06T11:59:03.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541044,'Y','N',TO_TIMESTAMP('2019-02-06 11:59:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','N','N','N','N','N','Y',0,'Create labels','N','Y','Java',TO_TIMESTAMP('2019-02-06 11:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Create labels')
;

-- 2019-02-06T11:59:03.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541044 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-02-06T11:59:29.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541044,540972,540672,TO_TIMESTAMP('2019-02-06 11:59:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y',TO_TIMESTAMP('2019-02-06 11:59:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-02-06T12:13:31.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', IsReport='Y', JasperReport='@PREFIX@de/metas/reports/addresslabel/report.jasper', Type='JasperReports',Updated=TO_TIMESTAMP('2019-02-06 12:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541044
;

-- 2019-02-06T12:17:15.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-06 12:17:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Etiketten erstellen' WHERE AD_Process_ID=541044 AND AD_Language='de_CH'
;

-- 2019-02-06T12:17:20.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-06 12:17:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541044 AND AD_Language='en_US'
;

-- 2019-02-06T12:17:27.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-06 12:17:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Etiketten erstellen' WHERE AD_Process_ID=541044 AND AD_Language='nl_NL'
;

-- 2019-02-06T12:17:39.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Etiketten erstellen',Updated=TO_TIMESTAMP('2019-02-06 12:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541044
;



-- 2019-02-06T17:10:46.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540672
;

-- 2019-02-06T17:11:22.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541044,540970,540673,TO_TIMESTAMP('2019-02-06 17:11:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y',TO_TIMESTAMP('2019-02-06 17:11:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;



