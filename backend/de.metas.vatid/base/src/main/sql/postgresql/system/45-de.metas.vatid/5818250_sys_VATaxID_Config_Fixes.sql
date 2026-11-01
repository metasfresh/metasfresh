-- VAT-ID online check: fix two review findings on 5818220_sys_VATaxID_Config_Table.sql.
-- That script is already applied to live databases, so both are corrected here rather than
-- edited in place.
--
-- 1) OnServiceUnavailable now points at its own reference list (542126, added in
--    5818240_sys_VATaxID_Config_OnServiceUnavailable_ReferenceList.sql) instead of reusing the
--    full VATaxIDStatus list (542125), and its CHECK constraint is narrowed to match.
-- 2) IsSelectionColumn is made consistent between the two independent check-enable flags: neither
--    belongs in the quick-filter bar of this single-row-per-org config tab (AD_Org_ID and
--    IsActive already cover the structural filter candidates there).

-- 1. Point OnServiceUnavailable at its own reference list
UPDATE AD_Column
SET AD_Reference_Value_ID = 542126,
    Updated = TO_TIMESTAMP('2026-08-11 15:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593146;

-- 2. Replace the physical CHECK constraint to match the narrower value set
SELECT db_alter_table('VATaxID_Config', 'ALTER TABLE VATaxID_Config DROP CONSTRAINT VATaxID_Config_OnServiceUnavailable_check');
SELECT db_alter_table('VATaxID_Config', 'ALTER TABLE VATaxID_Config ADD CONSTRAINT VATaxID_Config_OnServiceUnavailable_check CHECK (OnServiceUnavailable IN (''ServiceUnavailable'', ''Invalid''))');

-- 3. IsSelectionColumn consistency: IsVIESCheckEnabled matches IsFormatCheckEnabled ('N')
UPDATE AD_Column
SET IsSelectionColumn = 'N',
    Updated = TO_TIMESTAMP('2026-08-11 15:10:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593141;
