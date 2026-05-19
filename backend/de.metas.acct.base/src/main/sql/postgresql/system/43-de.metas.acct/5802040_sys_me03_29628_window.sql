-- Tax Declaration Window, Tabs, Fields, UI Elements, and Menu
--
-- IDs used:
--   AD_Window_ID=542146, Tabs: 549256/549257/549258
--   AD_Element_ID=584857 (legacy window element for C_TaxDeclaration_Legacy)
--   AD_UI_Section: 547771/547772/547773
--   AD_UI_Column: 549485/549486/549487
--   AD_UI_ElementGroup: 555313/555314/555315
--   AD_Field Header: 779181-779189, Lines: 779190-779195, Accts: 779196-779206
--   AD_UI_Element Header: 651165-651173, Lines: 651174-651179, Accts: 651180-651190
--   AD_Menu: 542323

-- ============================================================
-- Part 1: Inactivate old Steuererklärung window
-- ============================================================
UPDATE AD_Window
SET IsActive = 'N', Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Window_ID = 359;

-- Rename old window's TRLs to avoid unique constraint clash on new window
UPDATE AD_Window_Trl
SET Name = 'Tax Declaration (legacy)', Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Window_ID = 359 AND AD_Language = 'en_US';

UPDATE AD_Window_Trl
SET Name = 'Steuererklärung (alt)', Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Window_ID = 359 AND AD_Language IN ('de_DE', 'de_CH');

-- Also rename the base-record Name so after_migration_sync_translations doesn't reset it
UPDATE AD_Window
SET Name = 'Tax Declaration (legacy)', Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Window_ID = 359;

-- Re-link window 359 to a dedicated legacy element so the translation sync only targets the new window.
-- AD_Window.AD_Element_ID is NOT NULL so we cannot set it to NULL.
-- Without this, after_migration_sync_translations() finds both windows for element 2862 and
-- tries to set window 359 Name back to 'Tax Declaration' — violating the ad_window_name constraint.
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, PrintName, Name
)
VALUES (
    584857 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-19 00:00:00', 0, TIMESTAMP '2026-05-19 00:00:00', 0,
    'C_TaxDeclaration_Legacy', 'D', 'Tax Declaration (legacy)', 'Tax Declaration (legacy)'
) ON CONFLICT (AD_Element_ID) DO NOTHING;

UPDATE AD_Window
SET AD_Element_ID = 584857 /*From ID Server*/, Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Window_ID = 359;

-- ============================================================
-- Part 2: Create new AD_Window
-- ============================================================
INSERT INTO AD_Window (AD_Client_ID, AD_Org_ID, AD_Window_ID, AD_Element_ID, Created, CreatedBy, EntityType,
    IsActive, IsBetaFunctionality, IsDefault, IsSOTrx, Name, Updated, UpdatedBy, WindowType)
VALUES (0, 0, 542146 /*From ID Server - pre-fetched*/, 2862 /*C_TaxDeclaration_ID*/, TIMESTAMP '2026-05-19 00:00:00', 100, 'de.metas.acct',
    'Y', 'N', 'N', 'N', 'Tax Declaration', TIMESTAMP '2026-05-19 00:00:00', 100, 'M');

INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Help, IsTranslated, Name,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 542146, NULL, 'N', 'Tax Declaration',
    0, 0, TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = 542146);

-- German translation
UPDATE AD_Window_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Window_ID = 542146;

UPDATE AD_Window_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Window_ID = 542146;

-- ============================================================
-- Part 3: Create 3 AD_Tab records
-- ============================================================

-- Tab 1: Header (C_TaxDeclaration, level 0, single row)
INSERT INTO AD_Tab (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
    AD_Element_ID,
    Created, CreatedBy, EntityType, HasTree, IsActive, IsAdvancedTab,
    IsInfoTab, IsReadOnly, IsSingleRow, IsSortTab, IsTranslationTab,
    Name, OrderByClause, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 0, 549256 /*From ID Server - pre-fetched*/, 818, 542146,
    2862 /*C_TaxDeclaration_ID*/,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'de.metas.acct', 'N', 'Y', 'N',
    'N', 'N', 'Y', 'N', 'N',
    'Tax Declaration', NULL, 10, 0, TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 549256, NULL, NULL, NULL, 'Tax Declaration',
    'N', 0, 0, TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549256);

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Tab_ID = 549256;

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Tab_ID = 549256;

-- Tab 2: Lines (C_TaxDeclarationLine, level 1, read-only)
INSERT INTO AD_Tab (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
    AD_Element_ID,
    Created, CreatedBy, EntityType, HasTree, IsActive, IsAdvancedTab,
    IsInfoTab, IsReadOnly, IsSingleRow, IsSortTab, IsTranslationTab,
    Name, OrderByClause, SeqNo, TabLevel, Updated, UpdatedBy,
    AD_Column_ID)
