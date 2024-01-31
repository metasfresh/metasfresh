
-- 2024-01-31T07:32:16.794Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541695,'S',TO_TIMESTAMP('2024-01-31 08:32:16','YYYY-MM-DD HH24:MI:SS'),100,'If Y (the default for backwards-compatibility), then whenever an invoice is completed, 
it is enqueued to the C_Invoice_CreateExportData workpackage processor, which then might or might not export the invoice. 
(current case, see package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.InvoiceExportClientFactoryImpl).
Clients/Orgs which do a lot of invoicing might want do set this to N.','U','Y','de.metas.invoice.export.C_Invoice_CreateExportData.Enqueue',TO_TIMESTAMP('2024-01-31 08:32:16','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2024-01-31T07:32:20.170Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2024-01-31 08:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541695
;

-- 2024-01-31T07:32:27.989Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O', EntityType='de.metas.invoice',Updated=TO_TIMESTAMP('2024-01-31 08:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541695
;
