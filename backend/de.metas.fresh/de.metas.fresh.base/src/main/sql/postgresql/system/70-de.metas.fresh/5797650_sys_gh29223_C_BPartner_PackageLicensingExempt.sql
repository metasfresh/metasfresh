-- Package Licensing: add vendor exemption fields to C_BPartner
-- Vendors with pre-licensing (Vorlizenzierung) or domestic vendors can be marked as exempt
-- from packaging licensing calculations. The exemption is time-bounded (optional from/to dates).

-- ==========================================================================
-- 1) AD_Elements
-- ==========================================================================

-- IsPackageLicensingExempt
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Help, Updated, UpdatedBy)
VALUES (0, 584752 /*From ID Server*/, 0, 'IsPackageLicensingExempt', '2026-04-09 14:00', 0, 'D', 'Y',
        'Verpackungslizenzierung befreit', 'Verpackungsliz. befreit',
        'Lieferant ist von der Verpackungslizenzierung befreit (Vorlizenzierung)',
        'Wenn aktiviert, werden Wareneingänge von diesem Lieferanten bei der Verpackungslizenzierungs-Mengenmeldung nicht mitgezählt. Gilt für vorlizenzierte Lieferanten oder Lieferanten aus dem Inland.',
        '2026-04-09 14:00', 0);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584752, e.Name, e.PrintName, e.Description, e.Help, 'N', 0, 0, e.Created, 0, e.Updated, 0, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 584752
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584752);

UPDATE AD_Element_Trl SET Name = 'Package Licensing Exempt', PrintName = 'Pkg. Lic. Exempt',
                          Description = 'Vendor is exempt from package licensing (pre-licensed)',
                          Help = 'When enabled, receipts from this vendor are excluded from the packaging licensing quantity report. Applies to pre-licensed vendors or domestic vendors.',
                          IsTranslated = 'Y', Updated = '2026-04-09 14:00', UpdatedBy = 0
WHERE AD_Element_ID = 584752 AND AD_Language = 'en_US';

-- PackageLicensingExemptFrom
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Updated, UpdatedBy)
VALUES (0, 584753 /*From ID Server*/, 0, 'PackageLicensingExemptFrom', '2026-04-09 14:00', 0, 'D', 'Y',
        'Verpackungsliz. befreit ab', 'Verpackungsliz. befreit ab',
        'Ab diesem Datum ist der Lieferant von der Verpackungslizenzierung befreit',
        '2026-04-09 14:00', 0);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584753, e.Name, e.PrintName, e.Description, 'N', 0, 0, e.Created, 0, e.Updated, 0, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 584753
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584753);

UPDATE AD_Element_Trl SET Name = 'Pkg. Lic. Exempt From', PrintName = 'Pkg. Lic. Exempt From',
                          Description = 'Start date of vendor package licensing exemption',
                          IsTranslated = 'Y', Updated = '2026-04-09 14:00', UpdatedBy = 0
WHERE AD_Element_ID = 584753 AND AD_Language = 'en_US';

-- PackageLicensingExemptTo
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Updated, UpdatedBy)
VALUES (0, 584754 /*From ID Server*/, 0, 'PackageLicensingExemptTo', '2026-04-09 14:00', 0, 'D', 'Y',
        'Verpackungsliz. befreit bis', 'Verpackungsliz. befreit bis',
        'Bis zu diesem Datum ist der Lieferant von der Verpackungslizenzierung befreit',
        '2026-04-09 14:00', 0);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584754, e.Name, e.PrintName, e.Description, 'N', 0, 0, e.Created, 0, e.Updated, 0, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 584754
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Element_ID = 584754);

UPDATE AD_Element_Trl SET Name = 'Pkg. Lic. Exempt To', PrintName = 'Pkg. Lic. Exempt To',
                          Description = 'End date of vendor package licensing exemption',
                          IsTranslated = 'Y', Updated = '2026-04-09 14:00', UpdatedBy = 0
WHERE AD_Element_ID = 584754 AND AD_Language = 'en_US';

-- ==========================================================================
-- 2) AD_Columns on C_BPartner (AD_Table_ID=291)
-- ==========================================================================

-- IsPackageLicensingExempt (YesNo, default N)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAlwaysUpdateable,
                       IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated,
                       IsUpdateable, Name, PersonalDataCategory, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592348 /*From ID Server*/, 584752, 0, 20, 291,
        'IsPackageLicensingExempt', '2026-04-09 14:00', 0, 'N', 'D', 1, 'Y', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'Verpackungslizenzierung befreit', 'NP', 0, '2026-04-09 14:00', 0, 0);

