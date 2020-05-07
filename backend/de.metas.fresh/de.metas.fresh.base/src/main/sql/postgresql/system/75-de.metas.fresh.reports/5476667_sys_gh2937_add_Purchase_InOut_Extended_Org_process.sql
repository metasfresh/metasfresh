-- 2017-11-08T16:32:43.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,IsReport,IsDirectPrint,AccessLevel,IsBetaFunctionality,IsServerProcess,ShowHelp,Updated,UpdatedBy,Value,JasperReport,AD_Process_ID,IsUseBPartnerLanguage,IsApplySecuritySettings,AllowProcessReRun,Type,RefreshAllAfterExecution,IsOneInstanceOnly,Classname,EntityType,AD_Org_ID,Name) VALUES (0,'Y',TO_TIMESTAMP('2017-11-08 16:32:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','3','N','N','S',TO_TIMESTAMP('2017-11-08 16:32:43','YYYY-MM-DD HH24:MI:SS'),100,'Purchase InOut Extended Org (Jasper)','@PREFIX@de/metas/docs/purchase/inout_org_data_right/report.jasper',540886,'Y','N','N','Java','N','N','org.compiere.report.ReportStarter','de.metas.fresh',0,'Purchase InOut Extended Org')
;

-- 2017-11-08T16:32:43.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540886 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

