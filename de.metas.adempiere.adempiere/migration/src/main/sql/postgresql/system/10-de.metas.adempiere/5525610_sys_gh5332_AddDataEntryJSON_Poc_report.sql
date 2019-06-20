-- 2019-06-20T15:35:45.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541154,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-06-20 15:35:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','N','Y','Y',0,'Products Data Entry (Jasper)','N','N','JasperReportsSQL',TO_TIMESTAMP('2019-06-20 15:35:45','YYYY-MM-DD HH24:MI:SS'),100,'ProductsDataEntry(Jasper)')
;

-- 2019-06-20T15:35:45.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541154 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-06-20T15:36:07.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 15:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;


-- 2019-06-20T15:44:25.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2019-06-20 15:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;

-- 2019-06-20T15:44:37.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 15:44:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;



-- 2019-06-20T15:49:55.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReports with SQL',Updated=TO_TIMESTAMP('2019-06-20 15:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;



-- 2019-06-20T15:56:19.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2019-06-20 15:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;




-- 2019-06-20T16:06:19.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', JSONPath='/dataentry/byId/540573/@M_Product_ID/-1@', Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 16:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;

-- 2019-06-20T16:29:09.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/dataentry/report_cu.jasper',Updated=TO_TIMESTAMP('2019-06-20 16:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;