-- PackageLicensingExemptFrom (Date, optional)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAlwaysUpdateable,
                       IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated,
                       IsUpdateable, Name, PersonalDataCategory, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592349 /*From ID Server*/, 584753, 0, 15, 291,
        'PackageLicensingExemptFrom', '2026-04-09 14:00', 0, 'D', 7, 'Y', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'Verpackungsliz. befreit ab', 'NP', 0, '2026-04-09 14:00', 0, 0);

-- PackageLicensingExemptTo (Date, optional)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAlwaysUpdateable,
                       IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated,
                       IsUpdateable, Name, PersonalDataCategory, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592350 /*From ID Server*/, 584754, 0, 15, 291,
        'PackageLicensingExemptTo', '2026-04-09 14:00', 0, 'D', 7, 'Y', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'Verpackungsliz. befreit bis', 'NP', 0, '2026-04-09 14:00', 0, 0);

-- Physical columns (new columns, use ALTER TABLE ADD COLUMN)
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS IsPackageLicensingExempt CHAR(1) DEFAULT 'N';
UPDATE C_BPartner SET IsPackageLicensingExempt = 'N' WHERE IsPackageLicensingExempt IS NULL;

ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS PackageLicensingExemptFrom DATE;
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS PackageLicensingExemptTo DATE;

-- ==========================================================================
-- 3) AD_Fields on Vendor Tab of standard BPartner window (AD_Window_ID=123, AD_Tab_ID=224)
-- ==========================================================================

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, EntityType, IsActive, IsDisplayed, IsEncrypted, IsReadOnly,
                      Name, Updated, UpdatedBy)
VALUES (0, 592348, 777056 /*From ID Server*/, 0, 224,
        '2026-04-09 14:00', 0, 'D', 'Y', 'Y', 'N', 'N',
        'Verpackungslizenzierung befreit', '2026-04-09 14:00', 0);

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, EntityType, IsActive, IsDisplayed, IsEncrypted, IsReadOnly,
                      Name, Updated, UpdatedBy)
VALUES (0, 592349, 777057 /*From ID Server*/, 0, 224,
        '2026-04-09 14:00', 0, 'D', 'Y', 'Y', 'N', 'N',
        'Verpackungsliz. befreit ab', '2026-04-09 14:00', 0);

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, EntityType, IsActive, IsDisplayed, IsEncrypted, IsReadOnly,
                      Name, Updated, UpdatedBy)
VALUES (0, 592350, 777058 /*From ID Server*/, 0, 224,
        '2026-04-09 14:00', 0, 'D', 'Y', 'Y', 'N', 'N',
        'Verpackungsliz. befreit bis', '2026-04-09 14:00', 0);

-- AD_Field_Trl: auto-created by the system, but we need to propagate en_US translations
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, f.AD_Field_ID, f.Name, f.Description, f.Help, 'N', 0, 0, f.Created, 0, f.Updated, 0, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND f.AD_Field_ID IN (777056, 777057, 777058)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl t WHERE t.AD_Language = l.AD_Language AND t.AD_Field_ID = f.AD_Field_ID);

-- Propagate en_US translations from AD_Element_Trl to AD_Field_Trl
UPDATE AD_Field_Trl
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y',
    Updated = '2026-04-09 14:00', UpdatedBy = 0
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = 'en_US'
WHERE AD_Field_Trl.AD_Field_ID = f.AD_Field_ID
  AND f.AD_Field_ID IN (777056, 777057, 777058)
  AND AD_Field_Trl.AD_Language = 'en_US'
  AND et.IsTranslated = 'Y';

-- ==========================================================================
-- 4) AD_UI_Elements on standard BPartner Vendor tab (224) — Advanced Edit (erweiterte Erfassung)
--    Per UI guidelines: Org and Client are the last fields in advanced edit.
--    Currently there's one advanced field at SeqNo=25. We add ours at SeqNo 30, 35, 40.
-- ==========================================================================

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 777056, 0, 224, 1000033, 649798 /*From ID Server*/, 'F',
        '2026-04-09 14:00', 0, 'Y', 'Y',
        'Y', 'N', 'N',
        'Verpackungslizenzierung befreit', 30, 0, 0, '2026-04-09 14:00', 0);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 777057, 0, 224, 1000033, 649799 /*From ID Server*/, 'F',
        '2026-04-09 14:00', 0, 'Y', 'Y',
        'Y', 'N', 'N',
        'Verpackungsliz. befreit ab', 35, 0, 0, '2026-04-09 14:00', 0);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 777058, 0, 224, 1000033, 649800 /*From ID Server*/, 'F',
        '2026-04-09 14:00', 0, 'Y', 'Y',
        'Y', 'N', 'N',
        'Verpackungsliz. befreit bis', 40, 0, 0, '2026-04-09 14:00', 0);

-- NOTE: Customer-specific window (AD_Window_ID=541858, AD_Tab_ID=547789) fields
-- are in the kh202 customer repo migration, not here.
