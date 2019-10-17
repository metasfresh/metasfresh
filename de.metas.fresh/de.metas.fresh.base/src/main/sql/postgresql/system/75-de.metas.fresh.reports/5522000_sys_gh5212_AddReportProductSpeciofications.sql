-- 2019-05-20T14:43:38.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541134,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-05-20 14:43:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','N','Y','N','Y','Y','@PREFIX@de/metas/reports/productspecifications/report.jasper',0,'Abfrage für Spezifikation(Jasper)','N','N','JasperReports',TO_TIMESTAMP('2019-05-20 14:43:38','YYYY-MM-DD HH24:MI:SS'),100,'ProductSpecifications')
;

-- 2019-05-20T14:43:38.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541134 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-05-20T14:46:33.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541134,208,540715,TO_TIMESTAMP('2019-05-20 14:46:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-05-20 14:46:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

