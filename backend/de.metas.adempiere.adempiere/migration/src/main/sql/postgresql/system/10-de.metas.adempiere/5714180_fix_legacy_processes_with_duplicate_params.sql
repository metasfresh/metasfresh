-- Run mode: SWING_CLIENT

-- Process: AD_Migration_MergeToFrom(org.adempiere.ad.migration.process.MigrationMergeToFrom)
-- ParameterName: DeleteOld
-- 2023-12-20T19:26:17.553Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2023-12-20 19:26:17.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540366
;

-- Process: AD_Migration_MergeToFrom(org.adempiere.ad.migration.process.MigrationMergeToFrom)
-- ParameterName: DeleteOld
-- 2023-12-20T19:26:21.279Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540367
;

-- 2023-12-20T19:26:21.288Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540367
;

-- Process: PP_Mutiple Components Change(org.eevolution.process.ComponentChange)
-- ParameterName: M_Product_ID
-- 2023-12-20T19:28:11.538Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=53242
;

-- 2023-12-20T19:28:11.545Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=53242
;

