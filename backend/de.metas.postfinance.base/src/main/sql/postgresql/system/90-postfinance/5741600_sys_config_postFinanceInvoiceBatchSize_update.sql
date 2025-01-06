-- Run mode: WEBUI

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 40
-- 2024-12-12T15:34:18.845Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize',Updated=TO_TIMESTAMP('2024-12-12 16:34:18.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 40
-- 2024-12-12T15:34:29.303Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Number of invoices (invoice data + byte code of pdf) send in one batch. Number should be choosen so the total size of data is below 5MB. Size of single invoice can be checked by downloading attached postfinance_upload.xml after sending to postfinance.',Updated=TO_TIMESTAMP('2024-12-12 16:34:29.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 30
-- SysConfig Value (old): 40
-- 2024-12-12T15:34:34.561Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Value='30',Updated=TO_TIMESTAMP('2024-12-12 16:34:34.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 30
-- 2024-12-12T15:35:07.972Z
UPDATE AD_SysConfig SET AD_Org_ID=0, ConfigurationLevel='O',Updated=TO_TIMESTAMP('2024-12-12 16:35:07.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 30
-- 2024-12-12T15:35:10.937Z
UPDATE AD_SysConfig SET AD_Client_ID=0,Updated=TO_TIMESTAMP('2024-12-12 16:35:10.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

-- SysConfig Name: de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize
-- SysConfig Value: 30
-- 2024-12-12T15:35:18.394Z
UPDATE AD_SysConfig SET ConfigurationLevel='S',Updated=TO_TIMESTAMP('2024-12-12 16:35:18.394','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541748
;

