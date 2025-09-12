-- apply the same file again, because in the migration-script 
-- backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/system/70-de.metas.fresh/5718710_sys_gh17549_SkipHouseKeeping_SysConfig.sql
-- the value was set wrongly, so the sysconfig was ineffective
    
-- 2025-09-01T09:12:02.570Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.housekeeping.SkipHouseKeeping.ApiAuditHouseKeepingTask',Updated=TO_TIMESTAMP('2024-09-01 16:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541712
;
