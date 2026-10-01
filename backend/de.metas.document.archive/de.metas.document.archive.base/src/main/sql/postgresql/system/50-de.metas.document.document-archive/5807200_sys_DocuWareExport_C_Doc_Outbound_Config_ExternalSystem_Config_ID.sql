-- Adds column ExternalSystem_Config_ID to C_Doc_Outbound_Config.
-- When set, archived PDFs of completed documents covered by this outbound config
-- are exported to the linked external system (e.g. a DMS like DocuWare).
--
-- IDs allocated from idserver.metas.de on 2026-06-10:
--   AD_Element   578728  (ExternalSystem_Config_ID) -- REUSED: already exists
--   AD_Column    592799  (C_Doc_Outbound_Config.ExternalSystem_Config_ID)
--   AD_Field     780753  (field on tab 540477, window 540173)
--   AD_UI_Element 652048 (in doctype group 540348 on tab 540477)
--   AD_MigrationScript prefix: 5807200

-- ============================================================
-- 1. Update AD_Element 578728 (ExternalSystem_Config_ID) with
--    generic description and help texts.
--    IMPORTANT: this element is SHARED across many tables — only
--    generic, context-neutral text belongs here.  Context-specific
--    documentation (e.g. what DocuWare does with this field) belongs
--    in user documentation, not in the shared element.
--    ORDERING NOTE: base AND trl rows must be set to the same text
--    BEFORE any propagation call; setting the base row alone is not
--    enough because the cascade from stale trl rows overwrites it.
-- ============================================================
UPDATE AD_Element
SET Description = 'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    Help        = 'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    Updated     = TO_TIMESTAMP('2026-06-10 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 578728;

-- German translations (de_DE, de_CH) — must be set before propagation calls
UPDATE AD_Element_Trl
SET Description  = 'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    Help         = 'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-10 09:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 578728 AND AD_Language IN ('de_DE', 'de_CH');

-- English translation — must be set before propagation calls
UPDATE AD_Element_Trl
SET Name         = 'External System Config',
    PrintName    = 'External System Config',
    Description  = 'External-system configuration this record is linked to.',
    Help         = 'Links this record to an external-system configuration (ExternalSystem_Config).',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-10 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 578728 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'en_US');

-- ============================================================
-- 2. Physical column on C_Doc_Outbound_Config (nullable, no FK)
--    No FK constraint: no sibling _ID columns on this table have one
--    (AD_PrintFormat_ID has FK, but ExternalSystem_Config_ID is a
--    cross-module link; de.metas.document.archive must NOT depend on
--    de.metas.externalsystem at the Java level).
-- ============================================================
ALTER TABLE C_Doc_Outbound_Config
    ADD COLUMN IF NOT EXISTS ExternalSystem_Config_ID NUMERIC(10);

-- ============================================================
-- 3. AD_Column (592799) on C_Doc_Outbound_Config (AD_Table_ID 540434)
--    AD_Reference_ID=19 (TableDir), IsMandatory='N'
--    EntityType='D' — this is a core metasfresh column on a core table.
-- ============================================================
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
VALUES (592799 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    578728, 540434, 'ExternalSystem_Config_ID', 'External System Config',
    'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    0, 'D', 19,
    'N', 'Y', 'N', 'N', 'N', 'N',
    10, 'N', 'N',
    'NP',
    'DC', 0, 'N', 'Y',
    'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N',
    'N', 'N', 'N', 'N', 0,
    0, 0)
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- Ensure Description/Help on the column use the generic shared-element texts
-- (ON CONFLICT DO NOTHING above leaves the row untouched on re-run).
UPDATE AD_Column
SET Description = 'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    Help        = 'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    Updated     = TO_TIMESTAMP('2026-06-10 09:01:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Column_ID = 592799;

-- Skeleton _Trl rows for all active system languages
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592799
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578728, 'en_US');

-- ============================================================
-- 4. AD_Field (780753) on tab 540477 (Ausgehende Belege Konfig)
--    window 540173 (Ausgehende Belege Konfig)
--    SeqNo=0 (WebUI only; AccessLevel=3)
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-06-10 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-10 09:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    780753 /*From ID Server*/, 540477, 592799,
    'External System Config',
    'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    'Y', 'N', 'N', 'N',
    0, 0, 'D', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

-- Ensure Description/Help on the field use the generic shared-element texts
-- (ON CONFLICT DO NOTHING above leaves the row untouched on re-run; this UPDATE
-- corrects any pre-existing row that still held context-specific text).
UPDATE AD_Field
SET Description = 'Konfiguration des externen Systems, mit der dieser Datensatz verknüpft ist.',
    Help        = 'Verknüpft diesen Datensatz mit einer externen System-Konfiguration (ExternalSystem_Config).',
    Updated     = TO_TIMESTAMP('2026-06-10 09:02:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Field_ID = 780753;

-- Skeleton _Trl rows
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=780753
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(578728);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780753;
SELECT AD_Element_Link_Create_Missing_Field(780753);

-- ============================================================
-- 5. AD_UI_Element (652048) in 'doctype' group (540348)
--    on tab 540477.
--    SeqNo=30 (after CC Pfad at 20), SeqNoGrid=85 (between CC Pfad=80 and Sektion=90).
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780753, 0, 540477,
    540348 /*doctype group*/, 652048 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-06-10 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'Y',
    'N', 'N', 0,
    'External System Config', 30, 85, 0,
    TO_TIMESTAMP('2026-06-10 09:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;
