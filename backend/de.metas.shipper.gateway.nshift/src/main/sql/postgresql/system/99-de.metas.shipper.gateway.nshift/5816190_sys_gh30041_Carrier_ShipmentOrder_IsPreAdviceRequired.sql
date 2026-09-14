-- nShift: add IsPreAdviceRequired flag to Carrier_ShipmentOrder (shipment order record)
-- Persists the pre-advice value that was resolved for the order and sent to nShift, so it
-- is visible/traceable on the shipment order. Advanced-edit field in the existing "flags"
-- element group, next to InternationalDelivery.
-- Reuses AD_Element 584937 (shared with C_BPartner / C_BPartner_Location / C_Order / C_BP_Group).
-- IDs allocated from idserver.metas.de on 2026-07-24:
--   AD_Column_ID:      593023 (Carrier_ShipmentOrder.IsPreAdviceRequired)
--   AD_Field_ID:       781844 (Tab 548456 Carrier_ShipmentOrder main tab)
--   AD_UI_Element_ID:  652768 (Tab 548456, flags group 553600, IsAdvancedField='Y')

-- =============================================================================
-- 1. AD_Column for Carrier_ShipmentOrder.IsPreAdviceRequired
-- AD_Table_ID=542532 (Carrier_ShipmentOrder), AD_Reference_ID=20 (Yes-No)
-- Boolean flag mirroring the sibling InternationalDelivery/IsActive flags on this table.
-- =============================================================================
-- 2026-07-24T10:00:00.000Z
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, DefaultValue, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       EntityType, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                       IsAllowLogging, IsLazyLoading, IsExcludeFromZoomTargets,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (593023 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-24 10:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-24 10:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        0, 542532 /*Carrier_ShipmentOrder*/, 584937 /*From ID Server*/, 20 /*YesNo*/,
        'IsPreAdviceRequired', 'Voranmeldung erforderlich', NULL, NULL,
        1, 'N', 'Y', 'Y', 'N',
        'D', 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y', 'N', 'Y',
        'N', 'NP');

-- 2026-07-24T10:00:01.000Z
-- Skeleton Trl rows for the column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593023 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ select update_Column_Translation_From_AD_Element(584937 /*From ID Server*/);

-- =============================================================================
-- 2. Physical column DDL
-- =============================================================================
-- 2026-07-24T10:00:02.000Z
-- IsMandatory='Y' in AD_Column -> physical column NOT NULL (DEFAULT 'N' backfills existing rows),
-- matching sibling boolean columns IsActive / InternationalDelivery on this table.
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder',
  'ALTER TABLE public.Carrier_ShipmentOrder ADD COLUMN IF NOT EXISTS IsPreAdviceRequired CHAR(1) DEFAULT ''N'' CHECK (IsPreAdviceRequired IN (''Y'',''N'')) NOT NULL');

-- =============================================================================
-- 3. AD_Field in Tab 548456 (Carrier_ShipmentOrder main tab)
-- =============================================================================
-- 2026-07-24T10:00:03.000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593023 /*From ID Server*/, 781844 /*From ID Server*/, 0, 548456,
        TO_TIMESTAMP('2026-07-24 10:00:03', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100, NULL, 1, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Voranmeldung erforderlich',
        TO_TIMESTAMP('2026-07-24 10:00:03', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- 2026-07-24T10:00:04.000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 781844 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 2026-07-24T10:00:05.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937 /*From ID Server*/)
;

-- 2026-07-24T10:00:06.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781844 /*From ID Server*/;

-- 2026-07-24T10:00:07.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781844 /*From ID Server*/);

-- =============================================================================
-- 4. AD_UI_Element in Tab 548456, flags group 553600 (IsAdvancedField='Y')
-- Advanced-edit only, next to InternationalDelivery (SeqNo 20) -> SeqNo 30.
-- =============================================================================
-- 2026-07-24T10:00:08.000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781844 /*From ID Server*/, 0, 548456, 553600, 652768 /*From ID Server*/,
        TO_TIMESTAMP('2026-07-24 10:00:08', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'Voranmeldung erforderlich', 30, 0, 0,
        TO_TIMESTAMP('2026-07-24 10:00:08', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