VALUES (0, 0, 549257 /*From ID Server - pre-fetched*/, 819, 542146,
    2863 /*C_TaxDeclarationLine_ID*/,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'de.metas.acct', 'N', 'Y', 'N',
    'N', 'Y', 'N', 'N', 'N',
    'Lines', NULL, 20, 1, TIMESTAMP '2026-05-19 00:00:00', 100,
    14476 /*C_TaxDeclaration_ID on C_TaxDeclarationLine*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 549257, NULL, NULL, NULL, 'Lines',
    'N', 0, 0, TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549257);

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Zeilen',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Tab_ID = 549257;

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Zeilen',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Tab_ID = 549257;

-- Tab 3: Accounting Facts (C_TaxDeclarationAcct, level 1, read-only)
INSERT INTO AD_Tab (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
    AD_Element_ID,
    Created, CreatedBy, EntityType, HasTree, IsActive, IsAdvancedTab,
    IsInfoTab, IsReadOnly, IsSingleRow, IsSortTab, IsTranslationTab,
    Name, OrderByClause, SeqNo, TabLevel, Updated, UpdatedBy,
    AD_Column_ID)
VALUES (0, 0, 549258 /*From ID Server - pre-fetched*/, 820, 542146,
    2864 /*C_TaxDeclarationAcct_ID*/,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'de.metas.acct', 'N', 'Y', 'N',
    'N', 'Y', 'N', 'N', 'N',
    'Accounting Facts', NULL, 30, 1, TIMESTAMP '2026-05-19 00:00:00', 100,
    14494 /*C_TaxDeclaration_ID on C_TaxDeclarationAcct*/);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 549258, NULL, NULL, NULL, 'Accounting Facts',
    'N', 0, 0, TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = 549258);

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Buchungszeilen',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Tab_ID = 549258;

UPDATE AD_Tab_Trl
SET IsTranslated = 'Y', Name = 'Buchungszeilen',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Tab_ID = 549258;

-- ============================================================
-- Part 4: UI Structure (Section + Column + ElementGroup per tab)
-- ============================================================

-- Header tab UI structure
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_UI_Section_ID, AD_Tab_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 547771 /*From ID Server*/, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'main', 10, TIMESTAMP '2026-05-19 00:00:00', 100, 'main');

INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
    Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549485 /*From ID Server*/, 547771,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 10, TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555313 /*From ID Server*/, 549485,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'default', 10, 'primary', TIMESTAMP '2026-05-19 00:00:00', 100);

-- Lines tab UI structure
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_UI_Section_ID, AD_Tab_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 547772 /*From ID Server*/, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'main', 10, TIMESTAMP '2026-05-19 00:00:00', 100, 'main');

INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
    Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549486 /*From ID Server*/, 547772,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 10, TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555314 /*From ID Server*/, 549486,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'default', 10, 'primary', TIMESTAMP '2026-05-19 00:00:00', 100);

-- Accts tab UI structure
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_UI_Section_ID, AD_Tab_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 547773 /*From ID Server*/, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'main', 10, TIMESTAMP '2026-05-19 00:00:00', 100, 'main');

INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
    Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549487 /*From ID Server*/, 547773,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 10, TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID,
    Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 555315 /*From ID Server*/, 549487,
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'default', 10, 'primary', TIMESTAMP '2026-05-19 00:00:00', 100);

-- ============================================================
-- Part 5a: Header tab fields (AD_Tab_ID=549256)
-- ============================================================

-- AD_Org_ID (col 14453)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14453, 779181 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Org', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779181
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779181 /*From ID Server*/, 0, 549256,
    555313, 651165 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Org', 10, 10, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- IsActive (col 14454)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14454, 779182 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Active', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779182
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779182 /*From ID Server*/, 0, 549256,
    555313, 651166 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Active', 20, 20, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- DateFrom (col 14462)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14462, 779183 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Date From', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779183
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779183 /*From ID Server*/, 0, 549256,
    555313, 651167 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Date From', 30, 30, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- DateTo (col 14463)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14463, 779184 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Date To', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779184
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779184 /*From ID Server*/, 0, 549256,
    555313, 651168 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Date To', 40, 40, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- DateTrx (col 14461)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14461, 779185 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Document Date', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779185
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779185 /*From ID Server*/, 0, 549256,
    555313, 651169 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Document Date', 50, 50, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- C_AcctSchema_ID (col 592509)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592509, 779186 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Accounting Schema', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779186
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779186 /*From ID Server*/, 0, 549256,
    555313, 651170 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Accounting Schema', 60, 60, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Description (col 14460)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14460, 779187 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Description', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779187
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779187 /*From ID Server*/, 0, 549256,
    555313, 651171 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Description', 70, 70, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Processed (col 14465) — read-only
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14465, 779188 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Processed', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779188
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779188 /*From ID Server*/, 0, 549256,
    555313, 651172 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Processed', 80, 80, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Processing / Build button (col 14464)
-- Note: AD_Field has no AD_Process_ID column; the process link is via AD_Column.AD_Process_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14464, 779189 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Process', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779189
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779189 /*From ID Server*/, 0, 549256,
    555313, 651173 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Process', 90, 90, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- ============================================================
-- Part 5b: Lines tab fields (AD_Tab_ID=549257, all read-only)
-- ============================================================

