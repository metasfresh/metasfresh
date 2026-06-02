-- nShift service levels: add M_Shipper_ServiceLevel_Config tab, fields, and UI elements to M_Shipper window (142)

-- DB sequence for M_Shipper_ServiceLevel_Config
CREATE SEQUENCE IF NOT EXISTS M_SHIPPER_SERVICELEVEL_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

-- AD_Sequence registration
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy,
                         CurrentNext, CurrentNextSys, Description, IncrementNo,
                         IsActive, IsAudited, IsAutoSequence, IsTableID, Name, StartNo,
                         Updated, UpdatedBy)
SELECT 0, 0, nextval('AD_Sequence_seq'),
       TO_TIMESTAMP('2026-05-26 14:10:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       1000000, 50000, 'Table M_Shipper_ServiceLevel_Config', 1,
       'Y', 'N', 'Y', 'Y', 'M_Shipper_ServiceLevel_Config', 1000000,
       TO_TIMESTAMP('2026-05-26 14:10:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100
WHERE NOT EXISTS (SELECT 1 FROM AD_Sequence WHERE Name = 'M_Shipper_ServiceLevel_Config');

-- Tab: Lieferweg(142,D) -> Service Level Konfiguration
INSERT INTO AD_Tab (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
                    AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields,
                    IncludedTabNewRecordInputMode, InternalName,
                    IsActive, IsAdvancedTab, IsAutodetectDefaultDateFilter, IsCheckParentsChanged,
                    IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad,
                    IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents,
                    IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab,
                    MaxQueryRecords, Name, Parent_Column_ID, Processing, SeqNo, TabLevel,
                    Updated, UpdatedBy)
VALUES (0, 592634, 584917, 0, 549282 /*From ID Server*/, 542606, 142,
        'Y',
        TO_TIMESTAMP('2026-05-26 14:11:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'D', 'N', 'N',
        'A', 'M_Shipper_ServiceLevel_Config',
        'Y', 'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'Y',
        'N', 'N', 'N',
        'Y', 'Y', 'N', 'N', 'N',
        0, 'Service Level Konfiguration', 2077, 'N', 90, 1,
        TO_TIMESTAMP('2026-05-26 14:11:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name,
                        NotFound_Message, NotFound_MessageDetail,
                        QuickInput_CloseButton_Caption, QuickInput_OpenButton_Caption,
                        IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.NotFound_Message, t.NotFound_MessageDetail,
       t.QuickInput_CloseButton_Caption, t.QuickInput_OpenButton_Caption,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Tab_ID = 549282
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID);

SELECT update_tab_translation_from_ad_element(584917);

SELECT AD_Element_Link_Create_Missing_Tab(549282);

-- Field: SeqNo
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType, Help,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592635, 780489 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-05-26 14:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',
        22, 'D', '"Reihenfolge" bestimmt die Reihenfolge der Einträge',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Reihenfolge',
        TO_TIMESTAMP('2026-05-26 14:12:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780489
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(566);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780489;
SELECT AD_Element_Link_Create_Missing_Field(780489);

-- Field: External_System_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592636, 780490 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-05-26 14:12:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 10, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Externes System',
        TO_TIMESTAMP('2026-05-26 14:12:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780490
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(583968);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780490;
SELECT AD_Element_Link_Create_Missing_Field(780490);

-- Field: ServiceLevel
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592637, 780491 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-05-26 14:12:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 60, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Service Level',
        TO_TIMESTAMP('2026-05-26 14:12:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780491
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(584123);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780491;
SELECT AD_Element_Link_Create_Missing_Field(780491);

-- Field: IsActive
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType, Help,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592629, 780492 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-05-26 14:12:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Der Eintrag ist im System aktiv', 1, 'D',
        'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Aktiv',
        TO_TIMESTAMP('2026-05-26 14:12:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780492
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(348);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780492;
SELECT AD_Element_Link_Create_Missing_Field(780492);

-- Field: AD_Org_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592628, 580485 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-05-26 14:12:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 10, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Sektion',
        TO_TIMESTAMP('2026-05-26 14:12:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 580485
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(113);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 580485;
SELECT AD_Element_Link_Create_Missing_Field(580485);

-- UI Section
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID,
                           Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549282, 547801 /*From ID Server*/,
        TO_TIMESTAMP('2026-05-26 14:13:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Y', 'main', 10,
        TO_TIMESTAMP('2026-05-26 14:13:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'main');

INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name,
                               IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_UI_Section_ID = 547801
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID);

-- UI Column
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID,
                          Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549527 /*From ID Server*/, 547801,
        TO_TIMESTAMP('2026-05-26 14:13:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Y', 10,
        TO_TIMESTAMP('2026-05-26 14:13:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- UI ElementGroup
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID,
                                Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549527, 555400 /*From ID Server*/,
        TO_TIMESTAMP('2026-05-26 14:13:20.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Y', 'main', 10,
        TO_TIMESTAMP('2026-05-26 14:13:20.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- UI Elements
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, Description, Help,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780489, 0, 549282, 555400, 651846 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-05-26 14:14:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',
        '"Reihenfolge" bestimmt die Reihenfolge der Einträge',
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Reihenfolge', 10, 0, 0,
        TO_TIMESTAMP('2026-05-26 14:14:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780490, 0, 549282, 555400, 651847 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-05-26 14:14:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Externes System', 20, 0, 0,
        TO_TIMESTAMP('2026-05-26 14:14:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780491, 0, 549282, 555400, 651848 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-05-26 14:14:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Service Level', 30, 0, 0,
        TO_TIMESTAMP('2026-05-26 14:14:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy, Description, Help,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780492, 0, 549282, 555400, 651849 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-05-26 14:14:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Der Eintrag ist im System aktiv',
        'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.',
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Aktiv', 40, 0, 0,
        TO_TIMESTAMP('2026-05-26 14:14:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- Set grid display for all 4 elements
UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 10,
    Updated = TO_TIMESTAMP('2026-05-26 14:15:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 651846;

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 20,
    Updated = TO_TIMESTAMP('2026-05-26 14:15:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 651847;

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 30,
    Updated = TO_TIMESTAMP('2026-05-26 14:15:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 651848;

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 40,
    Updated = TO_TIMESTAMP('2026-05-26 14:15:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 651849;

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 580485, 0, 549282, 555400, 580486 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-05-26 14:14:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Sektion', 50, 0, 0,
        TO_TIMESTAMP('2026-05-26 14:14:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 50,
    Updated = TO_TIMESTAMP('2026-05-26 14:15:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 580486;
