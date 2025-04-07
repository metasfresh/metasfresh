-- Run mode: SWING_CLIENT

-- Column: AD_Record_Warning.Record_ID
-- 2025-02-14T17:05:01.801Z
UPDATE AD_Column SET AD_Reference_ID=28, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-02-14 17:05:01.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589491
;

-- Column: AD_Record_Warning.MsgText
-- 2025-02-14T17:09:56.374Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-14 17:09:56.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589492
;

-- 2025-02-14T17:09:59.638Z
INSERT INTO t_alter_column values('ad_record_warning','MsgText','TEXT',null,null)
;

-- 2025-02-14T17:09:59.642Z
INSERT INTO t_alter_column values('ad_record_warning','MsgText',null,'NOT NULL',null)
;

