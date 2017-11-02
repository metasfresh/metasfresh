-- 2017-11-02T15:32:55.344
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541168,'S',TO_TIMESTAMP('2017-11-02 15:32:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Async_InitDelayMillis',TO_TIMESTAMP('2017-11-02 15:32:55','YYYY-MM-DD HH24:MI:SS'),100,'60000')
;

-- 2017-11-02T15:34:12.434
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='The time of waiting until the IQueueProcessorExecutorService initializes. It is defined in milliseconds.',Updated=TO_TIMESTAMP('2017-11-02 15:34:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541168
;

