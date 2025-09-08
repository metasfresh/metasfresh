
-- 2025-02-11T16:47:44.779Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541753,'O',TO_TIMESTAMP('2025-02-11 17:47:44','YYYY-MM-DD HH24:MI:SS'),100,'If Y and a sales-rep is selected in a C_Order or C_Invoice, then that sales-rep is also set in the respective bill-partner''s C_BPartner-record.
This can be a problem if many delivery partners share the same bill-partner, but were recruited by different sales reps.','de.metas.contracts.commission','Y','de.metas.contracts.commission.updateSalesPartnerInCustomerMaterdata',TO_TIMESTAMP('2025-02-11 17:47:44','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2025-02-11T16:47:54.462Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.contracts.commission.UpdateSalesPartnerInCustomerMaterdata',Updated=TO_TIMESTAMP('2025-02-11 17:47:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541753
;
