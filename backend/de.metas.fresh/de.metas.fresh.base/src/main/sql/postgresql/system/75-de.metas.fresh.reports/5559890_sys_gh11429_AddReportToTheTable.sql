-- 2020-05-25T07:48:26.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,Classname,CopyFromProcess,UpdatedBy,JasperReport,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,EntityType,Description,AD_Org_ID,IsServerProcess) VALUES (0,'Y',TO_TIMESTAMP('2020-05-25 10:48:26','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-25 10:48:26','YYYY-MM-DD HH24:MI:SS'),'Y','Y','3','N','N','de.metas.report.jasper.client.process.JasperReportStarter','N',100,'@PREFIX@de/metas/label/finishedproduct/report.jasper',584694,'Finishedproduct Label','N','Y','N','N','N',0,'JasperReportsSQL','Y','Fertigprodukt Label','U','Fertigprodukt Label (Jasper)',0,'N')
;

-- 2020-05-25T07:48:26.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584694 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-05-25T07:48:32.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-25 10:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584694
;

-- 2020-05-25T07:48:38.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Finishedproduct Label (Jasper)', IsTranslated='Y', Name='Finishedproduct Label',Updated=TO_TIMESTAMP('2020-05-25 10:48:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584694
;

-- 2020-05-25T07:52:19.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584694,53027,540819,TO_TIMESTAMP('2020-05-25 10:52:18','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',TO_TIMESTAMP('2020-05-25 10:52:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

