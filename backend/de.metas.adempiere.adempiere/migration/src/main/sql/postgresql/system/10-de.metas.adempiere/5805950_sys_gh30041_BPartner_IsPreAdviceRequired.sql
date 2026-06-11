-- nShift: add IsPreAdviceRequired flag to C_BPartner
-- Reuses AD_Element 584937 (shared with C_BPartner_Location and C_Order)
-- IDs allocated from idserver.metas.de:
--   AD_Column_ID:      592703 (C_BPartner.IsPreAdviceRequired)
--   AD_Field_ID:       780682 (Tab 220 C_BPartner main tab)
--   AD_UI_Element_ID:  651982 (Tab 220)

-- =============================================================================
-- 1. AD_Column for C_BPartner.IsPreAdviceRequired
-- AD_Table_ID=291 (C_BPartner), AD_Reference_ID=17 (List), AD_Reference_Value_ID=319 (YesNoNull)
-- Nullable: null = not configured at this level (fall through to default)
-- =============================================================================
-- 2026-06-03T10:00:27.000Z
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       EntityType, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                       IsAllowLogging, IsLazyLoading, IsExcludeFromZoomTargets,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592703 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-03 10:00:27', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-06-03 10:00:27', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        0, 291 /*C_BPartner*/, 584937 /*From ID Server*/, 17 /*List*/, 319 /*YesNoNull*/,
        'IsPreAdviceRequired', 'Voranmeldung erforderlich', NULL, NULL,
        1, 'N', 'Y', 'N',
        'D', 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y', 'Y', 'Y',
        'N', 'NP');

-- 2026-06-03T10:00:29.000Z
-- Skeleton Trl rows for the column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 592703 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ select update_Column_Translation_From_AD_Element(584937 /*From ID Server*/);

-- =============================================================================
-- 2. Physical column DDL
-- =============================================================================
-- 2026-06-03T10:00:30.000Z
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS IsPreAdviceRequired CHAR(1);
ALTER TABLE C_BPartner ADD CONSTRAINT IsPreAdviceRequired_check CHECK (IsPreAdviceRequired IN ('Y','N'));

-- =============================================================================
-- 3. AD_Field in Tab 220 (C_BPartner main tab)
-- =============================================================================
-- 2026-06-03T10:00:33.000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592703 /*From ID Server*/, 780682 /*From ID Server*/, 0, 220,
        TO_TIMESTAMP('2026-06-03 10:00:33', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100, NULL,
        1, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-06-03 10:00:33', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- 2026-06-03T10:00:34.000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 780682 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 2026-06-03T10:00:35.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937 /*From ID Server*/)
;

-- 2026-06-03T10:00:36.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780682 /*From ID Server*/;

-- 2026-06-03T10:00:37.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780682 /*From ID Server*/);

-- =============================================================================
-- 4. AD_UI_Element in Tab 220 (IsAdvancedField='Y')
-- Use advanced edit group 540671 (same as DeliveryStop fields on tab 220)
-- =============================================================================
-- 2026-06-03T10:00:38.000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780682 /*From ID Server*/, 0, 220, 540671, 651982 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:00:38', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'Voranmeldung erforderlich', 120, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:38', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
