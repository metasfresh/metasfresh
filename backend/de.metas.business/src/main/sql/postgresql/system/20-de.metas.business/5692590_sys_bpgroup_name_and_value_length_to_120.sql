-- Column: C_BP_Group.Name
-- 2023-06-21T07:27:02.301393Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2023-06-21 09:27:02.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=4970
;

-- 2023-06-21T07:27:05.267606200Z
INSERT INTO t_alter_column values('c_bp_group','Name','VARCHAR(120)',null,null)
;

-- Column: C_BP_Group.Value
-- 2023-06-21T07:27:29.060075Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2023-06-21 09:27:29.059','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=4969
;

-- 2023-06-21T07:27:31.202834400Z
INSERT INTO t_alter_column values('c_bp_group','Value','VARCHAR(120)',null,null)
;

