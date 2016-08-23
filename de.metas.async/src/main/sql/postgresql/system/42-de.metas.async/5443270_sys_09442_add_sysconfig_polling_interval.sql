
-- 23.03.2016 23:01
-- URL zum Konzept
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=540986;
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,0,540986,'C',TO_TIMESTAMP('2016-03-23 23:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Interval in which each single workpackage queue processor polls for new work packages.','de.metas.async','Y','de.metas.async.PollIntervallMillis',TO_TIMESTAMP('2016-03-23 23:01:28','YYYY-MM-DD HH24:MI:SS'),100,'5000')
;

-- 23.03.2016 23:01
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
SELECT 0,0,540987,'C',TO_TIMESTAMP('2016-03-23 23:01:47','YYYY-MM-DD HH24:MI:SS'),100,'Interval in which each single workpackage queue processor polls for new work packages.','de.metas.async','Y','de.metas.async.PollIntervallMillis',TO_TIMESTAMP('2016-03-23 23:01:47','YYYY-MM-DD HH24:MI:SS'),100,'5000'
WHERE not exists (select 1 from aD_sysconfig where ad_sysconfig_id=540987)
;

-- 23.03.2016 23:01
-- URL zum Konzept
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=540986
;

