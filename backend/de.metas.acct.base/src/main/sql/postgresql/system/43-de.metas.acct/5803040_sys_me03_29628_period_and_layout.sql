-- Tax Declaration Iter4+: add C_Period_ID, DateAcct, DocumentNo + 2-column header layout
-- Adds a period picker; hides the DateFrom/DateTo/DateTrx range fields from UI.

-- ==========================================================================================
-- Section 1: AD_Val_Rule — filter C_Period by the AcctSchema's calendar
-- ==========================================================================================
INSERT INTO AD_Val_Rule (
    AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID,
    Code, EntityType, IsActive, Name, Type,
    Created, CreatedBy, Updated, UpdatedBy
)
VALUES (
    0, 0, 540787 /*From ID Server*/,
    'C_Year_ID IN (SELECT y.C_Year_ID FROM C_Year y WHERE y.C_Calendar_ID = (SELECT a.C_Calendar_ID FROM C_AcctSchema a WHERE a.C_AcctSchema_ID = @C_AcctSchema_ID@))',
    'de.metas.acct', 'Y', 'C_Period for TaxDeclaration by AcctSchema', 'S',
    NOW(), 100, NOW(), 100
);

-- ==========================================================================================
-- Section 2: AD_Column — DocumentNo (AD_Element_ID=290, String/30)
-- ==========================================================================================
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592556 /*From ID Server*/, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    818, 290, 'DocumentNo', 30, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'Y',
    10, 'N', 'N', 'Y', 'de.metas.acct', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592556
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ==========================================================================================
-- Section 3: AD_Column — DateAcct (AD_Element_ID=263, Date)
-- ==========================================================================================
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version
)
VALUES (
    592557 /*From ID Server*/, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    818, 263, 'DateAcct', 7, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    15, 'N', 'N', 'Y', 'de.metas.acct', 0
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592557
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ==========================================================================================
-- Section 4: AD_Column — C_Period_ID (AD_Element_ID=206, TableDir, val rule)
-- ==========================================================================================
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    AD_Val_Rule_ID
)
VALUES (
    592558 /*From ID Server*/, 0, 0, 'Y', NOW(), 0, NOW(), 0,
    818, 206, 'C_Period_ID', 10, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'Y',
    19, 'N', 'N', 'Y', 'de.metas.acct', 0,
    540787
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592558
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ==========================================================================================
-- Section 5: Physical DDL — add 3 new columns to C_TaxDeclaration
-- ==========================================================================================
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS DocumentNo VARCHAR(30);
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS DateAcct DATE;
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS C_Period_ID NUMERIC(10) REFERENCES C_Period;

-- ==========================================================================================
-- Section 6: Replace period-uniqueness index with C_Period_ID-based variant
-- ==========================================================================================
DROP INDEX IF EXISTS C_TaxDeclaration_period_unique;
CREATE UNIQUE INDEX IF NOT EXISTS C_TaxDeclaration_acctschema_period_unique
    ON C_TaxDeclaration(C_AcctSchema_ID, C_Period_ID)
    WHERE C_Period_ID IS NOT NULL AND IsActive = 'Y';

-- ==========================================================================================
-- Section 7: AD_Field — 3 new fields in header tab (AD_Tab_ID=549256)
-- ==========================================================================================

-- 7a. DocumentNo
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592556, 780251 /*From ID Server*/, 0, 549256,
    NOW(), 100, 30, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Document No', NOW(), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780251
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 7b. DateAcct
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592557, 780252 /*From ID Server*/, 0, 549256,
    NOW(), 100, 20, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Date Acct', NOW(), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780252
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 7c. C_Period_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592558, 780253 /*From ID Server*/, 0, 549256,
    NOW(), 100, 20, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Period', NOW(), 100);
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780253
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- ==========================================================================================
-- Section 8: Header tab 2-column layout redesign
--
-- Before: 1 column (549485), 1 primary group (555313), all 9 fields
-- After:  Left col (549485): primary group [DocumentNo, C_AcctSchema_ID, Description,
--                                           DateFrom/To/Trx hidden]
--                            dates group  [C_Period_ID, DateAcct]
--         Right col (549512): flags group [IsActive, Processed, Processing]
--                             org group   [AD_Org_ID]
-- ==========================================================================================

-- 8a. Add right column (SeqNo=20) to header section (547771)
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
    Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549512 /*From ID Server*/, 547771,
    NOW(), 100, 'Y', 20, NOW(), 100);

-- 8b. Dates group — left column, below primary group
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555374 /*From ID Server*/, 549485,
    NOW(), 100, 'Y', 'dates', 20, 'primary', NOW(), 100);

