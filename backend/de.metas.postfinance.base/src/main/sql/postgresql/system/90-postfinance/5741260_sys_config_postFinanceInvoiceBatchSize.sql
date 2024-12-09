-- Run mode: WEBUI

-- SysConfig Name: de.metas.postfinance.document.export.PostFinanceYbInvoiceService.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 40
-- 2024-12-06T11:40:20.001Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541748,'O',TO_TIMESTAMP('2024-12-06 12:40:19.983','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','de.metas.postfinance.document.export.PostFinanceYbInvoiceService.PostFinanceUploadInvoiceBatchSize',TO_TIMESTAMP('2024-12-06 12:40:19.983','YYYY-MM-DD HH24:MI:SS.US'),100,'20')
;

-- SysConfig Name: de.metas.postfinance.document.export.PostFinanceYbInvoiceService.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 40
-- 2024-12-06T11:41:10.301Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', EntityType='de.metas.postfinance',Updated=TO_TIMESTAMP('2024-12-06 12:41:10.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.PostFinanceYbInvoiceService.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 40
-- 2024-12-09T09:32:26.835Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Number auf invoices (invoice data + byte code of pdf) send in one batch. Number should be chosen so the total size of data is below 5MB. Size of single invoice can be checked by downloading attached postfinance_upload.xml after sending to PostFinance.',Updated=TO_TIMESTAMP('2024-12-09 10:32:26.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

