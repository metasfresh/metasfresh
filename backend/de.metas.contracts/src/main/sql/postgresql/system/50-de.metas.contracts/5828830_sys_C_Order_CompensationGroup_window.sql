-- Order compensation groups: read-only window "Auftrag Kompensationsgruppe" / "Order Compensation Group".
--
-- C_Order_CompensationGroup (AD_Table_ID=540856) had no window, so the compensation group field of an order line
-- could not be zoomed into. This creates a read-only window over that table:
--   * header tab: the group with its order, contract, schema, product category, BOM, activity; no insert/delete;
--     default sort: order descending, then name; filters: name, order, contract, schema, organisation;
--   * sub-tab "Auftragsposition" / "Order Line": the order lines of the group (read-only), sorted by line no.
-- The table's AD_Window_ID is set, so the group field of an order line zooms into this window.
-- The contract column is opened up as zoom target, so a contract's Related Documents lists its groups.
-- The menu entry is created by the follow-up menu script.
--
-- Captions reuse existing elements without changing them:
--   window / header tab : AD_Element 543469 (C_Order_CompensationGroup_ID) "Auftrag Kompensationsgruppe" / "Order Compensation Group"
--   sub-tab             : AD_Element 572851 "Auftragsposition" / "Order Line" (also the sales order's line tab)
--   contract field      : AD_Element 574343 "Vertrag" / "Contract" (AD_Name_ID; no other usage)
--
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript 5828830 (this script)
--   AD_Window 542196; AD_Tab 549517 (header), 549518 (order lines)
--   AD_UI_Section 548009 (header), 548010 (order lines)
--   AD_UI_Column 549781 (header left), 549782 (header right), 549783 (order lines)
--   AD_UI_ElementGroup 555818 (header left primary), 555819 (header left), 555820 (header right "flags"),
--                      555821 (header right "org"), 555822 (order lines primary)
--   AD_Field 785661..785671 (header), 785672..785680 (order lines), 785681 (order lines, hidden link column)
--   AD_UI_Element 654990..655000 (header), 655001..655009 (order lines)

-- ============================================================================
-- 1) AD_Window
-- ============================================================================
INSERT INTO AD_Window (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsBetaFunctionality, IsDefault, IsEnableRemoteCacheInvalidation, IsOneInstanceOnly, IsSOTrx, Name, Processing, Updated, UpdatedBy, WindowType, WinHeight, WinWidth)
VALUES (0, 543469, 0, 542196 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Auftrag Kompensationsgruppe', 'N', TO_TIMESTAMP('2026-10-09 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'M', 0, 0)
;
INSERT INTO AD_Window_Trl (AD_Language, AD_Window_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Window_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Window t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Window_ID = 542196
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Window_ID = t.AD_Window_ID)
;
/* DDL */ select update_window_translation_from_ad_element(543469)
;
DELETE FROM AD_Element_Link WHERE AD_Window_ID = 542196
;
/* DDL */ select AD_Element_Link_Create_Missing_Window(542196)
;
-- Zoom target of FK fields referencing C_Order_CompensationGroup (e.g. C_OrderLine.C_Order_CompensationGroup_ID)
UPDATE AD_Table
SET AD_Window_ID = 542196, Updated = TO_TIMESTAMP('2026-10-09 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 540856 /* C_Order_CompensationGroup */
;
-- ============================================================================
-- 2) AD_Tab: header -- TabLevel=0, read-only, no insert
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Parent_Column_ID, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, NULL, 543469, 0, 549517 /*From ID Server*/, 540856, 542196, 'N', TO_TIMESTAMP('2026-10-09 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'N', 'N', 'C_Order_CompensationGroup', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Auftrag Kompensationsgruppe', NULL, 'N', 10, 0, TO_TIMESTAMP('2026-10-09 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549517
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(543469)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549517
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549517)
;
-- ============================================================================
-- 3) AD_Tab: Auftragsposition / Order Line -- TabLevel=1, read-only, linked by C_OrderLine.C_Order_CompensationGroup_ID
-- ============================================================================
-- The WebUI takes the child link column from AD_Tab.AD_Column_ID (else from the child's IsParent column, which for
-- C_OrderLine is C_Order_ID) and the parent's from Parent_Column_ID (else the parent key). AD_Column_ID needs a field
-- on the tab: the hidden field 785681 below.
INSERT INTO AD_Tab (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Parent_Column_ID, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 557744 /* C_OrderLine.C_Order_CompensationGroup_ID */, 572851, 0, 549518 /*From ID Server*/, 260, 542196, 'N', TO_TIMESTAMP('2026-10-09 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'N', 'N', 'C_OrderLine', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 0, 'Auftragsposition', NULL, 'N', 20, 1, TO_TIMESTAMP('2026-10-09 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549518
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(572851)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549518
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549518)
;
-- ============================================================================
-- 4) Navigation + filters on C_Order_CompensationGroup columns
-- ============================================================================
-- Contract -> its order compensation groups in Related Documents (C_Order_ID and the schema are already zoom targets)
UPDATE AD_Column
SET IsExcludeFromZoomTargets = 'N', Updated = TO_TIMESTAMP('2026-10-09 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593649 /* C_Order_CompensationGroup.C_Flatrate_Term_ID */
;
-- Filters: Name, Order, Contract, Schema, Organisation (Name and AD_Org_ID already are selection columns)
UPDATE AD_Column
SET SelectionColumnSeqNo = 10, Updated = TO_TIMESTAMP('2026-10-09 11:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 557817 /* C_Order_CompensationGroup.Name */
;
UPDATE AD_Column
SET IsSelectionColumn = 'Y', SelectionColumnSeqNo = 20, Updated = TO_TIMESTAMP('2026-10-09 11:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 557816 /* C_Order_CompensationGroup.C_Order_ID */
;
UPDATE AD_Column
SET IsSelectionColumn = 'Y', SelectionColumnSeqNo = 30, Updated = TO_TIMESTAMP('2026-10-09 11:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593649 /* C_Order_CompensationGroup.C_Flatrate_Term_ID */
;
UPDATE AD_Column
SET IsSelectionColumn = 'Y', SelectionColumnSeqNo = 40, Updated = TO_TIMESTAMP('2026-10-09 11:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 559466 /* C_Order_CompensationGroup.C_CompensationGroup_Schema_ID */
;
UPDATE AD_Column
SET SelectionColumnSeqNo = 50, Updated = TO_TIMESTAMP('2026-10-09 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 557809 /* C_Order_CompensationGroup.AD_Org_ID */
;
-- ============================================================================
-- 5) AD_Field: header tab (default sort: C_Order_ID descending (SortNo=-1), then Name ascending (SortNo=2))
-- ============================================================================
-- Name
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557817, 785661 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 255, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Name', 10, 10, 2, TO_TIMESTAMP('2026-10-09 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785661
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(469)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785661
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785661)
;
-- C_Order_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557816, 785662 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Auftrag', 20, 20, -1, TO_TIMESTAMP('2026-10-09 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785662
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(558)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785662
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785662)
;
-- C_Flatrate_Term_ID (caption 'Vertrag' via AD_Name_ID 574343)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 593649, 785663 /*From ID Server*/, 574343, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Vertrag', 30, 30, 0, TO_TIMESTAMP('2026-10-09 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785663
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(574343)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785663
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785663)
;
-- C_CompensationGroup_Schema_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 559466, 785664 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Kompensationsgruppe Schema', 40, 40, 0, TO_TIMESTAMP('2026-10-09 11:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785664
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543889)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785664
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785664)
;
-- M_Product_Category_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557818, 785665 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Produkt Kategorie', 50, 50, 0, TO_TIMESTAMP('2026-10-09 11:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785665
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(453)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785665
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785665)
;
-- PP_Product_BOM_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 564267, 785666 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Stücklistenversion', 60, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785666
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(53245)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785666
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785666)
;
-- C_Activity_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 573341, 785667 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Kostenstelle', 70, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785667
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(1005)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785667
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785667)
;
-- IsActive
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557812, 785668 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Aktiv', 80, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785668
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785668
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785668)
;
-- IsNamePrinted
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 575180, 785669 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Name drucken', 90, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785669
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(579524)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785669
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785669)
;
-- AD_Org_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557809, 785670 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Sektion', 100, 60, 0, TO_TIMESTAMP('2026-10-09 11:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785670
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785670
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785670)
;
-- AD_Client_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557808, 785671 /*From ID Server*/, NULL, 0, 549517, TO_TIMESTAMP('2026-10-09 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Mandant', 110, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785671
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(102)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785671
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785671)
;
-- ============================================================================
-- 6) AD_Field: order lines tab (default sort: Line ascending)
-- ============================================================================
-- Line
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 2214, 785672 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Zeile Nr.', 10, 10, 1, TO_TIMESTAMP('2026-10-09 11:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785672
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(439)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785672
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785672)
;
-- M_Product_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 2221, 785673 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Produkt', 20, 20, 0, TO_TIMESTAMP('2026-10-09 11:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785673
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(454)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785673
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785673)
;
-- QtyEntered
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 12876, 785674 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Menge', 30, 30, 0, TO_TIMESTAMP('2026-10-09 11:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785674
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(2589)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785674
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785674)
;
-- C_UOM_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 2222, 785675 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Maßeinheit', 40, 40, 0, TO_TIMESTAMP('2026-10-09 11:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785675
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(215)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785675
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785675)
;
-- PriceEntered
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 12875, 785676 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Preis', 50, 50, 0, TO_TIMESTAMP('2026-10-09 11:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785676
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(2588)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785676
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785676)
;
-- Price_UOM_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 550846, 785677 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Preiseinheit', 60, 60, 0, TO_TIMESTAMP('2026-10-09 11:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785677
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(542464)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785677
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785677)
;
-- LineNetAmt
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 3723, 785678 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Zeilennetto', 70, 70, 0, TO_TIMESTAMP('2026-10-09 11:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785678
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(441)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785678
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785678)
;
-- GroupCompensationType
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557778, 785679 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Gruppenart', 80, 80, 0, TO_TIMESTAMP('2026-10-09 11:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785679
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543461)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785679
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785679)
;
-- GroupCompensationAmtType
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557779, 785680 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Preisminderung Betrag Art', 90, 90, 0, TO_TIMESTAMP('2026-10-09 11:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785680
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543462)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785680
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785680)
;
-- link column of the tab (see AD_Tab.AD_Column_ID above); hidden, no UI element
-- C_Order_CompensationGroup_ID
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, Updated, UpdatedBy)
VALUES (0, 557744, 785681 /*From ID Server*/, NULL, 0, 549518, TO_TIMESTAMP('2026-10-09 11:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Auftrag Kompensationsgruppe', 0, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785681
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(543469)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785681
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785681)
;
-- ============================================================================
-- 7) UI layout: header tab (left: primary group + second group; right: flags group + org group)
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549517, 548009 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-09 11:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548009
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
-- Left column
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549781 /*From ID Server*/, 548009, TO_TIMESTAMP('2026-10-09 11:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-09 11:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549782 /*From ID Server*/, 548009, TO_TIMESTAMP('2026-10-09 11:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 20, TO_TIMESTAMP('2026-10-09 11:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Left column: primary group (Name, Order, Contract, Schema)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549781, 555818 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-10-09 11:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Left column: second group (Product category, BOM, Activity)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549781, 555819 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 20, NULL, TO_TIMESTAMP('2026-10-09 11:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column: flags group (IsActive first)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549782, 555820 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'flags', 10, NULL, TO_TIMESTAMP('2026-10-09 11:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Right column: org group (Org then Client), last group of the right column
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549782, 555821 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'org', 20, NULL, TO_TIMESTAMP('2026-10-09 11:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Name: SeqNo=10, SeqNoGrid=10
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785661, 0, 549517, 555818, 654990 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Name', 10, 10, 0, TO_TIMESTAMP('2026-10-09 11:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- C_Order_ID: SeqNo=20, SeqNoGrid=20
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785662, 0, 549517, 555818, 654991 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Auftrag', 20, 20, 0, TO_TIMESTAMP('2026-10-09 11:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- C_Flatrate_Term_ID: SeqNo=30, SeqNoGrid=30
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785663, 0, 549517, 555818, 654992 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Vertrag', 30, 30, 0, TO_TIMESTAMP('2026-10-09 11:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- C_CompensationGroup_Schema_ID: SeqNo=40, SeqNoGrid=40
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785664, 0, 549517, 555818, 654993 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Kompensationsgruppe Schema', 40, 40, 0, TO_TIMESTAMP('2026-10-09 11:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- M_Product_Category_ID: SeqNo=10, SeqNoGrid=50
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785665, 0, 549517, 555819, 654994 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produkt Kategorie', 10, 50, 0, TO_TIMESTAMP('2026-10-09 11:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- PP_Product_BOM_ID: SeqNo=20, not in grid
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785666, 0, 549517, 555819, 654995 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Stücklistenversion', 20, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- C_Activity_ID: SeqNo=30, not in grid
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785667, 0, 549517, 555819, 654996 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Kostenstelle', 30, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- IsActive: SeqNo=10, not in grid
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785668, 0, 549517, 555820, 654997 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Aktiv', 10, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- IsNamePrinted: SeqNo=20, not in grid
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785669, 0, 549517, 555820, 654998 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Name drucken', 20, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- AD_Org_ID: SeqNo=10, SeqNoGrid=60
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785670, 0, 549517, 555821, 654999 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Sektion', 10, 60, 0, TO_TIMESTAMP('2026-10-09 11:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- AD_Client_ID: SeqNo=20, not in grid
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785671, 0, 549517, 555821, 655000 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Mandant', 20, 0, 0, TO_TIMESTAMP('2026-10-09 11:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- ============================================================================
-- 8) UI layout: order lines tab (one primary group; UOM next to quantity, price UOM next to price)
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549518, 548010 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-09 11:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548010
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
-- Single column
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549783 /*From ID Server*/, 548010, TO_TIMESTAMP('2026-10-09 11:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-09 11:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Single primary group
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549783, 555822 /*From ID Server*/, TO_TIMESTAMP('2026-10-09 11:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-10-09 11:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- Line: SeqNo=10, SeqNoGrid=10
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785672, 0, 549518, 555822, 655001 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Zeile Nr.', 10, 10, 0, TO_TIMESTAMP('2026-10-09 11:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S')
;
-- M_Product_ID: SeqNo=20, SeqNoGrid=20
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785673, 0, 549518, 555822, 655002 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produkt', 20, 20, 0, TO_TIMESTAMP('2026-10-09 11:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100, 'L')
;
-- QtyEntered: SeqNo=30, SeqNoGrid=30
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785674, 0, 549518, 555822, 655003 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Menge', 30, 30, 0, TO_TIMESTAMP('2026-10-09 11:00:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S')
;
-- C_UOM_ID: SeqNo=40, SeqNoGrid=40
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785675, 0, 549518, 555822, 655004 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Maßeinheit', 40, 40, 0, TO_TIMESTAMP('2026-10-09 11:00:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S')
;
-- PriceEntered: SeqNo=50, SeqNoGrid=50
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785676, 0, 549518, 555822, 655005 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Preis', 50, 50, 0, TO_TIMESTAMP('2026-10-09 11:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- Price_UOM_ID: SeqNo=60, SeqNoGrid=60
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785677, 0, 549518, 555822, 655006 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:58', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Preiseinheit', 60, 60, 0, TO_TIMESTAMP('2026-10-09 11:00:58', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S')
;
-- LineNetAmt: SeqNo=70, SeqNoGrid=70
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785678, 0, 549518, 555822, 655007 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:00:59', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Zeilennetto', 70, 70, 0, TO_TIMESTAMP('2026-10-09 11:00:59', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- GroupCompensationType: SeqNo=80, SeqNoGrid=80
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785679, 0, 549518, 555822, 655008 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Gruppenart', 80, 80, 0, TO_TIMESTAMP('2026-10-09 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
-- GroupCompensationAmtType: SeqNo=90, SeqNoGrid=90
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy, WidgetSize)
VALUES (0, 785680, 0, 549518, 555822, 655009 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-09 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Preisminderung Betrag Art', 90, 90, 0, TO_TIMESTAMP('2026-10-09 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100, NULL)
;
