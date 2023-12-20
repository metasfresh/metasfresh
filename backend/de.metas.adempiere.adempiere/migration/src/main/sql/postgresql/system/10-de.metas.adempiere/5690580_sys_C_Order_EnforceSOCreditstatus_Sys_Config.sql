-- SysConfig Name: de.metas.bpartner.service.impl.BPartnerStatsService.C_Order_EnforceSOCreditStatus
-- SysConfig Value: Y
-- 2023-06-12T14:13:52.768Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541632,'S',TO_TIMESTAMP('2023-06-12 17:13:52','YYYY-MM-DD HH24:MI:SS'),100,'When ''Y'', the sales order, shipment, sales invoice and outgoing payment won''t be completed if the credit limit was exceeded. When ''N'', there will be a user notification about the exceeded credit limit on sales order completion, but the documents will all be completed without error.','D','Y','de.metas.bpartner.service.impl.BPartnerStatsService.C_Order_EnforceSOCreditStatus',TO_TIMESTAMP('2023-06-12 17:13:52','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;



-- SysConfig Name: de.metas.bpartner.service.impl.BPartnerStatsService.C_Order_EnforceSOCreditStatus
-- SysConfig Value: Y
-- 2023-06-12T15:31:45.234Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2023-06-12 18:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541632
;

