-- nShift Lieferweg: shipment-schedule window (500221) carrier field changes.
-- (1) Carrier_Product_ID (AD_Field 755020) and Carrier_Goods_Type_ID (AD_Field 755019)
--     are already IsReadOnly='Y' from previous migrations — confirmed by query, no change needed.
-- (2) Add a services Labels widget to the advanced-edit section (AD_UI_ElementGroup 540052),
--     mirroring the Sales Order labels wiring added in 5815670:
--       * hidden labels tab (TabLevel=1) on window 500221 over M_ShipmentSchedule_Carrier_Service (AD_Table 542544)
--       * selector AD_Field on that tab: Carrier_Service_ID (AD_Column 591336), reusing element 584113
--       * Labels UI element (type='L') on the main tab (500221), pointing to the selector field
--         and the hidden tab.
--     The selector field carries NO DisplayLogic (the existing carrier fields on this window
--     have none either — the widget is always visible when the advanced edit is open).
--     Val rule 540757 (Carrier_Service_ID_for_M_Shipper_ID) is set on the bridge column via
--     AD_Column to constrain the lookup to the selected shipper + carrier product.
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_MigrationScript  5815700
--   AD_Element          585127 (tab caption "Lieferweg-Services")
--   AD_Tab              549354 (hidden labels tab on window 500221)
--   AD_Field            781773 (Carrier_Service_ID selector field on the hidden tab)
--   AD_UI_Element       652705 (Labels element on main tab 500221, group 540052)

-- ============================================================================
-- 1) Set val rule on M_ShipmentSchedule_Carrier_Service.Carrier_Service_ID column
--    (AD_Column 591336) so the lookup is constrained to the selected shipper's services.
--    Val rule 540757 = Carrier_Service_ID_for_M_Shipper_ID (uses @M_Shipper_ID@ + @Carrier_Product_ID@).
-- ============================================================================
UPDATE AD_Column
   SET AD_Val_Rule_ID = 540757,
       Updated        = TO_TIMESTAMP('2026-07-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
 WHERE AD_Column_ID = 591336
;

-- ============================================================================
-- 2) Tab-caption element for the hidden labels tab
-- ============================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585127 /*From ID Server*/, 0,
        TO_TIMESTAMP('2026-07-23 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y',
        'Lieferweg-Services', 'Lieferweg-Services',
        TO_TIMESTAMP('2026-07-23 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Element_Trl
       (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM   AD_Language l, AD_Element t
WHERE  l.IsActive = 'Y'
  AND  (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND  t.AD_Element_ID = 585127
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y',
       Name         = 'Carrier Services',
       PrintName    = 'Carrier Services',
       Updated      = TO_TIMESTAMP('2026-07-23 10:01:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 585127
   AND AD_Language   = 'en_US'
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585127, 'en_US')
;

-- ============================================================================
-- 3) Hidden labels tab — TabLevel=1, over M_ShipmentSchedule_Carrier_Service (542544),
--    on window 500221. Framework auto-hides it because a Labels element (type='L') on
--    the main tab references it.
-- ============================================================================
INSERT INTO AD_Tab
       (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID,
        AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName,
        IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly,
        IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate,
        IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab,
        MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585127, 0, 549354 /*From ID Server*/, 542544, 500221,
        'Y',
        TO_TIMESTAMP('2026-07-23 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'N', 'N', 'M_ShipmentSchedule_Carrier_Service',
        'Y', 'N', 'Y', 'N', 'N',
        'N', 'Y', 'Y', 'N', 'N',
        'Y', 'Y', 'N', 'N', 'N',
        0, 'Lieferweg-Services', 'N', 100, 1,
        TO_TIMESTAMP('2026-07-23 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl
       (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM   AD_Language l, AD_Tab t
WHERE  l.IsActive = 'Y'
  AND  (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND  t.AD_Tab_ID = 549354
  AND  NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ SELECT update_tab_translation_from_ad_element(585127)
;

-- ============================================================================
-- 4) Selector field = Carrier_Service_ID on the hidden labels tab (549354).
--    No DisplayLogic (mirrors the always-visible carrier fields on this window).
--    Reuses element 584113 (Lieferweg-Service).
-- ============================================================================
INSERT INTO AD_Field
       (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
        Created, CreatedBy, DisplayLength, EntityType,
        IsActive, IsDisplayed, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
        Name, Updated, UpdatedBy)
VALUES (0, 591336, 781773 /*From ID Server*/, 0, 549354,
        TO_TIMESTAMP('2026-07-23 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N',
        'Lieferweg-Service',
        TO_TIMESTAMP('2026-07-23 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl
       (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM   AD_Language l, AD_Field t
WHERE  l.IsActive = 'Y'
  AND  (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND  t.AD_Field_ID = 781773
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781773
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781773)
;

-- ============================================================================
-- 5) Labels UI element on the main tab (500221), advanced-edit group 540052.
--    Type='L', SeqNo=410 (after Projekt at 400).
--    Labels_Selector_Field_ID = 781773 (the selector field above).
--    Labels_Tab_ID = 549354 (the hidden tab above).
-- ============================================================================
INSERT INTO AD_UI_Element
       (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID,
        AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering,
        IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine,
        Labels_Selector_Field_ID, Labels_Tab_ID,
        MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid,
        Updated, UpdatedBy)
VALUES (0, 0, 500221, 652705 /*From ID Server*/, 540052,
        'L',
        TO_TIMESTAMP('2026-07-23 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Y', 'Y', 'N',
        'Y', 'N', 'N', 'N',
        781773, 549354,
        0, 'Lieferweg-Services', 410, 0, 0,
        TO_TIMESTAMP('2026-07-23 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

SELECT add_missing_translations()
;