-- Line (col 14475)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14475, 779190 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Line', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779190
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779190 /*From ID Server*/, 0, 549257,
    555314, 651174 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Line', 10, 10, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- C_VAT_Code_ID (col 592510)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592510, 779191 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'VAT Code', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779191
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779191 /*From ID Server*/, 0, 549257,
    555314, 651175 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'VAT Code', 20, 20, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- AmountType (col 592511)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592511, 779192 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Amount Type', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779192
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779192 /*From ID Server*/, 0, 549257,
    555314, 651176 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Amount Type', 30, 30, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Amount (col 592512)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592512, 779193 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Amount', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779193
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779193 /*From ID Server*/, 0, 549257,
    555314, 651177 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Amount', 40, 40, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- LineCount (col 592513)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592513, 779194 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Line Count', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779194
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779194 /*From ID Server*/, 0, 549257,
    555314, 651178 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Line Count', 50, 50, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- C_Currency_ID (col 14635)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14635, 779195 /*From ID Server*/, 0, 549257,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Currency', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779195
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779195 /*From ID Server*/, 0, 549257,
    555314, 651179 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Currency', 60, 60, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- ============================================================
-- Part 5c: Accts tab fields (AD_Tab_ID=549258, all read-only)
-- ============================================================

-- DateAcct (col 14504)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14504, 779196 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Accounting Date', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779196
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779196 /*From ID Server*/, 0, 549258,
    555315, 651180 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Accounting Date', 10, 10, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Account_ID (col 14503)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14503, 779197 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Account', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779197
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779197 /*From ID Server*/, 0, 549258,
    555315, 651181 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Account', 20, 20, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- C_BPartner_ID (col 14502)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14502, 779198 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Business Partner', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779198
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779198 /*From ID Server*/, 0, 549258,
    555315, 651182 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Business Partner', 30, 30, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- C_VAT_Code_ID (col 592514)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592514, 779199 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'VAT Code', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779199
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779199 /*From ID Server*/, 0, 549258,
    555315, 651183 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'VAT Code', 40, 40, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- AmountType (col 592515)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592515, 779200 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Amount Type', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779200
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779200 /*From ID Server*/, 0, 549258,
    555315, 651184 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Amount Type', 50, 50, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- AmtAcctDr (col 14497)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14497, 779201 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Debit Amount', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779201
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779201 /*From ID Server*/, 0, 549258,
    555315, 651185 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Debit Amount', 60, 60, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- AmtAcctCr (col 14498)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14498, 779202 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Credit Amount', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779202
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779202 /*From ID Server*/, 0, 549258,
    555315, 651186 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Credit Amount', 70, 70, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Amount (col 592519)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592519, 779203 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Amount', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779203
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779203 /*From ID Server*/, 0, 549258,
    555315, 651187 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Amount', 80, 80, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Fact_Acct_ID (col 14495)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 14495, 779204 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'GL Entry', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779204
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779204 /*From ID Server*/, 0, 549258,
    555315, 651188 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'GL Entry', 90, 90, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- AD_Table_ID (col 592516)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592516, 779205 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Table', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779205
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779205 /*From ID Server*/, 0, 549258,
    555315, 651189 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Table', 100, 100, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- Record_ID (col 592517)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592517, 779206 /*From ID Server*/, 0, 549258,
    TIMESTAMP '2026-05-19 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'Y', 'N',
    'Record ID', TIMESTAMP '2026-05-19 00:00:00', 100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 779206
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 779206 /*From ID Server*/, 0, 549258,
    555315, 651190 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-19 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Record ID', 110, 110, 0,
    TIMESTAMP '2026-05-19 00:00:00', 100);

-- ============================================================
-- Part 6: AD_Menu entry
-- ============================================================
INSERT INTO AD_Menu (AD_Client_ID, AD_Org_ID, AD_Menu_ID, AD_Window_ID, Action, EntityType,
    IsActive, IsCreateNew, IsReadOnly, IsSOTrx, Name, Updated, UpdatedBy, Created, CreatedBy,
    IsSummary, AD_Element_ID, InternalName)
VALUES (0, 0, 542323 /*From ID Server*/, 542146, 'W', 'de.metas.acct',
    'Y', 'N', 'N', 'N', 'Tax Declaration', TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    'N', 2862 /*C_TaxDeclaration_ID*/, 'C_TaxDeclaration');

INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, IsTranslated, Name,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 542323, NULL, 'N', 'Tax Declaration',
    0, 0, TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = 542323);

UPDATE AD_Menu_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Menu_ID = 542323;

UPDATE AD_Menu_Trl
SET IsTranslated = 'Y', Name = 'Steuererklärung',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Menu_ID = 542323;

-- Place new menu entry under Finanzbuchhaltung (AD_Menu_ID=278)
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID, 0, 'Y', TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    t.AD_Tree_ID, 542323, 278, 12
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y' AND t.AD_Table_ID = 116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = t.AD_Tree_ID AND e.Node_ID = 542323);

-- Propagate AD_Element translations to AD_Field_Trl and other downstream TRL tables
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2862, NULL);  -- C_TaxDeclaration
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2863, NULL);  -- C_TaxDeclarationLine
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2864, NULL);  -- C_TaxDeclarationAcct
