

-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: N
-- 2023-04-24T13:03:40.817Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541608,'O',TO_TIMESTAMP('2023-04-24 15:03:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.invoice.review.AutoCreateForSalesInvoice',TO_TIMESTAMP('2023-04-24 15:03:40','YYYY-MM-DD HH24:MI:SS'),100,'N')
;


-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: N
-- 2023-04-24T13:04:07.307Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If Y and a sales invoice is completed, then metasfresh automatically creates a ',Updated=TO_TIMESTAMP('2023-04-24 15:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541608
;

-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: N
-- 2023-04-24T13:04:38.317Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If Y and a sales invoice is completed, then metasfresh automatically creates a C_Invoice_Review record for that invoice. This record can then be updated from 3rd-party systems via API.',Updated=TO_TIMESTAMP('2023-04-24 15:04:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541608
;

-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: N
-- 2023-04-24T13:04:57.973Z
UPDATE AD_SysConfig SET AD_Org_ID=0, ConfigurationLevel='O',Updated=TO_TIMESTAMP('2023-04-24 15:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541608
;


-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: Y
-- 2023-04-24T13:05:24.777Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,541609,'O',TO_TIMESTAMP('2023-04-24 15:05:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ab182','Y','de.metas.invoice.review.AutoCreateForSalesInvoice',TO_TIMESTAMP('2023-04-24 15:05:24','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- SysConfig Name: de.metas.invoice.review.AutoCreateForSalesInvoice
-- SysConfig Value: Y
-- 2023-04-24T13:05:41.286Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='This overrides the N value from https://abeamuat.metasfresh.com/window/50006/541608 for ab182',Updated=TO_TIMESTAMP('2023-04-24 15:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541609
;

