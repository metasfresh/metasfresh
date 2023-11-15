-- Tab: Geschäftspartner(541526,D) -> Vorfinanzierungsvertrag
-- Table: C_BPartner_InterimContract
-- 2023-08-09T16:09:44.390241600Z
UPDATE AD_Tab
SET IsActive='Y', Updated=TO_TIMESTAMP('2023-08-09 18:09:44.39', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Tab_ID = 547108
;

-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag
-- Table: C_BPartner_InterimContract
-- 2023-08-09T16:16:30.778388300Z
INSERT INTO AD_Tab (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, Description, EntityType, HasTree, Help, ImportFields, IncludedTabNewRecordInputMode, InternalName, IsActive, IsAdvancedTab, IsAutodetectDefaultDateFilter, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord,
                    IsQueryIfNoFilters, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 587238, 582617, 0, 547145, 542357, 123, 'Y', TO_TIMESTAMP('2023-08-09 18:16:30.653', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', 'D', 'N', 'Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', 'N',
        'A', 'C_BPartner_InterimContract', 'Y', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Vorfinanzierungsvertrag', 'N', 290, 1, TO_TIMESTAMP('2023-08-09 18:16:30.653', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:30.783726900Z
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, QuickInput_CloseButton_Caption, QuickInput_OpenButton_Caption, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Tab_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.QuickInput_CloseButton_Caption,
       t.QuickInput_OpenButton_Caption,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Tab t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Tab_ID = 547145
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;

-- 2023-08-09T16:16:30.820082800Z
/* DDL */

SELECT update_tab_translation_from_ad_element(582617)
;

-- 2023-08-09T16:16:30.837971200Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Tab(547145)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Mandant
-- Column: C_BPartner_InterimContract.AD_Client_ID
-- 2023-08-09T16:16:56.693085700Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587229, 719205, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:56.569', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Mandant für diese Installation.', 10, 'D', 'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'Mandant', TO_TIMESTAMP('2023-08-09 18:16:56.569', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:56.696230900Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719205
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:56.699393300Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-08-09T16:16:58.556882900Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719205
;

-- 2023-08-09T16:16:58.559353100Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719205)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Organisation
-- Column: C_BPartner_InterimContract.AD_Org_ID
-- 2023-08-09T16:16:58.657859100Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587230, 719206, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:58.577', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Organisatorische Einheit des Mandanten', 10, 'D', 'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Organisation',
        TO_TIMESTAMP('2023-08-09 18:16:58.577', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:58.658920700Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719206
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:58.660499300Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-08-09T16:16:59.208288500Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719206
;

-- 2023-08-09T16:16:59.208804100Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719206)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Aktiv
-- Column: C_BPartner_InterimContract.IsActive
-- 2023-08-09T16:16:59.312031Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587233, 719207, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:59.21', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Der Eintrag ist im System aktiv', 1, 'D',
        'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Aktiv', TO_TIMESTAMP('2023-08-09 18:16:59.21', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:59.313098100Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719207
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:59.314672700Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-08-09T16:16:59.597838500Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719207
;

-- 2023-08-09T16:16:59.598904600Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719207)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Vorfinanzierungsvertrag
-- Column: C_BPartner_InterimContract.C_BPartner_InterimContract_ID
-- 2023-08-09T16:16:59.686152100Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587236, 719208, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:59.601', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', 10, 'D', 'Diese Einstellungen dienen als Grundlage für die Erzeugung von Vorfinanzierungsverträgen für den gewählten Geschäftspartner.', 'Y', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'Vorfinanzierungsvertrag', TO_TIMESTAMP('2023-08-09 18:16:59.601', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:59.687191Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719208
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:59.688829400Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582617)
;

-- 2023-08-09T16:16:59.692519300Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719208
;

-- 2023-08-09T16:16:59.693034900Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719208)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Vorfinanzierungsvertrag
-- Column: C_BPartner_InterimContract.IsInterimContract
-- 2023-08-09T16:16:59.776693600Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587237, 719209, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:59.695', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 1, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Vorfinanzierungsvertrag', TO_TIMESTAMP('2023-08-09 18:16:59.695', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:59.777752500Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719209
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:59.779359600Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582618)
;

-- 2023-08-09T16:16:59.782605500Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719209
;

-- 2023-08-09T16:16:59.783132600Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719209)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Geschäftspartner
-- Column: C_BPartner_InterimContract.C_BPartner_ID
-- 2023-08-09T16:16:59.862520500Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587238, 719210, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:59.785', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Bezeichnet einen Geschäftspartner', 10, 'D', 'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Geschäftspartner',
        TO_TIMESTAMP('2023-08-09 18:16:59.785', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:59.863611600Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719210
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:16:59.865153600Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2023-08-09T16:16:59.915623700Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719210
;

-- 2023-08-09T16:16:59.916656900Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719210)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Erntekalender
-- Column: C_BPartner_InterimContract.C_Harvesting_Calendar_ID
-- 2023-08-09T16:16:59.998554500Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587239, 719211, 0, 547145, TO_TIMESTAMP('2023-08-09 18:16:59.919', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Erntekalender', TO_TIMESTAMP('2023-08-09 18:16:59.919', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:16:59.999638200Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719211
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:17:00.001276100Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(581157)
;

-- 2023-08-09T16:17:00.006011Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719211
;

-- 2023-08-09T16:17:00.007074600Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719211)
;

