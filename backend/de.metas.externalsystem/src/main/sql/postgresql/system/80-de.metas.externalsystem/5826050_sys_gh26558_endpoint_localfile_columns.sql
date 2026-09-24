-- Packzettel Import — add LocalRootLocation, Frequency, ImportFileNamePattern columns
-- Adds three columns to ExternalSystem_Endpoint for LOCAL_FILE transport: input directory, poll frequency, and filename pattern.

-- IDs allocated from idserver.metas.de on 2026-09-23:
--   AD_Element 585487 /*From ID Server*/ (ImportFileNamePattern)
--   AD_Column  593641 /*From ID Server*/ (LocalRootLocation)
--   AD_Column  593642 /*From ID Server*/ (Frequency)
--   AD_Column  593643 /*From ID Server*/ (ImportFileNamePattern)

-- ============================================================================
-- Step 1: Create ImportFileNamePattern AD_Element
-- ============================================================================

INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, Name, PrintName, Description, Help,
    EntityType
) VALUES (
    585487 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    100, TO_TIMESTAMP('2026-09-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'ImportFileNamePattern',
    'Import-Dateinamensmuster',
    'Import-Dateinamensmuster',
    'Ändert den resultierenden Namen einer importierten Datei — für den Anhang am Datensatz und für die Kopie im Verzeichnis für bearbeitete bzw. fehlerhafte Dateien. Platzhalter: {filename} (Dateiname ohne Endung), {timestamp} (yyyyMMdd_HHmmss); die Endung der Quelldatei wird automatisch angehängt. Leer: Name bleibt unverändert.',
    NULL,
    'de.metas.externalsystem'
);

-- ============================================================================
-- Step 2: Seed AD_Element_Trl for all active system languages
-- ============================================================================

INSERT INTO AD_Element_Trl (
    AD_Element_ID, AD_Language,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    IsTranslated, Name, PrintName, Description, Help
)
SELECT
    t.AD_Element_ID, l.AD_Language,
    t.AD_Client_ID, t.AD_Org_ID, 'Y',
    t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
    'N', t.Name, t.PrintName, t.Description, t.Help
FROM AD_Language l, AD_Element t
WHERE
    l.IsActive='Y' AND l.IsSystemLanguage='Y'
    AND t.AD_Element_ID=585487 /*From ID Server*/
    AND NOT EXISTS (
        SELECT 1 FROM AD_Element_Trl tt
        WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID
    );

-- ============================================================================
-- Step 3: Update English (en_US) translation for ImportFileNamePattern
-- ============================================================================

UPDATE AD_Element_Trl SET
    Name='Import Filename Pattern',
    PrintName='Import Filename Pattern',
    Description='Changes the resulting name of an imported file — for the attachment on the record and for the copy in the directory for processed or failed files. Placeholders: {filename} (filename without extension), {timestamp} (yyyyMMdd_HHmmss); the source file''s extension is appended automatically. Empty: name remains unchanged.',
    Help=NULL,
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585487 /*From ID Server*/;

-- ============================================================================
-- Step 4: Mark German translations (de_DE, de_CH) as translated
-- ============================================================================

UPDATE AD_Element_Trl SET
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-09-23 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID=585487 /*From ID Server*/;

-- ============================================================================
-- Step 5: Create AD_Column rows for the three new columns
-- ============================================================================

-- LocalRootLocation (reuses AD_Element 583042 "Lokales Stammverzeichnis")
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (593641 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    583042, 542551, 'LocalRootLocation', 'Lokales Stammverzeichnis', NULL, NULL,
    0, 'de.metas.externalsystem', 10,
    'N', 'Y', 'N', 'N', 'N', 'N',
    255, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0);

-- Frequency (reuses AD_Element 1506 "Häufigkeit")
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (593642 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    1506, 542551, 'Frequency', 'Häufigkeit', NULL, NULL,
    0, 'de.metas.externalsystem', 11,
    'N', 'Y', 'N', 'N', 'N', 'N',
    10, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0);

-- ImportFileNamePattern (new element 585487)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Element_ID, AD_Table_ID, ColumnName, Name, Description, Help,
    Version, EntityType, AD_Reference_ID,
    IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
    FieldLength, IsTranslated, IsSelectionColumn,
    PersonalDataCategory,
    CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
    IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
    IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
    IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
    IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
    SelectionColumnSeqNo, SeqNo)
VALUES (593643 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    585487 /*From ID Server*/, 542551, 'ImportFileNamePattern', 'Import-Dateinamensmuster', NULL, NULL,
    0, 'de.metas.externalsystem', 10,
    'N', 'Y', 'N', 'N', 'N', 'N',
    255, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'Y', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0);

-- ============================================================================
-- Step 6: Seed AD_Column_Trl rows for all active system languages
-- ============================================================================

INSERT INTO AD_Column_Trl (
    AD_Column_ID, AD_Language,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    IsTranslated, Name
)
SELECT
    c.AD_Column_ID, l.AD_Language,
    c.AD_Client_ID, c.AD_Org_ID, 'Y',
    c.Created, c.CreatedBy, c.Updated, c.UpdatedBy,
    'N', c.Name
FROM AD_Language l, AD_Column c
WHERE
    l.IsActive='Y' AND l.IsSystemLanguage='Y'
    AND c.AD_Column_ID IN (593641 /*From ID Server*/, 593642 /*From ID Server*/, 593643 /*From ID Server*/)
    AND NOT EXISTS (
        SELECT 1 FROM AD_Column_Trl ct
        WHERE ct.AD_Language=l.AD_Language AND ct.AD_Column_ID=c.AD_Column_ID
    );

-- ============================================================================
-- Step 7: Propagate element translations to column translations
-- ============================================================================

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583042, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1506, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585487 /*From ID Server*/, NULL);

-- ============================================================================
-- Step 8: Create physical columns in the table
-- ============================================================================

ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS LocalRootLocation VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS Frequency INTEGER;
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS ImportFileNamePattern VARCHAR(255);
