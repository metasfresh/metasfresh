-- 2017-11-07T18:58:08.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,IsReport,IsDirectPrint,AccessLevel,IsBetaFunctionality,IsServerProcess,ShowHelp,Updated,UpdatedBy,Value,JasperReport,AD_Process_ID,IsUseBPartnerLanguage,IsApplySecuritySettings,AllowProcessReRun,Type,RefreshAllAfterExecution,IsOneInstanceOnly,Classname,EntityType,AD_Org_ID,Name) VALUES (0,'Y',TO_TIMESTAMP('2017-11-07 18:58:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','3','N','N','S',TO_TIMESTAMP('2017-11-07 18:58:08','YYYY-MM-DD HH24:MI:SS'),100,'Sales Invoice Extended Org (Jasper)','@PREFIX@de/metas/docs/sales/invoice_org_data_right/report.jasper',540882,'Y','N','N','Java','N','N','org.compiere.report.ReportStarter','U',0,'Sales Invoice Extended Org')
;

-- 2017-11-07T18:58:08.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540882 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

