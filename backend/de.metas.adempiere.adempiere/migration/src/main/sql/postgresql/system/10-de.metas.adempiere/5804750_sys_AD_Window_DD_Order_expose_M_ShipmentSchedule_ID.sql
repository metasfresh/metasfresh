-- Expose M_ShipmentSchedule_ID on the Distribution Order window (header + line tabs)
-- DisplayLogic '@M_ShipmentSchedule_ID/0@>0' → field shown only when the value is set (> 0).
-- Used by DD_Order picking reconcile flow (https://github.com/metasfresh/me03/issues/29966).
--
-- IDs allocated from idserver.metas.de on 2026-05-26:
--   AD_Field    780487  (DD_Order header tab, column DD_Order.M_ShipmentSchedule_ID)
--   AD_Field    780488  (DD_OrderLine line tab, column DD_OrderLine.M_ShipmentSchedule_ID)
--   AD_UI_Element 651844 (DD_Order header tab)
--   AD_UI_Element 651845 (DD_OrderLine line tab)
--
-- Window layout (from 5462580_sys_gh1485-distribution-order-window-webui.sql):
--   AD_Tab_ID 53055 = DD_Order header tab,    AD_UI_ElementGroup_ID 540423 ("default", SeqNo=10)
--   AD_Tab_ID 53050 = DD_OrderLine line tab,  AD_UI_ElementGroup_ID 540424 ("default", SeqNo=10)

-- =============================================================================
-- 1. AD_Field on DD_Order header tab (for DD_Order.M_ShipmentSchedule_ID)
-- =============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Column_ID, AD_Tab_ID,
                      Created, CreatedBy, Updated, UpdatedBy, IsActive,
                      EntityType, Name,
                      DisplayLength, DisplayLogic,
                      IsDisplayed, IsDisplayedGrid,
                      IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine)
VALUES (0, 0, 780487,
        (SELECT AD_Column_ID FROM AD_Column
          WHERE ColumnName = 'M_ShipmentSchedule_ID'
            AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DD_Order')),
        53055,
        TO_TIMESTAMP('2026-05-26 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'D', 'Lieferdisposition',
        10, '@M_ShipmentSchedule_ID/0@>0',
        'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N')
;

-- Skeleton Trl rows for the header field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780487
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- Propagate element translations
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_ShipmentSchedule_ID')
)
;

-- Wire up element-link for header field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780487
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780487)
;

-- =============================================================================
-- 2. AD_UI_Element on DD_Order header tab
-- =============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID,
                           AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive,
                           Name,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES (0, 0, 651844,
        780487,
        53055,
        540423,
        'F',
        TO_TIMESTAMP('2026-05-26 15:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 15:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'Lieferdisposition',
        'Y',
        'Y', 'Y', 'N',
        990, 990, 0)
;

-- =============================================================================
-- 3. AD_Field on DD_OrderLine line tab (for DD_OrderLine.M_ShipmentSchedule_ID)
-- =============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Column_ID, AD_Tab_ID,
                      Created, CreatedBy, Updated, UpdatedBy, IsActive,
                      EntityType, Name,
                      DisplayLength, DisplayLogic,
                      IsDisplayed, IsDisplayedGrid,
                      IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine)
VALUES (0, 0, 780488,
        (SELECT AD_Column_ID FROM AD_Column
          WHERE ColumnName = 'M_ShipmentSchedule_ID'
            AND AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DD_OrderLine')),
        53050,
        TO_TIMESTAMP('2026-05-26 15:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 15:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'D', 'Lieferdisposition',
        10, '@M_ShipmentSchedule_ID/0@>0',
        'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N')
;

-- Skeleton Trl rows for the line field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 780488
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- (No second propagation call — same AD_Element as header field above; already propagated.)

-- Wire up element-link for line field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780488
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780488)
;

-- =============================================================================
-- 4. AD_UI_Element on DD_OrderLine line tab
-- =============================================================================
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_UI_Element_ID,
                           AD_Field_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive,
                           Name,
                           IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           SeqNo, SeqNoGrid, SeqNo_SideList)
VALUES (0, 0, 651845,
        780488,
        53050,
        540424,
        'F',
        TO_TIMESTAMP('2026-05-26 15:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 15:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'Lieferdisposition',
        'Y',
        'Y', 'Y', 'N',
        990, 990, 0)
;
