-- nShift: add Attention (z. Hd.) advanced field to BPartner Location tab in Dist-Org windows
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_Field      780664 (tab 540874 "Adresse" in window 540366 "Geschäftspartner Dist-Orgs")
--   AD_Field      780665 (tab 541854 "Adresse" in window 540676 "Organisation Stammdaten")
--   AD_UI_Element 651964 (window 540366)
--   AD_UI_Element 651965 (window 540676)

-- ============================================================
-- Window 540366 "Geschäftspartner Dist-Orgs" → Tab 540874 "Adresse"
-- ============================================================

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592663, 780664 /*From ID Server*/, 0, 540874,
        TO_TIMESTAMP('2026-06-03 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',
        30, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'z. Hd.',
        TO_TIMESTAMP('2026-06-03 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100)
;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780664
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584922)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=780664
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(780664)
;

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780664, 0, 540874, 541147, 651964 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'z. Hd.', 47, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100)
;

-- ============================================================
-- Window 540676 "Organisation Stammdaten" → Tab 541854 "Adresse"
-- ============================================================

INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592663, 780665 /*From ID Server*/, 0, 541854,
        TO_TIMESTAMP('2026-06-03 10:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 'Zu Händen, Türcode oder weitere Pflichtinformationen für das Versandetikett (max. 30 Zeichen)',
        30, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'z. Hd.',
        TO_TIMESTAMP('2026-06-03 10:00:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100)
;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780665
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584922)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=780665
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(780665)
;

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780665, 0, 541854, 542736, 651965 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'z. Hd.', 47, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100)
;
