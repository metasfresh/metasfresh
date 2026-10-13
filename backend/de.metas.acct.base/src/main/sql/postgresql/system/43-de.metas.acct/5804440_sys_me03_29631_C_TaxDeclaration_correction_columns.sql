-- Tax Declaration — add 4 Correction-lifecycle columns to C_TaxDeclaration header
--
-- IsCorrection: Flag indicating this is a correction of an earlier Tax Declaration
-- C_TaxDeclaration_Original_ID: Self-reference pointing to the original (non-correction) declaration
-- IsCorrectionNeeded: Flag set externally to indicate a future correction is needed (workflow control)
-- CorrectionNeededReason: User-facing text explaining why correction is needed

-- ====================================================================================
-- Section 1: AD_Element rows (4 elements)
-- ====================================================================================
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, Name, PrintName, EntityType
) VALUES
    (584908 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
     'IsCorrection', 'Berichtigung?', 'Berichtigung?', 'de.metas.acct'),
    (584909 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
     'C_TaxDeclaration_Original_ID', 'Berichtigte Steuererklärung', 'Berichtigte Steuererklärung', 'de.metas.acct'),
    (584910 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
     'IsCorrectionNeeded', 'Berichtigung erforderlich?', 'Berichtigung erforderlich?', 'de.metas.acct'),
    (584911 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:00:00', 100, TIMESTAMP '2026-05-25 00:00:00', 100,
     'CorrectionNeededReason', 'Grund für Berichtigung', 'Grund für Berichtigung', 'de.metas.acct');

-- ====================================================================================
-- Section 2: AD_Element_Trl (en_US explicit + CROSS JOIN for other system languages)
-- ====================================================================================
-- Phase 1: en_US explicit (because the EN names differ from the DE base)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
VALUES
    ('en_US', 584908 /*From ID Server*/, 'Correction?', 'Correction?', NULL, NULL, 'Y', 0, 0, TIMESTAMP '2026-05-25 00:00:12', 100, TIMESTAMP '2026-05-25 00:00:12', 100, 'Y'),
    ('en_US', 584909 /*From ID Server*/, 'Original Tax Declaration', 'Original Tax Declaration', NULL, NULL, 'Y', 0, 0, TIMESTAMP '2026-05-25 00:00:12', 100, TIMESTAMP '2026-05-25 00:00:12', 100, 'Y'),
    ('en_US', 584910 /*From ID Server*/, 'Correction needed?', 'Correction needed?', NULL, NULL, 'Y', 0, 0, TIMESTAMP '2026-05-25 00:00:12', 100, TIMESTAMP '2026-05-25 00:00:12', 100, 'Y'),
    ('en_US', 584911 /*From ID Server*/, 'Reason for Correction', 'Reason for Correction', NULL, NULL, 'Y', 0, 0, TIMESTAMP '2026-05-25 00:00:12', 100, TIMESTAMP '2026-05-25 00:00:12', 100, 'Y');

-- Phase 2: any other active system language gets the base-language fallback
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, e.AD_Element_ID, e.Name, e.PrintName, e.Description, e.Help, 'N',
    e.AD_Client_ID, e.AD_Org_ID, e.Created, e.CreatedBy, e.Updated, e.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID IN (584908 /*From ID Server*/, 584909 /*From ID Server*/, 584910 /*From ID Server*/, 584911 /*From ID Server*/)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = e.AD_Element_ID);

-- ====================================================================================
-- Section 3: AD_Column rows (4 columns for C_TaxDeclaration)
-- ====================================================================================
-- 3a. IsCorrection: CHAR(1) NOT NULL DEFAULT 'N'
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592616 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:01:00', 100, TIMESTAMP '2026-05-25 00:01:00', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclaration'),
    584908 /*From ID Server*/, 'IsCorrection', 1, 'N', 'N', 'Y',
    'N', 'N', 0, 'N', 'Y', 'N',
    319, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);

-- 3b. C_TaxDeclaration_Original_ID: NUMERIC(10) NULL
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592617 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:01:01', 100, TIMESTAMP '2026-05-25 00:01:01', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclaration'),
    584909 /*From ID Server*/, 'C_TaxDeclaration_Original_ID', 10, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    30, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);

