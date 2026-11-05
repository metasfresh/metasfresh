-- Widen C_BPartner.Name2 from VARCHAR(60) to VARCHAR(255). "Name" is already 255; Name2 (the second
-- name / company-addition line) was too short for some legitimate business names. t_alter_column is used
-- because Name2 may be referenced in views (the trigger drops and recreates them).
INSERT INTO t_alter_column values('c_bpartner','Name2','VARCHAR(255)',null,null)
;

-- keep the AD metadata width in sync with the physical column
UPDATE AD_Column SET FieldLength=255, Updated=TO_TIMESTAMP('2026-11-05 13:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
 WHERE AD_Table_ID=291 AND ColumnName='Name2'
;
