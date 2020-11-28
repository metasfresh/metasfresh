
-- 18.11.2015 07:16
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540914,'S',TO_TIMESTAMP('2015-11-18 07:16:26','YYYY-MM-DD HH24:MI:SS'),100,'Specifies what to do if the process encounters a sales order line without a "vendor-bpartner-product" record.','de.metas.order','Y','de.metas.order.C_Order_CreatePOFromSOs.OnMissing_C_BPartner_Product',TO_TIMESTAMP('2015-11-18 07:16:26','YYYY-MM-DD HH24:MI:SS'),100,'LOG')
;

-- 18.11.2015 07:17
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Specifies what to do if the process encounters a sales order line without a "vendor-bpartner-product" record.
Possible values (upper/lower case is ignored): 
Log=>log them to AD_PinstanceLog; Error=>like "Log", but fail and rollback; Ignore=>silently skip',Updated=TO_TIMESTAMP('2015-11-18 07:17:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540914
;

