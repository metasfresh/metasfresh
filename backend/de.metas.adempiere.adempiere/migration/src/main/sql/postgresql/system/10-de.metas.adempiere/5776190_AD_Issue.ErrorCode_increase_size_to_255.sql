-- Run mode: SWING_CLIENT

-- Column: AD_Issue.ErrorCode
-- 2025-11-10T14:21:22.512Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-11-10 14:21:22.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589751
;

-- 2025-11-10T14:21:24.074Z
INSERT INTO t_alter_column values('ad_issue','ErrorCode','VARCHAR(255)',null,null)
;

