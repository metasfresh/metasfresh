

-- SysConfig Name: org.compiere.model.MInvoice.Apply5CentRounding
-- SysConfig Value: N
-- 2024-08-26T08:43:02.367Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541731,'O',TO_TIMESTAMP('2024-08-26 11:43:02.099','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoice','Y','org.compiere.model.MInvoice.Apply5CentRounding',TO_TIMESTAMP('2024-08-26 11:43:02.099','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;



-- SysConfig Name: org.compiere.model.MInvoice.Apply5CentRounding
-- SysConfig Value: N
-- 2024-08-26T08:48:50.033Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='When ''Y'', the sales invoice grand total will be rounded to nearest 5 cents (Rappen).
',Updated=TO_TIMESTAMP('2024-08-26 11:48:50.032','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541731
;

