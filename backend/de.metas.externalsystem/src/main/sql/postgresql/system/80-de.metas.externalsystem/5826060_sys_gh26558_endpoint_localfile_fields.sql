-- Window fields for the LOCAL_FILE transport on ExternalSystem_Endpoint (tab 548506, window 541967).
-- Exposes the columns added by 5826050_sys_gh26558_endpoint_localfile_columns.sql (LocalRootLocation,
-- Frequency, ImportFileNamePattern), visible only when TransportType = LOCAL_FILE.
--
-- The LOCAL_FILE element group shares the section's FIRST column (549281) with the HTTP group rather
-- than getting a third column of its own: the WebUI divides a section's 12-unit grid evenly across its
-- columns (frontend/src/components/window/Section.js), so a third column would take every column from
-- col-sm-6 to col-sm-4 and truncate the pre-existing HTTP and SFTP labels (measured at a 1280px
-- viewport: 607px -> 405px per column; "SFTP-Verzeichnispfad" -> "SFTP-Verz..."). Only one transport's
-- group is ever displayed, since every field is DisplayLogic-gated on TransportType, so sharing a
-- column costs nothing.
--
-- IDs allocated from idserver.metas.de on 2026-09-23:
--   AD_UI_ElementGroup 555778 (new group "LOCAL_FILE", SeqNo=20, under existing column 549281)
--   AD_Field           785059 (LocalRootLocation field on tab 548506)
--   AD_Field           785060 (Frequency field on tab 548506)
--   AD_Field           785061 (ImportFileNamePattern field on tab 548506)
--   AD_UI_Element      654799 (LocalRootLocation in group 555778)
--   AD_UI_Element      654800 (Frequency in group 555778)
--   AD_UI_Element      654801 (ImportFileNamePattern in group 555778)

-- ============================================================
-- 1. AD_UI_ElementGroup (555778): "LOCAL_FILE", SeqNo=20, in the section's existing first column
-- ============================================================
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_UI_ElementGroup_ID, AD_UI_Column_ID, SeqNo, UIStyle, Name)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
    555778 /*From ID Server*/, 549281, 20, NULL, 'LOCAL_FILE');

-- ============================================================
-- 2. AD_Field: LocalRootLocation (785059) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
    785059 /*From ID Server*/, 548506, 593641,
    'Lokales Stammverzeichnis', 'Stammverzeichnis des lokalen Rechners.', NULL,
    'Y', 'N', 'N', 'N',
    0, 0, '@TransportType/X@=''LOCAL_FILE''', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=785059
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(583042);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785059;
SELECT AD_Element_Link_Create_Missing_Field(785059);

-- ============================================================
-- 3. AD_Field: Frequency (785060) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
    785060 /*From ID Server*/, 548506, 593642,
    'Häufigkeit', 'Häufigkeit von Ereignissen', NULL,
    'Y', 'N', 'N', 'N',
    0, 0, '@TransportType/X@=''LOCAL_FILE''', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=785060
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(1506);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785060;
SELECT AD_Element_Link_Create_Missing_Field(785060);

-- ============================================================
-- 4. AD_Field: ImportFileNamePattern (785061) on tab 548506
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Field_ID, AD_Tab_ID, AD_Column_ID,
    Name, Description, Help,
    IsDisplayed, IsReadOnly, IsMandatory, IsEncrypted,
    SeqNo, SeqNoGrid, DisplayLogic, EntityType, IsDisplayedGrid)
VALUES (0, 0, 'Y',
    TO_TIMESTAMP('2026-09-23 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-23 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
    785061 /*From ID Server*/, 548506, 593643,
    'Import-Dateinamensmuster',
    'Ändert den resultierenden Namen einer importierten Datei — für den Anhang am Datensatz und für die Kopie im Verzeichnis für bearbeitete bzw. fehlerhafte Dateien. Platzhalter: {filename} (Dateiname ohne Endung), {timestamp} (yyyyMMdd_HHmmss); die Endung der Quelldatei wird automatisch angehängt. Leer: Name bleibt unverändert.',
    NULL,
    'Y', 'N', 'N', 'N',
    0, 0, '@TransportType/X@=''LOCAL_FILE''', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=785061
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(585487);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785061;
SELECT AD_Element_Link_Create_Missing_Field(785061);

-- ============================================================
-- 5. AD_UI_Element: LocalRootLocation (654799) in group 555778, SeqNo=10
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785059, 0, 548506, 555778,
    654799 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-09-23 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'Lokales Stammverzeichnis', 10, 0, 0,
    TO_TIMESTAMP('2026-09-23 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 6. AD_UI_Element: Frequency (654800) in group 555778, SeqNo=20
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785060, 0, 548506, 555778,
    654800 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-09-23 11:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'Häufigkeit', 20, 0, 0,
    TO_TIMESTAMP('2026-09-23 11:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- ============================================================
-- 7. AD_UI_Element: ImportFileNamePattern (654801) in group 555778, SeqNo=30
-- ============================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
    AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
    IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
    IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785061, 0, 548506, 555778,
    654801 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-09-23 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
    'N', 'N', 'Y', 'N',
    'N', 'N', 0,
    'Import-Dateinamensmuster', 30, 0, 0,
    TO_TIMESTAMP('2026-09-23 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
