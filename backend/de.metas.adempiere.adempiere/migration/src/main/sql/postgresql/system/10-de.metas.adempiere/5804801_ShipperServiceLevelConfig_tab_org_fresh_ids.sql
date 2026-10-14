-- Fixes failure of: 5804800_ShipperServiceLevelConfig_tab.sql
-- nShift service levels: re-add the AD_Org field + UI element to the Service Level Konfiguration
-- tab with fresh ID-server IDs. The original IDs in 5804800 (AD_Field 580485 / AD_UI_Element
-- 580486) were not from the ID server and collide with pre-existing rows downstream; they are
-- disabled in 5804800 and removed by 5804799.
--
-- IDs allocated from idserver.metas.de on 2026-06-11:
--   AD_Field      780756 (AD_Org_ID field on the Service Level Konfiguration tab)
--   AD_UI_Element 652051 (its UI element)

-- Field: AD_Org_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592628, 780756 /*From ID Server*/, 0, 549282,
        TO_TIMESTAMP('2026-06-11 14:17:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100, 10, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Sektion',
        TO_TIMESTAMP('2026-06-11 14:17:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name,
                          IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780756
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_FieldTranslation_From_AD_Name_Element(113);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780756;
SELECT AD_Element_Link_Create_Missing_Field(780756);

-- UI Element: AD_Org_ID
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780756, 0, 549282, 555400, 652051 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-06-11 14:17:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0,
        'Sektion', 50, 0, 0,
        TO_TIMESTAMP('2026-06-11 14:17:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 50,
    Updated = TO_TIMESTAMP('2026-06-11 14:17:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 652051;
