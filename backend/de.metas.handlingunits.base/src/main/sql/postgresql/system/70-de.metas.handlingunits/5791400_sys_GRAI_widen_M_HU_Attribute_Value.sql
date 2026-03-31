-- Migration: Widen M_HU_Attribute.Value from VARCHAR(255) to TEXT
-- Purpose: For aggregate HUs (non-materialized TUs), GRAI codes are stored as
--          comma-separated values which can exceed 255 characters.
-- Issue: me03#28474

-- Step 1: Update AD_Column.FieldLength to signal TEXT type
UPDATE AD_Column
SET FieldLength    = 99999999,
    Updated        = '2026-03-03 12:00',
    UpdatedBy      = 100
WHERE AD_Column_ID = 549254  -- M_HU_Attribute.Value
;

-- Step 2: Apply DDL change via t_alter_column (handles view dependencies)
INSERT INTO t_alter_column VALUES ('M_HU_Attribute', 'Value', 'TEXT', null, null)
;
