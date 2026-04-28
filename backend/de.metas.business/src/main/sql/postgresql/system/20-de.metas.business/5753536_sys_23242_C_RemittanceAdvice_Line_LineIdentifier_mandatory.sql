-- Run mode: SWING_CLIENT

-- Table: C_RemittanceAdvice_Line
-- 2025-05-05T14:58:19.528Z
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2025-05-05 14:58:19.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=541574
;

-- Column: C_RemittanceAdvice_Line.LineIdentifier
-- 2025-05-05T14:58:23.638Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-05-05 14:58:23.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589928
;

-- 2025-05-05T14:58:24.792Z
INSERT INTO t_alter_column values('c_remittanceadvice_line','LineIdentifier','VARCHAR(255)',null,null)
;

-- 2025-05-05T14:58:24.795Z
INSERT INTO t_alter_column values('c_remittanceadvice_line','LineIdentifier',null,'NOT NULL',null)
;

