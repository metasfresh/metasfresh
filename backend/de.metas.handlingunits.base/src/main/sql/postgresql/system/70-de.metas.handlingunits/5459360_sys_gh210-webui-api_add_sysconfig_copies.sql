-- 2017-03-31T18:58:35.031
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.handlingunits.ReceiptScheduleHUPOSJasperProcess',Updated=TO_TIMESTAMP('2017-03-31 18:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540575
;

-- 2017-03-31T18:59:12.755
-- URL zum Konzept
UPDATE AD_SysConfig SET Name='de.metas.handlingunits.ReceiptScheduleHUPOSJasper.AD_Process_ID',Updated=TO_TIMESTAMP('2017-03-31 18:59:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540575
;

-- 2017-03-31T19:00:41.562
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541108,'S',TO_TIMESTAMP('2017-03-31 19:00:41','YYYY-MM-DD HH24:MI:SS'),100,'If the process is run without an explicit process parameter to specifie the number of copie, then use this value','de.metas.handlingunits','Y','de.metas.handlingunits.ReceiptScheduleHUPOSJasper.Copies',TO_TIMESTAMP('2017-03-31 19:00:41','YYYY-MM-DD HH24:MI:SS'),100,'2')
;

-- 2017-03-31T19:28:31.340
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='If the process is run without an explicit process parameter to specifie the number of copie, then use this value.',Updated=TO_TIMESTAMP('2017-03-31 19:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541108
;

-- 2017-03-31T19:29:00.068
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='If the process is run, then use this value for the number of copies. 
If this sysconfig value is missing, then got with 1 as default.',Updated=TO_TIMESTAMP('2017-03-31 19:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541108
;

