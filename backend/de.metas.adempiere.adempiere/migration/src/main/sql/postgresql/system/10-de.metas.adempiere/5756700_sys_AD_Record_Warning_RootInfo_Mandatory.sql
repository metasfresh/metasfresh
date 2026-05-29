-- Column: AD_Record_Warning.Root_AD_Table_ID
-- 2025-06-03T14:59:41.883Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-03 14:59:41.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590150
;

-- 2025-06-03T14:59:42.794Z
INSERT INTO t_alter_column values('ad_record_warning','Root_AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2025-06-03T14:59:42.798Z
INSERT INTO t_alter_column values('ad_record_warning','Root_AD_Table_ID',null,'NOT NULL',null)
;

-- Column: AD_Record_Warning.Root_Record_ID
-- 2025-06-03T14:59:49.416Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-03 14:59:49.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590151
;

-- 2025-06-03T14:59:52.277Z
INSERT INTO t_alter_column values('ad_record_warning','Root_Record_ID','NUMERIC(10)',null,null)
;

-- 2025-06-03T14:59:52.285Z
INSERT INTO t_alter_column values('ad_record_warning','Root_Record_ID',null,'NOT NULL',null)
;

