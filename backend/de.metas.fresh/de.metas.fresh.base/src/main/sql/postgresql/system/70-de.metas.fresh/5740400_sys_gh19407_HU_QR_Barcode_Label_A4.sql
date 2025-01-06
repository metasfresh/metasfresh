-- Value: M_HU_QRCode_PDF_L_A4
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_L_A4.jasper
-- 2024-11-22T10:54:38.760Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585436,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2024-11-22 11:54:38','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/label/QR_Label_L_A4.jasper',0,'HU QR-Barcode-Etikett groß A4','json','N','N','xls','Takes the QR Codes as JSON string param and generates the PDF for them.','JasperReportsSQL',TO_TIMESTAMP('2024-11-22 11:54:38','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_QRCode_PDF_L_A4')
;

-- 2024-11-22T10:54:39.140Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585436 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_HU_QRCode_PDF_L_A4(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-11-22T10:55:51.995Z
UPDATE AD_Process_Trl SET Name='HU QR Barcode Label Large A4',Updated=TO_TIMESTAMP('2024-11-22 11:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585436
;

