-- 2018-10-26T09:13:35.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReports',Updated=TO_TIMESTAMP('2018-10-26 09:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540944
;

-- 2018-10-26T09:24:07.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReports',Updated=TO_TIMESTAMP('2018-10-26 09:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=500009
;

-- 2018-10-26T09:24:17.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2018-10-26 09:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=500009
;

-- 2018-10-26T09:29:48.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Description,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,TechnicalNote,Classname,Type) VALUES (0,'Y',TO_TIMESTAMP('2018-10-26 09:29:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-26 09:29:48','YYYY-MM-DD HH24:MI:SS'),'Y','N','3','Y','N','N','N',100,541030,'C_Invoice_JasperWithAttachedDocuments','Y','Y','N','Der Prozess erstellt ein Rechnungs-PDF, dass außerdem alle PDF-Anhänge der Rechnung enthält, die mit "Concatename_Pdf_to_InvoicePdf=true" getaggt sind.','N','N',0,0,'Rechnungsdruckstück mit ggf. angehängten PDF-Belegen','de.metas.invoice','This process first runs the Report (Jasper) process that is specified at SysConfig "de.metas.invoice.C_Invoice.SalesInvoice.Report.AD_Process_ID"
and then appends all PDF attachments that is tagged with "Concatename_Pdf_to_InvoicePdf=true"','de.metas.invoice.process.C_Invoice_JasperWithAttachedDocuments','Java')
;

-- 2018-10-26T09:29:48.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541030 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-10-26T09:30:07.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 09:30:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=541030 AND AD_Language='de_CH'
;

-- 2018-10-26T09:38:05.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 09:38:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice-PDF with possible attached additional PDFs',Description='The process creates an invoice-PDF, then appends the PDF-data of attachments that are tagged with "Concatename_Pdf_to_InvoicePdf=true".' WHERE AD_Process_ID=541030 AND AD_Language='en_US'
;

-- 2018-10-26T09:38:24.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType) VALUES (0,TO_TIMESTAMP('2018-10-26 09:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','O',TO_TIMESTAMP('2018-10-26 09:38:24','YYYY-MM-DD HH24:MI:SS'),100,541248,'500009','AD_Process_ID of the sale invoice jasper process.
This config is used by the process C_Invoice_JasperWithAttachedDocuments',0,'de.metas.invoice.C_Invoice.SalesInvoice.Report.AD_Process_ID','de.metas.invoice')
;

-- 2018-10-26T09:39:09.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Der Prozess erstellt ein Rechnungs-PDF, dass außerdem alle PDF-Anhänge der Rechnung enthält, die mit "Concatenate_Pdf_to_InvoicePdf=true" getaggt sind.', TechnicalNote='This process first runs the Report (Jasper) process that is specified at SysConfig "de.metas.invoice.C_Invoice.SalesInvoice.Report.AD_Process_ID"
and then appends all PDF attachments that is tagged with "Concatenate_Pdf_to_InvoicePdf=true"',Updated=TO_TIMESTAMP('2018-10-26 09:39:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541030
;

-- 2018-10-26T09:39:13.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 09:39:13','YYYY-MM-DD HH24:MI:SS'),Description='The process creates an invoice-PDF, then appends the PDF-data of attachments that are tagged with "Concatenate_Pdf_to_InvoicePdf=true".' WHERE AD_Process_ID=541030 AND AD_Language='en_US'
;

-- 2018-10-26T09:39:18.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-26 09:39:18','YYYY-MM-DD HH24:MI:SS'),Description='Der Prozess erstellt ein Rechnungs-PDF, dass außerdem alle PDF-Anhänge der Rechnung enthält, die mit "Concatenate_Pdf_to_InvoicePdf=true" getaggt sind.' WHERE AD_Process_ID=541030 AND AD_Language='de_CH'
;

-- 2018-10-26T09:40:39.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_Invoice_SalesInvoiceJasperWithAttachedDocuments',Updated=TO_TIMESTAMP('2018-10-26 09:40:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541030
;

-- 2018-10-26T09:40:44.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.invoice.process.C_Invoice_SalesInvoiceJasperWithAttachedDocuments',Updated=TO_TIMESTAMP('2018-10-26 09:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541030
;

-- 2018-10-26T09:40:51.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='AD_Process_ID of the sale invoice jasper process.
This config is used by the process C_Invoice_SalesInvoiceJasperWithAttachedDocuments.',Updated=TO_TIMESTAMP('2018-10-26 09:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541248
;

UPDATE AD_PrintFormat SET JasperProcess_ID=541030, Updated=TO_TIMESTAMP('2018-10-26 09:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE JasperProcess_ID=500009;
