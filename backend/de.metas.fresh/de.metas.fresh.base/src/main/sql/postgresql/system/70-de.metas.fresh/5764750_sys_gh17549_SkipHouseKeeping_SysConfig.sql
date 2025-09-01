-- apply the same file again, because in the migration-script 
-- backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/system/70-de.metas.fresh/5718710_sys_gh17549_SkipHouseKeeping_SysConfig.sql
-- the value was set wrongly, so the sysconfig was ineffective
    
-- 2024-03-07T09:12:02.570Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541712,'S',TO_TIMESTAMP('2024-03-07 10:12:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.housekeeping.SkipHouseKeeping.ApiAuditHouseKeepingTask',TO_TIMESTAMP('2024-03-07 10:12:02','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

