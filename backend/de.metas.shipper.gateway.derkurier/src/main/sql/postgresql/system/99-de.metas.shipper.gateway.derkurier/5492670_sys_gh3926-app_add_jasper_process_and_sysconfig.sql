-- 2018-05-04T17:09:43.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,UpdatedBy,JasperReport,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,AD_Org_ID,Name,Classname,Value,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-05-04 17:09:42','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-05-04 17:09:42','YYYY-MM-DD HH24:MI:SS'),'N','Y','3','S','Y','N',100,'@PREFIX@de/metas/label/der_kurier/report.jasper',540957,'N','Y','N','Java','N','N',0,'Der Kurier','org.compiere.report.ReportStarter','Der Kurier (Jasper)','de.metas.shipper.gateway.derkurier')
;

-- 2018-05-04T17:09:43.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540957 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-05-04T17:13:00.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541206,'S',TO_TIMESTAMP('2018-05-04 17:13:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.derkurier','Y','de.metas.shipper.gateway.derkurier.PackageLabel.AD_Process_ID',TO_TIMESTAMP('2018-05-04 17:13:00','YYYY-MM-DD HH24:MI:SS'),100,'540957')
;

