-- 2022-02-03T15:48:34.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584977,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2022-02-03 17:48:33','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','Y','Y','Y','N','N','Y','Y','Y','@PREFIX@de/metas/docs/label/HU_LabelJSON.jasper',0,'HU QR barcode (jasper)','json','N','N','JasperReportsJSON',TO_TIMESTAMP('2022-02-03 17:48:33','YYYY-MM-DD HH24:MI:SS'),100,'qr_json_label')
;

-- 2022-02-03T15:48:34.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584977 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-02-03T15:54:16.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541447,'O',TO_TIMESTAMP('2022-02-03 17:54:15','YYYY-MM-DD HH24:MI:SS'),100,'This specifies the process which the system shall use when json labels for HUs. Note: AD_Process_ID=584977 is the process with the value "HU QR barcode (jasper)".','de.metas.handlingunits','Y','de.metas.handlingunits.HUJSONLabel.AD_Process_ID',TO_TIMESTAMP('2022-02-03 17:54:15','YYYY-MM-DD HH24:MI:SS'),100,'584977')
;