-- 8c. Flags group — right column (must start with IsActive per layout rules)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555375 /*From ID Server*/, 549512,
    NOW(), 100, 'Y', 'flags', 10, 'primary', NOW(), 100);

-- 8d. Org group — right column, below flags
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555376 /*From ID Server*/, 549512,
    NOW(), 100, 'Y', 'org', 20, 'primary', NOW(), 100);

-- 8e. Reorganise existing AD_UI_Elements

-- AD_Org_ID (651165): move to org group in right column; show in grid at SeqNoGrid=50
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555376, SeqNo=10, IsDisplayedGrid='Y', SeqNoGrid=50, Updated=NOW() WHERE AD_UI_Element_ID=651165;

-- IsActive (651166): move to flags group (first element — required by layout rules); hide from grid
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555375, SeqNo=10, IsDisplayedGrid='N', SeqNoGrid=999, Updated=NOW() WHERE AD_UI_Element_ID=651166;

-- DateFrom (651167): keep in primary group but hide from both form and grid
UPDATE AD_UI_Element SET SeqNo=999, SeqNoGrid=999, IsDisplayed='N', IsDisplayedGrid='N', Updated=NOW() WHERE AD_UI_Element_ID=651167;

-- DateTo (651168): same
UPDATE AD_UI_Element SET SeqNo=999, SeqNoGrid=999, IsDisplayed='N', IsDisplayedGrid='N', Updated=NOW() WHERE AD_UI_Element_ID=651168;

-- DateTrx (651169): same
UPDATE AD_UI_Element SET SeqNo=999, SeqNoGrid=999, IsDisplayed='N', IsDisplayedGrid='N', Updated=NOW() WHERE AD_UI_Element_ID=651169;

-- C_AcctSchema_ID (651170): stays in primary group — update SeqNo to 20 (after DocumentNo=10); show in grid at SeqNoGrid=30
UPDATE AD_UI_Element SET SeqNo=20, IsDisplayedGrid='Y', SeqNoGrid=30, Updated=NOW() WHERE AD_UI_Element_ID=651170;

-- Description (651171): stays in primary group — SeqNo=30; hide from grid
UPDATE AD_UI_Element SET SeqNo=30, IsDisplayedGrid='N', SeqNoGrid=999, Updated=NOW() WHERE AD_UI_Element_ID=651171;

-- Processed (651172): move to flags group; show in grid at SeqNoGrid=40
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555375, SeqNo=20, IsDisplayedGrid='Y', SeqNoGrid=40, Updated=NOW() WHERE AD_UI_Element_ID=651172;

-- Processing (651173): move to flags group but hide from both form and grid
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555375, SeqNo=999, SeqNoGrid=999, IsDisplayed='N', IsDisplayedGrid='N', Updated=NOW() WHERE AD_UI_Element_ID=651173;

-- 8f. Insert new AD_UI_Elements for the 3 new fields

-- DocumentNo (651697): primary group, SeqNo=10 (first visible field)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780251 /*From ID Server*/, 0, 549256,
    555313, 651697 /*From ID Server*/, 'F',
    NOW(), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Document No', 10, 10, 0,
    NOW(), 100);

-- C_Period_ID (651698): dates group, SeqNo=10
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780253 /*From ID Server*/, 0, 549256,
    555374, 651698 /*From ID Server*/, 'F',
    NOW(), 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Period', 10, 20, 0,
    NOW(), 100);

-- DateAcct (651699): dates group, SeqNo=20
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780252 /*From ID Server*/, 0, 549256,
    555374, 651699 /*From ID Server*/, 'F',
    NOW(), 100, 'Y', 'N',
    'Y', 'N', 'N',
    'Date Acct', 20, 999, 0,
    NOW(), 100);

-- ==========================================================================================
-- Section 9: Default filter columns (IsSelectionColumn)
-- Target: DocumentNo, C_Period_ID, C_AcctSchema_ID, Processed, AD_Org_ID
-- AD_Org_ID (14453) already has IsSelectionColumn='Y' — no change needed
-- ==========================================================================================
UPDATE AD_Column SET IsSelectionColumn='Y', Updated=NOW() WHERE AD_Column_ID=592556; -- DocumentNo
UPDATE AD_Column SET IsSelectionColumn='Y', Updated=NOW() WHERE AD_Column_ID=592558; -- C_Period_ID
UPDATE AD_Column SET IsSelectionColumn='Y', Updated=NOW() WHERE AD_Column_ID=592509; -- C_AcctSchema_ID
UPDATE AD_Column SET IsSelectionColumn='Y', Updated=NOW() WHERE AD_Column_ID=14465;  -- Processed
