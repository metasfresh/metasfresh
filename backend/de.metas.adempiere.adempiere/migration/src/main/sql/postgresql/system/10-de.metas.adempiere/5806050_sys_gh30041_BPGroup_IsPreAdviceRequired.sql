-- nShift Pre-Advice: add IsPreAdviceRequired (YesNoNull) to C_BP_Group
-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_Column     592705
--   AD_Field      780684  (Tab 322 "Geschäftspartnergruppe")
--   AD_UI_Element 651984  (AD_UI_ElementGroup_ID=540481, SeqNo=100, after IsAutoInvoice at 90)

-- Column: C_BP_Group.IsPreAdviceRequired
-- AD_Table_ID=394 (C_BP_Group), AD_Reference_ID=17 (List), AD_Reference_Value_ID=319 (YesNoNull)
-- Nullable: null = not configured at this level
-- 2026-06-03T10:00:00.000000Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID,
                       AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy,
                       EntityType, FieldLength, IsActive,
                       IsAllowLogging, IsExcludeFromZoomTargets, IsLazyLoading,
                       IsMandatory, IsParent, IsKey, IsTranslated,
                       IsUpdateable, Name, Updated, UpdatedBy, Version,
                       PersonalDataCategory)
VALUES (0, 592705 /*From ID Server*/, 584937 /*From ID Server*/, 0,
        17 /*List*/, 319 /*YesNoNull*/, 394 /*C_BP_Group*/,
        'IsPreAdviceRequired',
        TO_TIMESTAMP('2026-06-03 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'D', 1, 'Y',
        'Y', 'Y', 'Y',
        'N', 'N', 'N', 'N',
        'Y', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-06-03 10:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0,
        'NP')
;

-- 2026-06-03T10:00:01.000000Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592705 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-06-03T10:00:02.000000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584937 /*From ID Server*/)
;

-- 2026-06-03T10:00:03.000000Z
ALTER TABLE C_BP_Group ADD COLUMN IF NOT EXISTS IsPreAdviceRequired CHAR(1);
ALTER TABLE C_BP_Group ADD CONSTRAINT IsPreAdviceRequired_check CHECK (IsPreAdviceRequired IN ('Y','N'));


-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Voranmeldung erforderlich
-- 2026-06-03T10:00:04.000000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592705 /*From ID Server*/, 780684 /*From ID Server*/, 0, 322 /*Geschäftspartnergruppe tab*/,
        TO_TIMESTAMP('2026-06-03 10:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        1, 'D',
        'Y', 'Y', 'N', 'N', 'N',
        'N', 'N', 'N', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-06-03 10:00:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-06-03T10:00:05.000000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780684 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-06-03T10:00:06.000000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937 /*From ID Server*/)
;

-- 2026-06-03T10:00:07.000000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780684 /*From ID Server*/
;

-- 2026-06-03T10:00:08.000000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780684 /*From ID Server*/)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> Voranmeldung erforderlich
-- SeqNo=100 (after IsAutoInvoice at SeqNo=90)
-- 2026-06-03T10:00:09.000000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           AD_UI_ElementType, Created, CreatedBy,
                           IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780684 /*From ID Server*/, 0, 322, 540481, 651984 /*From ID Server*/,
        'F',
        TO_TIMESTAMP('2026-06-03 10:00:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Y', 'N', 'N', 'Y',
        'N', 'N', 'N', 0,
        'Voranmeldung erforderlich', 100, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
