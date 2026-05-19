-- Tax Declaration Iter4: add VATCode string column to C_TaxDeclarationLine + C_TaxDeclarationAcct
--
-- Fact_Acct.VATCode is the authoritative string carrier of the VAT code (Mark's design).
-- C_VAT_Code_ID is only a "nice to have" link to the master record; aggregation must work
-- by VATCode string so that Fact_Acct entries without a matching C_VAT_Code master record
-- are still aggregated.
--
-- VATCode mirrors the Fact_Acct.VATCode column (AD_Element 542959, VARCHAR(10), String).

-- ====================================================================================
-- Section 1: AD_Column — C_TaxDeclarationLine.VATCode
-- ====================================================================================
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592559 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclarationLine'),
    542959, 'VATCode', 10, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    10, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592559
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ====================================================================================
-- Section 2: AD_Column — C_TaxDeclarationAcct.VATCode
-- ====================================================================================
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592560 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclarationAcct'),
    542959, 'VATCode', 10, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    10, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592560
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ====================================================================================
-- Section 3: Physical DDL — add VATCode column to both tables
-- ====================================================================================
ALTER TABLE C_TaxDeclarationLine ADD COLUMN IF NOT EXISTS VATCode VARCHAR(10);
ALTER TABLE C_TaxDeclarationAcct ADD COLUMN IF NOT EXISTS VATCode VARCHAR(10);

-- ====================================================================================
-- Section 4: AD_Field — VATCode on Lines tab (549257) and Accts tab (549258)
-- Position right after C_VAT_Code_ID for visual proximity.
-- ====================================================================================
-- 4a. C_TaxDeclarationLine.VATCode field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592559, 780255 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 10, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'VAT Code', TIMESTAMP '2026-05-19 00:00:00', 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780255
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 4b. C_TaxDeclarationAcct.VATCode field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592560, 780256 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 10, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'VAT Code', TIMESTAMP '2026-05-19 00:00:00', 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780256
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- ====================================================================================
-- Section 5: AD_UI_Element — insert VATCode right after C_VAT_Code_ID, shift downstream
-- ====================================================================================
-- 5a. Lines tab: current SeqNoGrid = 10(Line) 20(C_VAT_Code_ID) 30(AmountType) 40(Amount) 50(LineCount) 60(C_Currency_ID)
--      Insert VATCode at SeqNoGrid=25 (between C_VAT_Code_ID and AmountType)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780255 /*From ID Server*/, 0, 549257,
    555314, 651702 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'VAT Code', 25, 25, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- 5b. Accts tab: current SeqNoGrid order has C_VAT_Code_ID at 40, AmountType at 50
--      Insert VATCode at SeqNoGrid=45
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780256 /*From ID Server*/, 0, 549258,
    555315, 651703 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'VAT Code', 45, 45, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);