-- 3c. IsCorrectionNeeded: CHAR(1) NOT NULL DEFAULT 'N'
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592618 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:01:02', 100, TIMESTAMP '2026-05-25 00:01:02', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclaration'),
    584910 /*From ID Server*/, 'IsCorrectionNeeded', 1, 'N', 'N', 'Y',
    'N', 'N', 0, 'N', 'Y', 'N',
    319, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);

-- 3d. CorrectionNeededReason: VARCHAR(2000) NULL
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592619 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-25 00:01:03', 100, TIMESTAMP '2026-05-25 00:01:03', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_TaxDeclaration'),
    584911 /*From ID Server*/, 'CorrectionNeededReason', 2000, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    14, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);

-- ====================================================================================
-- Section 4: AD_Column_Trl (translate column names to all active languages)
-- ====================================================================================
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID IN (592616, 592617, 592618, 592619)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- ====================================================================================
-- Section 5: Physical DDL — ALTER TABLE C_TaxDeclaration ADD COLUMN
-- ====================================================================================
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS IsCorrection CHAR(1) NOT NULL DEFAULT 'N';
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS C_TaxDeclaration_Original_ID NUMERIC(10);
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS IsCorrectionNeeded CHAR(1) NOT NULL DEFAULT 'N';
ALTER TABLE C_TaxDeclaration ADD COLUMN IF NOT EXISTS CorrectionNeededReason VARCHAR(2000);

-- Foreign key constraint: C_TaxDeclaration_Original_ID references C_TaxDeclaration(C_TaxDeclaration_ID)
-- Named constraint: TaxDeclarationOriginal_TaxDeclaration
ALTER TABLE C_TaxDeclaration ADD CONSTRAINT TaxDeclarationOriginal_TaxDeclaration
  FOREIGN KEY (C_TaxDeclaration_Original_ID) REFERENCES C_TaxDeclaration(C_TaxDeclaration_ID);

-- ====================================================================================
-- Section 6: AD_Field rows (4 fields on the header tab 549256)
-- ====================================================================================
-- 6a. IsCorrection field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592616 /*From ID Server*/, 780479 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-25 00:02:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Berichtigung?', TIMESTAMP '2026-05-25 00:02:00', 100);

-- 6b. C_TaxDeclaration_Original_ID field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592617 /*From ID Server*/, 780480 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-25 00:02:01', 100, 10, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Berichtigte Steuererklärung', TIMESTAMP '2026-05-25 00:02:01', 100);

-- 6c. IsCorrectionNeeded field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592618 /*From ID Server*/, 780481 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-25 00:02:02', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Berichtigung erforderlich?', TIMESTAMP '2026-05-25 00:02:02', 100);

-- 6d. CorrectionNeededReason field
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592619 /*From ID Server*/, 780482 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-25 00:02:03', 100, 2000, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Grund für Berichtigung', TIMESTAMP '2026-05-25 00:02:03', 100);

-- ====================================================================================
-- Section 7: AD_Field_Trl (translate field names to all non-base system languages)
-- ====================================================================================
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID IN (780479, 780480, 780481, 780482)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- ====================================================================================
-- Section 8: AD_UI_Element rows (4 elements in the header tab's main group 555313)
-- Assign SeqNo starting from 40 (after current max of 30, incrementing by 10)
-- ====================================================================================
-- 8a. IsCorrection UI element
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780479 /*From ID Server*/, 0, 549256,
    555313, 651834 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-25 00:03:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Berichtigung?', 40, 40, 0,
    TIMESTAMP '2026-05-25 00:03:00', 100);

-- 8b. C_TaxDeclaration_Original_ID UI element
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780480 /*From ID Server*/, 0, 549256,
    555313, 651835 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-25 00:03:01', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Berichtigte Steuererklärung', 50, 50, 0,
    TIMESTAMP '2026-05-25 00:03:01', 100);

-- 8c. IsCorrectionNeeded UI element
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780481 /*From ID Server*/, 0, 549256,
    555313, 651836 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-25 00:03:02', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Berichtigung erforderlich?', 60, 60, 0,
    TIMESTAMP '2026-05-25 00:03:02', 100);

-- 8d. CorrectionNeededReason UI element
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780482 /*From ID Server*/, 0, 549256,
    555313, 651837 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-25 00:03:03', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Grund für Berichtigung', 70, 70, 0,
    TIMESTAMP '2026-05-25 00:03:03', 100);
