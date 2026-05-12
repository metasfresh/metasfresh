-- Tax Declaration Iter4: C_TaxDeclarationLine schema migration
-- Drop 13 legacy columns, add 4 new columns (C_VAT_Code_ID, AmountType, Amount, LineCount), add unique index

-- ============================================================================
-- Section 0: Create missing AD_Element for LineCount
-- ============================================================================
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, PrintName, Name
)
VALUES (
    584856 /*From ID Server*/, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    'LineCount', 'D', 'Line Count', 'Line Count'
) ON CONFLICT (AD_Element_ID) DO NOTHING;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584856
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- ============================================================================
-- Section 1: Remove legacy AD_Field records (required before AD_Column deletion)
-- ============================================================================
DELETE FROM AD_Field
WHERE AD_Column_ID IN (
    SELECT AD_Column_ID FROM AD_Column
    WHERE AD_Table_ID = 819
      AND ColumnName IN ('C_Invoice_ID','C_InvoiceLine_ID','GL_JournalLine_ID','C_AllocationLine_ID',
                         'DocumentNo','C_DocType_ID','IsSOTrx','C_BPartner_ID','IsManual',
                         'DateAcct','TaxBaseAmt','TaxAmt','C_Tax_ID')
);

-- ============================================================================
-- Section 2: Remove legacy AD_Column records
-- ============================================================================
DELETE FROM AD_Column
WHERE AD_Table_ID = 819
  AND ColumnName IN ('C_Invoice_ID','C_InvoiceLine_ID','GL_JournalLine_ID','C_AllocationLine_ID',
                     'DocumentNo','C_DocType_ID','IsSOTrx','C_BPartner_ID','IsManual',
                     'DateAcct','TaxBaseAmt','TaxAmt','C_Tax_ID');

-- ============================================================================
-- Section 3: Add new AD_Column records (4 columns)
-- ============================================================================

-- Column 1: C_VAT_Code_ID (uses existing AD_Element_ID 542958)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592510, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    819, 542958, 'C_VAT_Code_ID', 0,
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
  AND c.AD_Column_ID = 592510
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Column 2: AmountType (uses existing AD_Element_ID 1602)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592511, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    819, 1602, 'AmountType', 1,
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
  AND c.AD_Column_ID = 592511
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Column 3: Amount (uses existing AD_Element_ID 1367)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592512, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    819, 1367, 'Amount', 0,
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
  AND c.AD_Column_ID = 592512
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- Column 4: LineCount (uses new AD_Element_ID 584856 from ID server)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592513, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    819, 584856, 'LineCount', 0,
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
  AND c.AD_Column_ID = 592513
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ============================================================================
-- Section 4: Physical DDL
-- ============================================================================

-- Drop 13 legacy columns
-- Drop report.RV_TaxDeclarationLine first — it references these columns (see 5802000 which is now a no-op)
DROP VIEW IF EXISTS report.RV_TaxDeclarationLine;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_Invoice_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_InvoiceLine_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS GL_JournalLine_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_AllocationLine_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS DocumentNo;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_DocType_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS IsSOTrx;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_BPartner_ID;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS IsManual;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS DateAcct;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS TaxBaseAmt;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS TaxAmt;
ALTER TABLE C_TaxDeclarationLine DROP COLUMN IF EXISTS C_Tax_ID;

-- Add 4 new columns (table was wiped in 5801960 — no data to migrate)
ALTER TABLE C_TaxDeclarationLine ADD COLUMN IF NOT EXISTS C_VAT_Code_ID NUMERIC(10) REFERENCES C_VAT_Code;
ALTER TABLE C_TaxDeclarationLine ADD COLUMN IF NOT EXISTS AmountType CHAR(1);
ALTER TABLE C_TaxDeclarationLine ADD COLUMN IF NOT EXISTS Amount NUMERIC;
ALTER TABLE C_TaxDeclarationLine ADD COLUMN IF NOT EXISTS LineCount NUMERIC;

-- Unique aggregation key
CREATE UNIQUE INDEX IF NOT EXISTS C_TaxDeclarationLine_agg_key
    ON C_TaxDeclarationLine(C_TaxDeclaration_ID, C_VAT_Code_ID, AmountType)
    WHERE IsActive = 'Y';
