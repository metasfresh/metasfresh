-- 2023-03-13T11:07:40.137Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541601,'O',TO_TIMESTAMP('2023-03-13 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PreventReceiptOnMissingDeliveryInstructions',TO_TIMESTAMP('2023-03-13 13:07:39','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2023-03-13T11:11:45.901Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='PreventReceiptIfMissingDeliveryInstructions',Updated=TO_TIMESTAMP('2023-03-13 13:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541601
;

-- 2023-03-13T11:32:30.861Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='de.metas.deliveryplanning.webui.process.PreventReceiptIfMissingDeliveryInstructions',Updated=TO_TIMESTAMP('2023-03-13 13:32:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541601
;