-- Field: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> Erntejahr
-- Column: C_BPartner_InterimContract.Harvesting_Year_ID
-- 2023-08-09T16:17:00.096685500Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587240, 719212, 0, 547145, TO_TIMESTAMP('2023-08-09 18:17:00.009', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Erntejahr', TO_TIMESTAMP('2023-08-09 18:17:00.009', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-09T16:17:00.097751100Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 719212
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-08-09T16:17:00.099353Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2023-08-09T16:17:00.103080300Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 719212
;

-- 2023-08-09T16:17:00.103609600Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(719212)
;

-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D)
-- UI Section: default
-- 2023-08-09T16:18:50.116585200Z
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 547145, 545740, TO_TIMESTAMP('2023-08-09 18:18:50.01', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 10, TO_TIMESTAMP('2023-08-09 18:18:50.01', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'default')
;

-- 2023-08-09T16:18:50.117650800Z
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_UI_Section_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_UI_Section t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_UI_Section_ID = 545740
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default
-- UI Column: 10
-- 2023-08-09T16:19:01.053893500Z
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 547000, 545740, TO_TIMESTAMP('2023-08-09 18:19:00.947', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 10, TO_TIMESTAMP('2023-08-09 18:19:00.947', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Column: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10
-- UI Element Group: default
-- 2023-08-09T16:19:14.981457100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 547000, 551013, TO_TIMESTAMP('2023-08-09 18:19:14.867', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'default', 10, TO_TIMESTAMP('2023-08-09 18:19:14.867', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Erntekalender
-- Column: C_BPartner_InterimContract.C_Harvesting_Calendar_ID
-- 2023-08-09T16:20:24.876424800Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 719211, 0, 547145, 551013, 619685, 'F', TO_TIMESTAMP('2023-08-09 18:20:24.749', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Erntekalender', 10, 0, 0, TO_TIMESTAMP('2023-08-09 18:20:24.749', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Erntejahr
-- Column: C_BPartner_InterimContract.Harvesting_Year_ID
-- 2023-08-09T16:20:54.748736500Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 719212, 0, 547145, 551013, 619686, 'F', TO_TIMESTAMP('2023-08-09 18:20:54.634', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Erntejahr', 20, 0, 0, TO_TIMESTAMP('2023-08-09 18:20:54.634', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Vorfinanzierungsvertrag
-- Column: C_BPartner_InterimContract.IsInterimContract
-- 2023-08-09T16:23:14.345976300Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 719209, 0, 547145, 551013, 619688, 'F', TO_TIMESTAMP('2023-08-09 18:23:14.232', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Vorfinanzierungsvertrag', 30, 0, 0, TO_TIMESTAMP('2023-08-09 18:23:14.232', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D) -> default -> 10 -> default.Organisation
-- Column: C_BPartner_InterimContract.AD_Org_ID
-- 2023-08-09T16:23:50.618475300Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 719206, 0, 547145, 551013, 619689, 'F', TO_TIMESTAMP('2023-08-09 18:23:50.493', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Organisatorische Einheit des Mandanten', 'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.', 'Y', 'N', 'Y', 'N', 'N', 'Organisation', 40, 0, 0,
        TO_TIMESTAMP('2023-08-09 18:23:50.493', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag
-- Table: C_BPartner_InterimContract
-- 2023-08-09T16:48:40.270066200Z
UPDATE AD_Tab
SET SeqNo=165, Updated=TO_TIMESTAMP('2023-08-09 18:48:40.27', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Tab_ID = 547145
;

-- Value: C_Flatrate_Term_CreateInterimContract
-- Classname: de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create
-- 2023-08-09T16:54:11.047161800Z
UPDATE AD_Process
SET Classname='de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create', Updated=TO_TIMESTAMP('2023-08-09 18:54:11.045', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Process_ID = 585083
;

-- Process: C_Flatrate_Term_CreateInterimContract(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create)
-- ParameterName: C_Flatrate_Conditions_ID
-- 2023-08-09T16:54:24.252063600Z
DELETE
FROM AD_Process_Para_Trl
WHERE AD_Process_Para_ID = 542280
;

-- 2023-08-09T16:54:24.263564900Z
DELETE
FROM AD_Process_Para
WHERE AD_Process_Para_ID = 542280
;

-- Value: C_BPartner_InterimContract_Create
-- Classname: de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create
-- 2023-08-09T16:56:26.507162700Z
UPDATE AD_Process
SET Value='C_BPartner_InterimContract_Create', Updated=TO_TIMESTAMP('2023-08-09 18:56:26.505', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Process_ID = 585083
;

-- Process: C_BPartner_InterimContract_Create(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create)
-- Table: C_OrderLine
-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D)
-- Window: Geschäftspartner_OLD(123,D)
-- EntityType: D
-- 2023-08-09T16:59:40.889022200Z
DELETE
FROM AD_Table_Process
WHERE AD_Table_Process_ID = 541136
;

-- Process: C_BPartner_InterimContract_Create(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create)
-- Table: C_BPartner_InterimContract
-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag(547145,D)
-- Window: Geschäftspartner_OLD(123,D)
-- EntityType: D
-- 2023-08-09T17:00:45.208189Z
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Tab_ID, AD_Table_ID, AD_Table_Process_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy, WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (0, 0, 585083, 547145, 542357, 541406, 123, TO_TIMESTAMP('2023-08-09 19:00:45.097', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', TO_TIMESTAMP('2023-08-09 19:00:45.097', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'Y', 'N', 'N')
;

-- Process: C_BPartner_InterimContract_Create_old(de.metas.contracts.modular.interim.invoice.process.C_BPartner_InterimContract_Create)
-- Table: C_BPartner_InterimContract
-- EntityType: D
-- 2023-08-09T18:21:05.306795600Z
UPDATE AD_Table_Process
SET AD_Tab_ID=NULL, AD_Window_ID=NULL, Updated=TO_TIMESTAMP('2023-08-09 20:21:05.306', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Table_Process_ID = 541406
;

-- Tab: Geschäftspartner_OLD(123,D) -> Vorfinanzierungsvertrag
-- Table: C_BPartner_InterimContract
-- 2023-08-09T18:14:46.257710700Z
UPDATE AD_Tab
SET IsActive='N', Updated=TO_TIMESTAMP('2023-08-09 20:14:46.256', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Tab_ID = 547145
;

