-- 2026-05-12 Tax Declaration Iter4 — C_TaxDeclaration schema
-- Remove Name column, add C_AcctSchema_ID (mandatory), add period-uniqueness index

-- 1. Remove Name column from AD_Column
DELETE FROM AD_Column
WHERE ColumnName = 'Name'
  AND AD_Table_ID = 818;

-- 2. Add C_AcctSchema_ID to AD_Column
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592509, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    818, 181, 'C_AcctSchema_ID', 10, 'N', 'N', 'Y',
    'N', 'N', 0, 'N', 'N', 'N',
    19, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592509
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- 3. Physical DDL: drop Name, add C_AcctSchema_ID
-- Drop report.fresh_TaxDeclaration_Overview first — it references td.name (see 5802010 which is now a no-op)
DROP VIEW IF EXISTS report.fresh_TaxDeclaration_Overview;
ALTER TABLE C_TaxDeclaration DROP COLUMN IF EXISTS Name;
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS C_AcctSchema_ID NUMERIC(10) REFERENCES C_AcctSchema;
-- Table was wiped in migration 5801960; no existing rows to backfill
ALTER TABLE C_TaxDeclaration ALTER COLUMN C_AcctSchema_ID SET NOT NULL;

-- 4. Period-uniqueness partial index
CREATE UNIQUE INDEX IF NOT EXISTS C_TaxDeclaration_period_unique
    ON C_TaxDeclaration(C_AcctSchema_ID, DateFrom, DateTo)
    WHERE Processed='Y' AND IsActive='Y';
