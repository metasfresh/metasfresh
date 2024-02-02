-- Value: Badge_QRCode_PDF
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- 2024-02-01T19:03:41.661Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585352,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2024-02-01 20:03:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/security/AuthBadge_2cm.jasper',0,'Authentifizierungsausweis','json','N','N','xls','JasperReportsJSON',TO_TIMESTAMP('2024-02-01 20:03:41','YYYY-MM-DD HH24:MI:SS'),100,'AuthBadge_2cm_PDF')
;

-- 2024-02-01T19:03:41.664Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585352 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Badge_QRCode_PDF(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-02-01T19:03:58.507Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Authentication badge',Updated=TO_TIMESTAMP('2024-02-01 20:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585352
;

