-- nShift: add IsPreAdviceRequired flag to C_BPartner_Location
-- IDs allocated from idserver.metas.de:
--   AD_Element_ID:     584937 (shared element for all three tables)
--   AD_Column_ID:      592702 (C_BPartner_Location.IsPreAdviceRequired)
--   AD_Field_ID:       780679 (Tab 222 BPartner Location)
--   AD_UI_Element_ID:  651979 (Tab 222)

-- =============================================================================
-- 1. AD_Element for IsPreAdviceRequired
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (584937 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-06-03 10:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'IsPreAdviceRequired',
        'Voranmeldung erforderlich',
        'Voranmeldung erforderlich',
        NULL, NULL,
        'D');

-- 2026-06-03T10:00:01.000Z
-- Skeleton Trl rows for IsPreAdviceRequired element
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584937 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- 2026-06-03T10:00:02.000Z
-- English translation
UPDATE AD_Element_Trl
SET Name         = 'Pre-Advice Required',
    PrintName    = 'Pre-Advice Required',
    Description  = NULL,
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-03 10:00:02', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584937 /*From ID Server*/ AND AD_Language = 'en_US';

-- 2026-06-03T10:00:03.000Z
-- de_DE translation (same as base)
UPDATE AD_Element_Trl
SET Name         = 'Voranmeldung erforderlich',
    PrintName    = 'Voranmeldung erforderlich',
    Description  = NULL,
    Help         = NULL,
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-03 10:00:03', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584937 /*From ID Server*/ AND AD_Language = 'de_DE';

-- 2026-06-03T10:00:04.000Z
-- de_CH translation (same as base)
UPDATE AD_Element_Trl
SET Name         = 'Voranmeldung erforderlich',
    PrintName    = 'Voranmeldung erforderlich',
    Description  = NULL,
    Help         = NULL,
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-06-03 10:00:04', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 584937 /*From ID Server*/ AND AD_Language = 'de_CH';

-- =============================================================================
-- 2. AD_Column for C_BPartner_Location.IsPreAdviceRequired
-- AD_Table_ID=293 (C_BPartner_Location), AD_Reference_ID=17 (List), AD_Reference_Value_ID=319 (YesNoNull)
-- Nullable: null = not configured at this level (fall through to BPartner)
-- =============================================================================
-- 2026-06-03T10:00:05.000Z
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       EntityType, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                       IsAllowLogging, IsLazyLoading, IsExcludeFromZoomTargets,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592702 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-03 10:00:05', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-06-03 10:00:05', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        0, 293 /*C_BPartner_Location*/, 584937 /*From ID Server*/, 17 /*List*/, 319 /*YesNoNull*/,
        'IsPreAdviceRequired', 'Voranmeldung erforderlich', NULL, NULL,
        1, 'N', 'Y', 'N',
        'D', 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y', 'Y', 'Y',
        'N', 'NP');

-- 2026-06-03T10:00:07.000Z
-- Skeleton Trl rows for the column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 592702 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ select update_Column_Translation_From_AD_Element(584937 /*From ID Server*/);

-- =============================================================================
-- 3. Physical column DDL
-- =============================================================================
-- 2026-06-03T10:00:08.000Z
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS IsPreAdviceRequired CHAR(1);
ALTER TABLE C_BPartner_Location ADD CONSTRAINT IsPreAdviceRequired_check CHECK (IsPreAdviceRequired IN ('Y','N'));

-- =============================================================================
-- 4. AD_Field in Tab 222 (BPartner Location, standard BPartner window)
-- =============================================================================
-- 2026-06-03T10:00:11.000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592702 /*From ID Server*/, 780679 /*From ID Server*/, 0, 222,
        TO_TIMESTAMP('2026-06-03 10:00:11', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100, NULL,
        1, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-06-03 10:00:11', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- 2026-06-03T10:00:12.000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 780679 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 2026-06-03T10:00:17.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937 /*From ID Server*/)
;

-- 2026-06-03T10:00:18.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780679 /*From ID Server*/;
-- 2026-06-03T10:00:19.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780679 /*From ID Server*/);

-- =============================================================================
-- 5. AD_UI_Element in Tab 222 (IsAdvancedField='Y')
-- AD_UI_ElementGroup_ID=1000034, SeqNo=200 (after Attention at 190)
-- =============================================================================
-- 2026-06-03T10:00:24.000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780679 /*From ID Server*/, 0, 222, 1000034, 651979 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:00:24', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'Voranmeldung erforderlich', 200, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:24', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
