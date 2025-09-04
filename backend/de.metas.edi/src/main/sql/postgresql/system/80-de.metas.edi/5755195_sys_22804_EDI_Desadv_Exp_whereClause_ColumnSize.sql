
-- Column: EXP_Format.WhereClause
-- 2025-05-19T12:49:44.468Z
UPDATE AD_Column SET FieldLength=2048,Updated=TO_TIMESTAMP('2025-05-19 12:49:44.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=54500
;

-- 2025-05-19T12:49:47.522Z
INSERT INTO t_alter_column values('exp_format','WhereClause','VARCHAR(2048)',null,null)
;
