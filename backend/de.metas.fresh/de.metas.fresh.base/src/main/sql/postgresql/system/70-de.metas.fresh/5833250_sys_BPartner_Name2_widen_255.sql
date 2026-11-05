-- Widen C_BPartner.Name2 to VARCHAR(255). "Name" is already 255; Name2 (the second-name / company-addition
-- line) at 60 was too short for some legitimate business names. t_alter_column is used because Name2 may be
-- referenced in views (the trigger drops and recreates them). Only-widen / idempotent: both the physical
-- column and the AD_Column width change only when currently narrower than 255, so re-running or an instance
-- that already has it wider is a no-op (never shrinks).
INSERT INTO t_alter_column
SELECT 'c_bpartner','Name2','VARCHAR(255)',null,null
WHERE (SELECT character_maximum_length FROM information_schema.columns
       WHERE table_name='c_bpartner' AND column_name='name2') < 255
;

UPDATE AD_Column SET FieldLength=255, Updated=TO_TIMESTAMP('2026-11-05 13:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
 WHERE AD_Table_ID=291 AND ColumnName='Name2' AND FieldLength < 255
;
