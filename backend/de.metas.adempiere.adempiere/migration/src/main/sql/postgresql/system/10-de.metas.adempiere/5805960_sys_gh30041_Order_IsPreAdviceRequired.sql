-- nShift: add IsPreAdviceRequired flag to C_Order
-- Reuses AD_Element 584937 (shared with C_BPartner_Location and C_BPartner)
-- IDs allocated from idserver.metas.de:
--   AD_Column_ID:      592704 (C_Order.IsPreAdviceRequired)
--   AD_Field_ID:       780683 (Tab 186 C_Order)
--   AD_UI_Element_ID:  651983 (Tab 186)

-- =============================================================================
-- 1. AD_Column for C_Order.IsPreAdviceRequired
-- AD_Table_ID=259 (C_Order), AD_Reference_ID=20 (YesNo)
-- =============================================================================
-- 2026-06-03T10:00:39.000Z
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                       IsAllowLogging, IsLazyLoading, IsExcludeFromZoomTargets,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592704 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-03 10:00:39', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-06-03 10:00:39', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        0, 259 /*C_Order*/, 584937 /*From ID Server*/, 20 /*YesNo*/,
        'IsPreAdviceRequired', 'Voranmeldung erforderlich', NULL, NULL,
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y', 'Y', 'Y',
        'N', 'NP');

-- 2026-06-03T10:00:40.000Z
UPDATE AD_Column SET DefaultValue = 'N'
WHERE AD_Column_ID = 592704 /*From ID Server*/;

-- 2026-06-03T10:00:41.000Z
-- Skeleton Trl rows for the column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 592704 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ select update_Column_Translation_From_AD_Element(584937 /*From ID Server*/);

-- =============================================================================
-- 2. Physical column DDL
-- =============================================================================
-- 2026-06-03T10:00:42.000Z
ALTER TABLE C_Order ADD COLUMN IF NOT EXISTS IsPreAdviceRequired CHAR(1) DEFAULT 'N';

-- 2026-06-03T10:00:43.000Z
UPDATE C_Order SET IsPreAdviceRequired = 'N' WHERE IsPreAdviceRequired IS NULL;

-- 2026-06-03T10:00:44.000Z
ALTER TABLE C_Order ALTER COLUMN IsPreAdviceRequired SET NOT NULL;

-- =============================================================================
-- 3. AD_Field in Tab 186 (C_Order)
-- =============================================================================
-- 2026-06-03T10:00:45.000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 592704 /*From ID Server*/, 780683 /*From ID Server*/, 0, 186,
        TO_TIMESTAMP('2026-06-03 10:00:45', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100, NULL,
        1, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-06-03 10:00:45', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- 2026-06-03T10:00:46.000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 780683 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 2026-06-03T10:00:47.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937 /*From ID Server*/)
;

-- 2026-06-03T10:00:48.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780683 /*From ID Server*/;

-- 2026-06-03T10:00:49.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780683 /*From ID Server*/);

-- =============================================================================
-- 4. AD_UI_Element in Tab 186 (IsAdvancedField='Y')
-- Use advanced edit group 540499 (same group used for IsAutoInvoice on tab 186)
-- =============================================================================
-- 2026-06-03T10:00:50.000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 780683 /*From ID Server*/, 0, 186, 540499, 651983 /*From ID Server*/,
        TO_TIMESTAMP('2026-06-03 10:00:50', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'Voranmeldung erforderlich', 490, 0, 0,
        TO_TIMESTAMP('2026-06-03 10:00:50', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
