

-- 2022-07-12T15:04:38.820173400Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541466,'O',TO_TIMESTAMP('2022-07-12 18:04:38','YYYY-MM-DD HH24:MI:SS'),100,'When ''Y'', the invoices will be generated for the latest bill to location and user of the invoice partner. When ''N'', the initial location and user from the invoice will be used','D','Y','INVOICE_SENT_TO_CURRENT_BILLTO_ADDRESS_AND_USER',TO_TIMESTAMP('2022-07-12 18:04:38','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2022-07-12T15:08:25.190393200Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O', Name='C_Invoice_Send_To_Current_BillTo_Address_And_User',Updated=TO_TIMESTAMP('2022-07-12 18:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541466
;

