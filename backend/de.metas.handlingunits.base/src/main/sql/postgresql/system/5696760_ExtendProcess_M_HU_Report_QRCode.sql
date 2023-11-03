-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: IsDirectPrint
-- 2023-07-25T08:02:02.213Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1172,0,584980,542664,20,'IsDirectPrint',TO_TIMESTAMP('2023-07-25 11:02:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Print without dialog','de.metas.handlingunits',0,'The Direct Print checkbox indicates that this report will print without a print dialog box being displayed.','Y','N','Y','N','Y','N','Direct print',10,TO_TIMESTAMP('2023-07-25 11:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-25T08:02:02.219Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542664 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: M_HU_QRCode_PDF_S
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_S.jasper
-- 2023-07-25T08:11:15.138Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585289,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-07-25 11:11:14','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/label/QR_Label_S.jasper',0,'HU QR barcode S','json','N','N','xls','Takes the QR Codes as JSON string param and generates the PDF for them.','JasperReportsJSON',TO_TIMESTAMP('2023-07-25 11:11:14','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_QRCode_PDF_S')
;

-- 2023-07-25T08:11:15.143Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585289 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_HU_QRCode_PDF_S
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_S.jasper
-- 2023-07-25T08:13:10.740Z
UPDATE AD_Process SET Name='HU QR barcode Small',Updated=TO_TIMESTAMP('2023-07-25 11:13:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585289
;

-- Value: M_HU_QRCode_PDF_L
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_L.jasper
-- 2023-07-25T08:13:28.289Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585290,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-07-25 11:13:28','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/label/QR_Label_L.jasper',0,'HU QR barcode Large','json','N','N','xls','Takes the QR Codes as JSON string param and generates the PDF for them.','JasperReportsJSON',TO_TIMESTAMP('2023-07-25 11:13:28','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_QRCode_PDF_L')
;

-- 2023-07-25T08:13:28.291Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585290 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Name: PDF QR  Jasper Reports
-- 2023-07-25T08:15:56.768Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540648,'AD_Process.JasperReport IS NOT NULL AND AD_Process.JasperReport ilike ''%QR_Label%''',TO_TIMESTAMP('2023-07-25 11:15:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PDF QR  Jasper Reports','S',TO_TIMESTAMP('2023-07-25 11:15:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: AD_Process_ID
-- 2023-07-25T08:16:10.182Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,117,0,584980,542665,19,540648,'AD_Process_ID',TO_TIMESTAMP('2023-07-25 11:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','de.metas.handlingunits',0,'das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','N','Y','N','Prozess',20,TO_TIMESTAMP('2023-07-25 11:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-25T08:16:10.184Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542665 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: AD_Process_ID
-- 2023-07-25T08:19:09.471Z
UPDATE AD_Process_Para SET DefaultValue='584977',Updated=TO_TIMESTAMP('2023-07-25 11:19:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542665
;


-- Name: PDF QR  Jasper Reports
-- 2023-07-25T08:21:04.730Z
UPDATE AD_Val_Rule SET Code='AD_Process.IsActive=''Y'' AND AD_Process.JasperReport IS NOT NULL AND AD_Process.JasperReport ilike ''%QR_Label%''',Updated=TO_TIMESTAMP('2023-07-25 11:21:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540648
;

-- Value: M_HU_QRCode_PDF_L
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_L.jasper
-- 2023-07-25T14:20:19.337Z
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2023-07-25 17:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585290
;

-- Value: M_HU_QRCode_PDF_S
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_S.jasper
-- 2023-07-25T14:20:27.555Z
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2023-07-25 17:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585289
;


-- Process: M_HU_QRCode_PDF_S(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:50:47.178Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR Etikett - klein',Updated=TO_TIMESTAMP('2023-07-26 09:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585289
;

-- Value: M_HU_QRCode_PDF_S
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_S.jasper
-- 2023-07-26T06:50:52.029Z
UPDATE AD_Process SET Description='', Help=NULL, Name='QR Etikett - klein',Updated=TO_TIMESTAMP('2023-07-26 09:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585289
;

-- Process: M_HU_QRCode_PDF_S(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:50:52.022Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR Etikett - klein',Updated=TO_TIMESTAMP('2023-07-26 09:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585289
;

-- Process: M_HU_QRCode_PDF_S(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:50:59.658Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='HU QR barcode Small',Updated=TO_TIMESTAMP('2023-07-26 09:50:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585289
;

-- Value: M_HU_QRCode_PDF_L
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/QR_Label_L.jasper
-- 2023-07-26T06:51:31.146Z
UPDATE AD_Process SET Description='', Help=NULL, Name='QR Etikett - groß',Updated=TO_TIMESTAMP('2023-07-26 09:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585290
;

-- Process: M_HU_QRCode_PDF_L(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:51:31.137Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR Etikett - groß',Updated=TO_TIMESTAMP('2023-07-26 09:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585290
;

-- Process: M_HU_QRCode_PDF_L(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:51:36.693Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 09:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585290
;

-- Process: M_HU_QRCode_PDF_L(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-07-26T06:51:41.515Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='QR Etikett - groß',Updated=TO_TIMESTAMP('2023-07-26 09:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585290
;


-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: IsDirectPrint
-- 2023-07-26T07:54:39.072Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2023-07-26 10:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542664
;

-- 2023-07-26T07:58:59.352Z
UPDATE AD_Process_Para_Trl SET Description='Send it directly to the printer', Help='The Direct Print checkbox indicates that this report will print directly.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 10:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542664
;

-- 2023-07-26T07:59:18.965Z
UPDATE AD_Process_Para_Trl SET Description='Senden Sie es direkt an den Drucker', Help='Senden Sie es direkt an den Drucker', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 10:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=542664
;

-- 2023-07-26T07:59:23.155Z
UPDATE AD_Process_Para_Trl SET Description='Senden Sie es direkt an den Drucker', Help='Senden Sie es direkt an den Drucker', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 10:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542664
;

-- 2023-07-26T07:59:29.112Z
UPDATE AD_Process_Para_Trl SET Description='Senden Sie es direkt an den Drucker', Help='Senden Sie es direkt an den Drucker',Updated=TO_TIMESTAMP('2023-07-26 10:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542664
;

