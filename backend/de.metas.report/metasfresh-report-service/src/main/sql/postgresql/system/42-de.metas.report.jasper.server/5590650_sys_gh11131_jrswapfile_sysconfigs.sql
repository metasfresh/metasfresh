
-- 2021-05-31T16:33:57.676Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541368,'S',TO_TIMESTAMP('2021-05-31 18:33:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.adempiere.adempiereJasper','Y','de.metas.report.jasper.JRSwapFileVirtualizer.active',TO_TIMESTAMP('2021-05-31 18:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-05-31T16:34:37.400Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541369,'S',TO_TIMESTAMP('2021-05-31 18:34:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.adempiere.adempiereJasper','Y','de.metas.report.jasper.JRSwapFileVirtualizer.maxSize',TO_TIMESTAMP('2021-05-31 18:34:37','YYYY-MM-DD HH24:MI:SS'),100,'200')
;

-- 2021-05-31T16:35:17.784Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541370,'S',TO_TIMESTAMP('2021-05-31 18:35:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.adempiere.adempiereJasper','Y','de.metas.report.jasper.JRSwapFile.blockSize',TO_TIMESTAMP('2021-05-31 18:35:17','YYYY-MM-DD HH24:MI:SS'),100,'1024')
;

-- 2021-05-31T16:35:48.166Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541371,'S',TO_TIMESTAMP('2021-05-31 18:35:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.adempiere.adempiereJasper','Y','de.metas.report.jasper.JRSwapFile.minGrowCount',TO_TIMESTAMP('2021-05-31 18:35:48','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2021-05-31T16:36:41.198Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541372,'S',TO_TIMESTAMP('2021-05-31 18:36:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.adempiere.adempiereJasper','Y','de.metas.report.jasper.JRSwapFile.tempDirPrefix',TO_TIMESTAMP('2021-05-31 18:36:41','YYYY-MM-DD HH24:MI:SS'),100,'jasperSwapFile')
;

-- 2021-05-31T16:37:36.332Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='jasperSwapFiles',Updated=TO_TIMESTAMP('2021-05-31 18:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541372
;

-- 2021-05-31T16:47:13.865Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='2000',Updated=TO_TIMESTAMP('2021-05-31 18:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541369
;

-- URL zum Konzept
UPDATE AD_SysConfig SET Value='50',Updated=TO_TIMESTAMP('2021-05-31 19:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541369
;

-- 2021-05-31T17:51:31.282Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='M',Updated=TO_TIMESTAMP('2021-05-31 19:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541369
;

-- 2021-05-31T17:53:12.376Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='Maximum number of report pages that are stored in RAM before the virtualizer is actually used.',Updated=TO_TIMESTAMP('2021-05-31 19:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541369
;
