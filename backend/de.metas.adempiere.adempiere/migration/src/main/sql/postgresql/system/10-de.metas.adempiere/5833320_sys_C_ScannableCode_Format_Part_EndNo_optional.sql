-- C_ScannableCode_Format_Part.EndNo becomes optional: an empty End No means
--  - last part of the format: read to the end of the scanned code
--  - any other part: read up to the position before the next part starts

UPDATE AD_Column
SET IsMandatory = 'N',
    Updated     = TO_TIMESTAMP('2026-11-05 18:30:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Column_ID = 589950;

-- AD_Column.IsMandatory and the DB constraint must stay in sync
INSERT INTO t_alter_column values ('c_scannablecode_format_part', 'EndNo', 'NUMERIC(10)', null, null);
INSERT INTO t_alter_column values ('c_scannablecode_format_part', 'EndNo', null, 'NULL', null);
