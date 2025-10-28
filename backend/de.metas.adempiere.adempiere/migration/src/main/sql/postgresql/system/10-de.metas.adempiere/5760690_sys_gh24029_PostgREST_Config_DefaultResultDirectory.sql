-- Run mode: SWING_CLIENT

-- Column: S_PostgREST_Config.PostgREST_ResultDirectory
-- 2025-07-10T16:05:19.558Z
UPDATE AD_Column SET DefaultValue='/home/metasfresh/postgREST_share',Updated=TO_TIMESTAMP('2025-07-10 16:05:19.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589982
;

-- 2025-07-10T16:05:21.685Z
INSERT INTO t_alter_column values('s_postgrest_config','PostgREST_ResultDirectory','VARCHAR(1024)',null,'/home/metasfresh/postgREST_share')
;

-- 2025-07-10T16:05:21.724Z
UPDATE S_PostgREST_Config SET PostgREST_ResultDirectory='/home/metasfresh/postgREST_share' WHERE PostgREST_ResultDirectory IS NULL
;

