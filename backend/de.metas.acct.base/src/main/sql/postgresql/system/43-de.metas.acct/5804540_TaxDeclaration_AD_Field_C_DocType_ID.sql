-- Tax Declaration — add AD_Field + AD_UI_Element for C_DocType_ID on header tab 549256 (column 592580 was added by integrated 5803920 without the UI rows)
-- WebUI silently ignores AD_Columns without paired AD_Field + AD_UI_Element rows.
-- Mirror pattern of existing sibling field 779186 (C_AcctSchema_ID) created in 5802040.
-- Tab: 549256 (header tab of Tax Declaration window 542146)
-- ElementGroup: 555313 (default identity-class group: AD_Org_ID, DocumentNo, C_AcctSchema_ID, ...)
-- Column: 592580 (C_TaxDeclaration.C_DocType_ID, AD_Element_ID=196 reuse)

-- AD_Field row for C_DocType_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    Created, CreatedBy, DisplayLength, EntityType,
    IsActive, IsDisplayed, IsDisplayedGrid,
    IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
    Name, Updated, UpdatedBy)
VALUES (0, 592580, 780485 /*From ID Server*/, 0, 549256,
    TIMESTAMP '2026-05-26 00:00:00', 100, 1, 'de.metas.acct',
    'Y', 'Y', 'Y',
    'N', 'N', 'N', 'N', 'N',
    'Document Type', TIMESTAMP '2026-05-26 00:00:00', 100);

-- AD_Field_Trl rows for system languages (matches pattern from 5802040)
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
    'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Field_ID = 780485
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Pull element translations onto the new field (Belegart / Document Type / Type de document, etc.)
SELECT update_FieldTranslation_From_AD_Name_Element(196);

-- AD_UI_Element row pairing the field into the "default" group (555313).
-- SeqNo=15 places it between DocumentNo (SeqNo=10) and Buchführungs-Schema (SeqNo=20) — identity columns first.
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy)
VALUES (0, 780485 /*From ID Server*/, 0, 549256,
    555313, 651840 /*From ID Server*/, 'F',
    TIMESTAMP '2026-05-26 00:00:00', 100, 'Y', 'N',
    'Y', 'Y', 'N',
    'Document Type', 15, 15, 0,
    TIMESTAMP '2026-05-26 00:00:00', 100);
