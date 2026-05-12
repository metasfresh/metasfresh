-- 2026-05-12 Teo Sarca <teo.sarca@metasfresh.com>
-- Task 4: Tax Declaration Iter4 — C_TaxDeclarationAcct schema migration
-- Drop FK on Fact_Acct_ID (keep as plain integer), add 6 new columns + indexes

-- ===== Section 1: Drop FK constraint on Fact_Acct_ID =====
-- Fact_Acct_ID column is kept as a plain integer (no FK) to allow reposting
-- without being blocked by referential integrity when fact acct records are deleted
ALTER TABLE C_TaxDeclarationAcct DROP CONSTRAINT IF EXISTS factacct_ctaxdeclarationacct;

-- ===== Section 2: Add new AD_Column records (6 columns) =====
-- All elements already exist in the system; reusing their AD_Element_IDs

-- C_VAT_Code_ID (592514)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592514, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 542958, 'C_VAT_Code_ID', 10,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    19, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592514
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- AmountType (592515)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592515, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 1602, 'AmountType', 1,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    10, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592515
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- AD_Table_ID (592516)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592516, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 126, 'AD_Table_ID', 10,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    19, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592516
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Record_ID (592517)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592517, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 538, 'Record_ID', 10,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    11, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592517
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Line_ID (592518)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592518, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 1738, 'Line_ID', 10,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    11, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592518
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Amount (592519)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592519, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    820, 1367, 'Amount', 0,
    'N', 'N', 'N', 'N', 'N', 0, 'N', 'N', 'N',
    12, 'N', 'N', 'Y', 'D', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592519
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ===== Section 3: Physical DDL =====
-- Add 6 new columns (table was wiped in 5801960)
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS C_VAT_Code_ID NUMERIC(10) REFERENCES C_VAT_Code;
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS AmountType CHAR(1);
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS AD_Table_ID NUMERIC(10) REFERENCES AD_Table;
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS Record_ID NUMERIC(10) DEFAULT 0;
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS Line_ID NUMERIC(10);
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS Amount NUMERIC;

-- Indexes
CREATE INDEX IF NOT EXISTS C_TaxDeclarationAcct_FactAcct_idx
    ON C_TaxDeclarationAcct(Fact_Acct_ID);

CREATE INDEX IF NOT EXISTS C_TaxDeclarationAcct_decl_vatcode_idx
    ON C_TaxDeclarationAcct(C_TaxDeclaration_ID, C_VAT_Code_ID, AmountType);
