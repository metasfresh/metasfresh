-- me03#29295: Window design overhaul for window 540150 / tab 541052 (Shipment Line Attribute Config)
--
-- Creates WebUI layout for the included record tab:
-- - 1 UI section, 1 column, 1 element group (per design guide for included tabs)
-- - Grid view: Attribute, OnlyIfInReferencedASI, IsHUAttributeOverridesASI, Active, Org
-- - Org last in grid, Client not in grid (per design guide)

-- Also fix tab name: rename from "HU-Aggregation" to proper names
UPDATE AD_Tab SET Name = 'Shipment Line Attribute Config', Updated = '2026-04-17 02:00', UpdatedBy = 0 WHERE AD_Tab_ID = 541052;
UPDATE AD_Tab_Trl SET Name = 'Shipment Line Attribute Config', IsTranslated = 'Y', Updated = '2026-04-17 02:00', UpdatedBy = 0
WHERE AD_Tab_ID = 541052 AND AD_Language = 'en_US';
UPDATE AD_Tab_Trl SET Name = 'Lieferzeile Merkmalkonfiguration', IsTranslated = 'N', Updated = '2026-04-17 02:00', UpdatedBy = 0
WHERE AD_Tab_ID = 541052 AND AD_Language = 'de_DE';
UPDATE AD_Tab_Trl SET Name = 'Lieferzeile Merkmalkonfiguration', IsTranslated = 'N', Updated = '2026-04-17 02:00', UpdatedBy = 0
WHERE AD_Tab_ID = 541052 AND AD_Language = 'de_CH';

-- =============================================================================
-- UI Section
-- =============================================================================
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy, Value)
VALUES (547672 /*From ID Server*/, 0, 0, 541052,
        '2026-04-17 02:00', 0, 'Y', 'main', 10, '2026-04-17 02:00', 0, 'main');

-- =============================================================================
-- UI Column (only 1 for included tabs)
-- =============================================================================
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, AD_UI_Section_ID,
                          Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (549366 /*From ID Server*/, 0, 0, 547672,
        '2026-04-17 02:00', 0, 'Y', 10, '2026-04-17 02:00', 0);

-- =============================================================================
-- UI Element Group (only 1 for included tabs — white background)
-- =============================================================================
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, AD_UI_Column_ID,
                                Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (555125 /*From ID Server*/, 0, 0, 549366,
        '2026-04-17 02:00', 0, 'Y', 'default', 10, 'primary', '2026-04-17 02:00', 0);

-- =============================================================================
-- UI Elements — Grid view order:
--   Attribute (10), OnlyIfInReferenced (20), HUOverridesASI (30), Active (40), Org (50)
-- =============================================================================

-- Attribute (M_Attribute_ID)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649816 /*From ID Server*/, 0, 0, 541052,
        555125, 563057, 'F',
        '2026-04-17 02:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Merkmal', 10, 10, 0,
        '2026-04-17 02:00', 0);

-- Only if in referenced ASI (OnlyIfInReferencedASI)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649817 /*From ID Server*/, 0, 0, 541052,
        555125, 563058, 'F',
        '2026-04-17 02:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Nur falls in ref. Datensatz', 20, 20, 0,
        '2026-04-17 02:00', 0);

-- HU Attribute Overrides ASI (IsHUAttributeOverridesASI)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649818 /*From ID Server*/, 0, 0, 541052,
        555125, 777075, 'F',
        '2026-04-17 02:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'HU-Merkmal überschreibt Merkmalsatz', 30, 30, 0,
        '2026-04-17 02:00', 0);

-- Active (IsActive)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, AD_Tab_ID,
                           AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
                           Created, CreatedBy, IsActive, IsAdvancedField,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList,
                           Updated, UpdatedBy)
VALUES (649819 /*From ID Server*/, 0, 0, 541052,
        555125, 563054, 'F',
        '2026-04-17 02:00', 0, 'Y', 'N',
        'Y', 'Y', 'N',
        'Aktiv', 40, 40, 0,
        '2026-04-17 02:00', 0);
