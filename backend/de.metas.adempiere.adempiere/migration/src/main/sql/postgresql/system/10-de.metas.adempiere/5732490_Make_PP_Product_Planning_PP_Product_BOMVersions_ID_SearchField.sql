--
-- Script: /tmp/webui_migration_scripts_2024-08-26_8376368427608254311/5732490_migration_2024-09-05_postgresql.sql
-- User: metasfresh
-- OS user: root
--



-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- 2024-09-05T08:18:23.451Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-09-05 10:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;

-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- Column: PP_Product_Planning.PP_Product_BOMVersions_ID
-- 2024-09-05T08:19:11.258Z
UPDATE AD_Column SET AD_Reference_Value_ID=541584, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-09-05 10:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;
