-- Order Checkup: place C_Order.IsReprintOrderCheckup in the sales order window's advanced edit.
-- Assumes: AD_Column 593632 (C_Order.IsReprintOrderCheckup) + AD_Element 585474 already exist
-- (see 5824960_sys_OrderCheckup_C_Order_IsReprintOrderCheckup.sql).
-- Target: AD_Window 143 (Auftrag_OLD, core sales order window), header tab 186 (C_Order),
-- advanced-edit element group 540499 (same group used by IsPreAdviceRequired on this tab).
-- IDs allocated from idserver.metas.de:
--   AD_Field_ID:      785041 (Tab 186 C_Order)
--   AD_UI_Element_ID: 654781 (Tab 186)
-- EntityType='D' on the field (matching Task 1's AD_Column): sampling tab 186's other core
-- C_Order fields shows AD_Field.EntityType consistently matches its AD_Column.EntityType
-- (28/30 sampled). AD_UI_Element has no EntityType column at all, so nothing to align there.

-- =============================================================================
-- 1. AD_Field in Tab 186 (C_Order)
-- =============================================================================
-- 2026-09-17T09:00:00.000Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
                      Created, CreatedBy, Description, DisplayLength, EntityType,
                      IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly,
                      IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593632 /*From ID Server (Task 1)*/, 785041 /*From ID Server*/, 0, 186,
        TO_TIMESTAMP('2026-09-17 09:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100, NULL,
        1, 'D',
        'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Bestellkontrolle neu drucken',
        TO_TIMESTAMP('2026-09-17 09:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);

-- 2026-09-17T09:00:01.000Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785041 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- 2026-09-17T09:00:10.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585474 /*From ID Server (Task 1)*/)
;

-- 2026-09-17T09:00:11.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785041 /*From ID Server*/;

-- 2026-09-17T09:00:12.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(785041 /*From ID Server*/);

-- =============================================================================
-- 2. AD_UI_Element in Tab 186 (IsAdvancedField='Y')
-- Use advanced edit group 540499 (same group used for IsPreAdviceRequired on tab 186).
-- Highest existing SeqNo in this group is 520, held by the Labels element 652704
-- ("Lieferweg-Services", AD_UI_ElementType='L', AD_Field_ID IS NULL -- easy to miss with an
-- inner join to AD_Field). Place this field at 530, the first free slot.
-- =============================================================================
-- 2026-09-17T09:00:20.000Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID,
                           Created, CreatedBy,
                           IsActive, IsAdvancedField, IsDisplayed,
                           IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785041 /*From ID Server*/, 0, 186, 540499, 654781 /*From ID Server*/,
        TO_TIMESTAMP('2026-09-17 09:00:20', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'Y', 'Y', 'Y', 'N', 'N',
        'Bestellkontrolle neu drucken', 530, 0, 0,
        TO_TIMESTAMP('2026-09-17 09:00:20', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        100);
