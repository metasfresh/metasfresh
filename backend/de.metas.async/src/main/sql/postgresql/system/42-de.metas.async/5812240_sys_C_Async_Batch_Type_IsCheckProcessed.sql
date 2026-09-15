-- IDs allocated from idserver.metas.de on 2026-07-04:
--   AD_Element     585078 (IsCheckProcessed label for C_Async_Batch_Type)
--   AD_Column      592920 (C_Async_Batch_Type.IsCheckProcessed)
--   AD_Field       781326 (C_Async_Batch_Type maintenance window field)
--   AD_UI_Element  652438 (WebUI element for the field)
--
-- Gates whether the CheckProcessedAsynBatch work-package is enqueued for a given async batch type.
-- Default 'N'; seeded to 'Y' for the proven consumers in a follow-up migration.

-- 1) AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (585078 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-04 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-04 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'IsCheckProcessed', 'de.metas.async', 'Verarbeitung prüfen', 'Verarbeitung prüfen',
        'Steuert, ob nach Abschluss aller Elemente eines Stapels dessen Verarbeitungsstatus geprüft und gesetzt wird.',
        'Ist dieses Kennzeichen gesetzt, wird für Stapel dieses Typs nach Abschluss aller enthaltenen Elemente zusätzlich der Verarbeitungsstatus (Processed) des Stapels aktualisiert. Wird dies nicht benötigt, entfällt dieser zusätzliche Arbeitsschritt.')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy,
       TO_TIMESTAMP('2026-07-04 10:00:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585078
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name='Check Processed', PrintName='Check Processed',
    Description='Controls whether, once all elements of a batch complete, the batch''s processed status is checked and set.',
    Help='When enabled, an extra step verifies and updates the processed status of batches of this type once all their elements complete. When not needed, this step is skipped.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-04 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585078
;

UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-04 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585078
;

-- 2) AD_Column on C_Async_Batch_Type (540625)
INSERT INTO AD_Column (
    AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName,
    Created, CreatedBy, DDL_NoForeignKey, DefaultValue, EntityType, FacetFilterSeqNo, FieldLength,
    IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete,
    IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
    IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey,
    IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline,
    IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
    PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version
) VALUES (
    0, 592920 /*From ID Server*/, 585078 /*From ID Server*/, 0, 20, 540625, 'IsCheckProcessed',
    TO_TIMESTAMP('2026-07-04 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', 'de.metas.async', 0, 1,
    'Y', 'N', 'Y', 'N', 'N', 'N',
    'N', 'N', 'N', 'N', 'Y', 'N',
    'N', 'N', 'N', 'N', 'N',
    'N', 'Y', 'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'Y', 'N', 0, 'Verarbeitung prüfen',
    'NP', 0, 0, TO_TIMESTAMP('2026-07-04 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100, 0
)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy,
       TO_TIMESTAMP('2026-07-04 10:01:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592920
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585078)
;

-- 3) Physical DB column (new column -> ALTER TABLE ADD COLUMN wrapped in db_alter_table)
/* DDL */ SELECT public.db_alter_table('C_Async_Batch_Type','ALTER TABLE public.C_Async_Batch_Type ADD COLUMN IsCheckProcessed CHAR(1) NOT NULL DEFAULT ''N''')
;

-- 4) AD_Field on the C_Async_Batch_Type maintenance tab (540640, window 540248)
INSERT INTO AD_Field (
    AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength,
    EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly,
    IsSameLine, Name, SeqNo, SeqNoGrid, Updated, UpdatedBy
) VALUES (
    0, 592920, 781326 /*From ID Server*/, 0, 540640,
    TO_TIMESTAMP('2026-07-04 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 1,
    'de.metas.async', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N',
    'N', 'Verarbeitung prüfen', 65, 60, TO_TIMESTAMP('2026-07-04 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100
)
;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy,
       TO_TIMESTAMP('2026-07-04 10:02:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781326
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585078)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781326
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781326)
;

-- 5) WebUI placement — "flags" element group (541060), alongside IsActive (SeqNo 10)
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy
) VALUES (
    0, 781326, 0, 540640, 541060, 652438 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-07-04 10:02:30','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'Y',
    'N', 'N', 0, 'Verarbeitung prüfen', 20, 60, 0, TO_TIMESTAMP('2026-07-04 10:02:30','YYYY-MM-DD HH24:MI:SS'), 100
)
;
