-- Contract compensation groups: take-over tab on the settings window.
--
-- Adds, to the "Kompensationsgruppen-Vertragseinstellungen" window (AD_Window_ID=542194):
--   * a tab "Übernahme aus dem Verkaufsauftrag" / "Take over from the sales order" (TabLevel=1, child of the
--     settings tab 549507) over C_CompensationGroup_ContractSettings_TakeOver (AD_Table_ID=542652, from
--     migration 5827430): category + own-line discount product + the customer discount products.
--   * the customer discount products (junction C_CompensationGroup_ContractSettings_TakeOver_Product,
--     AD_Table_ID=542653) are a multi-value association of the take-over record. They are surfaced as an inline
--     multi-select via a Labels UI element (AD_UI_ElementType='L') on the take-over tab, backed by a hidden labels
--     tab + selector field on the junction table -- NOT as a (grand)child tab: the WebUI supports only one child
--     level (GridWindowVO.getChildTabs ignores grandchildren; LayoutFactory.getIncludedTabLayouts is empty for non-root tabs).
--   * The hidden labels tab is TabLevel=2 because its host (the take-over tab) is itself a child tab: the root layout
--     only instantiates TabLevel=1 children, so a TabLevel=1 backing tab would be rendered as a stray visible tab (the root
--     LayoutFactory skips only the labels tabs of its OWN labels elements). Precedent: displayed Labels elements 585705 / 588403
--     (window 123, host tab 496 TabLevel=1) use a TabLevel=2 labels tab (544008 / 544281). With no Parent_Column_ID the link column is
--     inferred from the host's key column (C_CompensationGroup_ContractSettings_TakeOver_ID, same name on the junction).
-- Modelled on 5806910 (Labels wiring) and the sibling "Belegarten" tab 549508 of 5826640.
--
-- Mandated field descriptions, via AD_Element/AD_Element_Trl:
--   (a) take-over record: the taken-over percentage also applies to goods that were in a product-bundle group on the sales order
--   (b) customer discount product sub-list: only discount products used exclusively for goods bonuses may be listed;
--       packaging bonuses must use their own discount product
-- Both M_Product_ID columns share the generic element label "Produkt"; the own-line discount product field and the customer
-- discount products widget therefore carry their own label elements (AD_Field.AD_Name_ID / AD_UI_Element.AD_Name_ID).
-- German term for "product-bundle": the system's established term is "Handelsstückliste" (AD_Ref_List "Bestandteil
-- Handelsstückliste" = "Component of bundle", OrderLineReasonForWithoutCharge.BundleComponent).
--
-- IDs allocated from idserver.metas.de on 2026-10-01:
--   AD_MigrationScript  5827470 (this script)
--   AD_Element   585507 (take-over tab title + description (a)), 585508 (customer discount products title + description (b);
--                caption of the hidden labels tab and of the Labels widget),
--                585509 (field label "Rabattprodukt (eigene Zeile)" + description (a)),
--                585510 (field label "Kundenrabattprodukt" of the labels selector field + description (b))
--   AD_Tab       549509 (take-over, TabLevel=1), 549511 (hidden labels backing tab, TabLevel=2)
--   AD_UI_Section 548002; AD_UI_Column 549771; AD_UI_ElementGroup 555798
--   AD_Field     785594 (M_Product_Category_ID), 785595 (M_Product_ID own line), 785596 (IsActive) -- take-over tab
--                785599 (M_Product_ID customer = labels selector) -- hidden labels tab
--   AD_UI_Element 654922 (category), 654923 (own-line product), 654927 (Labels: customer discount products), 654924 (IsActive)
-- ============================================================================
-- 1) AD_Element: take-over tab title (+ description (a))
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585507 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Übernahme aus dem Verkaufsauftrag', 'Übernahme aus dem Verkaufsauftrag',
        'Der übernommene Prozentsatz gilt auch für Waren, die im Verkaufsauftrag in einer Handelsstücklisten-Gruppe enthalten waren.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585507, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585507
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Take over from the sales order', PrintName = 'Take over from the sales order',
    Description = 'The taken-over percentage also applies to goods that were in a product-bundle group on the sales order.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585507 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: text is already the German base text (de_CH has no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585507 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 2) AD_Element: customer discount products title (hidden labels tab + Labels widget caption) (+ description (b))
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585508 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Übernommene Kundenrabattprodukte', 'Übernommene Kundenrabattprodukte',
        'Es dürfen nur Rabattprodukte aufgeführt werden, die ausschließlich für Warenboni verwendet werden; Verpackungsboni müssen ein eigenes Rabattprodukt verwenden.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585508, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585508
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Taken-over customer discount products', PrintName = 'Taken-over customer discount products',
    Description = 'Only discount products used exclusively for goods bonuses may be listed; packaging bonuses must use their own discount product.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585508 AND AD_Language = 'en_US'
;
-- de_CH: Swiss spelling (ß -> ss)
UPDATE AD_Element_Trl
SET Description = 'Es dürfen nur Rabattprodukte aufgeführt werden, die ausschliesslich für Warenboni verwendet werden; Verpackungsboni müssen ein eigenes Rabattprodukt verwenden.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585508 AND AD_Language = 'de_CH'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585508 AND AD_Language = 'de_DE'
;
-- ============================================================================
-- 3) AD_Element: field label "Rabattprodukt (eigene Zeile)" (take-over tab, own-line discount product) + description (a)
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585509 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Rabattprodukt (eigene Zeile)', 'Rabattprodukt (eigene Zeile)',
        'Der übernommene Prozentsatz gilt auch für Waren, die im Verkaufsauftrag in einer Handelsstücklisten-Gruppe enthalten waren.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585509, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585509
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Discount product (own line)', PrintName = 'Discount product (own line)',
    Description = 'The taken-over percentage also applies to goods that were in a product-bundle group on the sales order.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585509 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585509 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 4) AD_Element: field label "Kundenrabattprodukt" (labels selector field, customer discount product) + description (b)
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585510 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Kundenrabattprodukt', 'Kundenrabattprodukt',
        'Es dürfen nur Rabattprodukte aufgeführt werden, die ausschließlich für Warenboni verwendet werden; Verpackungsboni müssen ein eigenes Rabattprodukt verwenden.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585510, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585510
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Customer discount product', PrintName = 'Customer discount product',
    Description = 'Only discount products used exclusively for goods bonuses may be listed; packaging bonuses must use their own discount product.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585510 AND AD_Language = 'en_US'
;
-- de_CH: Swiss spelling (ß -> ss)
UPDATE AD_Element_Trl
SET Description = 'Es dürfen nur Rabattprodukte aufgeführt werden, die ausschliesslich für Warenboni verwendet werden; Verpackungsboni müssen ein eigenes Rabattprodukt verwenden.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585510 AND AD_Language = 'de_CH'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585510 AND AD_Language = 'de_DE'
;
-- ============================================================================
-- 5) AD_Tab: take-over -- TabLevel=1, bound to the settings tab via Parent_Column_ID
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Parent_Column_ID, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585507, 0, 549509 /*From ID Server*/, 542652, 542194, 'Y', TO_TIMESTAMP('2026-10-01 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'N', 'N', 'C_CompensationGroup_ContractSettings_TakeOver', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Übernahme aus dem Verkaufsauftrag', 593682 /* C_CompensationGroup_ContractSettings_TakeOver.C_CompensationGroup_ContractSettings_ID */, 'N', 30, 1, TO_TIMESTAMP('2026-10-01 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549509
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585507)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549509
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549509)
;
-- ============================================================================
-- 6) AD_Tab: hidden labels backing tab over the junction table -- TabLevel=2, no Parent_Column_ID
--    (never rendered as a tab: its Labels UI element lives on the take-over tab, see section 9)
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585508, 0, 549511 /*From ID Server*/, 542653, 542194, 'Y', TO_TIMESTAMP('2026-10-01 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'N', 'N', 'C_CompensationGroup_ContractSettings_TakeOver_Product', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Übernommene Kundenrabattprodukte', 'N', 40, 2, TO_TIMESTAMP('2026-10-01 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549511
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585508)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549511
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549511)
;
-- ============================================================================
-- 7) AD_Field: take-over tab (category, own-line discount product, IsActive)
-- ============================================================================
-- M_Product_Category_ID (column's own element 453)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593683, 785594 /*From ID Server*/, 0, 549509, TO_TIMESTAMP('2026-10-01 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Produkt Kategorie', TO_TIMESTAMP('2026-10-01 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785594
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(453 /*M_Product_Category_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785594
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785594)
;
-- M_Product_ID = own-line discount product (per-field label element 585509 via AD_Name_ID)
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593684, 785595 /*From ID Server*/, 585509, 0, 549509, TO_TIMESTAMP('2026-10-01 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Rabattprodukt (eigene Zeile)', TO_TIMESTAMP('2026-10-01 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785595
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585509 /*own-line discount product label element (AD_Name_ID)*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785595
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785595)
;
-- IsActive
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593678, 785596 /*From ID Server*/, 0, 549509, TO_TIMESTAMP('2026-10-01 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'de.metas.contracts', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Aktiv', TO_TIMESTAMP('2026-10-01 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785596
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(348 /*IsActive element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785596
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785596)
;
-- ============================================================================
-- 8) AD_Field: labels selector on the hidden labels tab = customer discount product (M_Product_ID of the junction)
--    (per-field label element 585510 via AD_Name_ID; no AD_UI_Element -- the Labels element of section 9 references it)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Name_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593694, 785599 /*From ID Server*/, 585510, 0, 549511, TO_TIMESTAMP('2026-10-01 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Kundenrabattprodukt', TO_TIMESTAMP('2026-10-01 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785599
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585510 /*customer discount product label element (AD_Name_ID)*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785599
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785599)
;
-- ============================================================================
-- 9) UI layout: take-over tab (single column, grid-first, one primary group) incl. the Labels element
-- ============================================================================
INSERT INTO AD_UI_Section (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy, Value)
VALUES (0, 0, 549509, 548002 /*From ID Server*/, TO_TIMESTAMP('2026-10-01 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-01 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language, AD_UI_Section_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_UI_Section_ID = 548002
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_UI_Section_ID = t.AD_UI_Section_ID)
;
INSERT INTO AD_UI_Column (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_Section_ID, Created, CreatedBy, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 549771 /*From ID Server*/, 548002, TO_TIMESTAMP('2026-10-01 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 10, TO_TIMESTAMP('2026-10-01 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, UIStyle, Updated, UpdatedBy)
VALUES (0, 0, 549771, 555798 /*From ID Server*/, TO_TIMESTAMP('2026-10-01 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'default', 10, 'primary', TO_TIMESTAMP('2026-10-01 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: M_Product_Category_ID (SeqNo=10, SeqNoGrid=10)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785594, 0, 549509, 555798, 654922 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-01 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Produkt Kategorie', 10, 10, 0, TO_TIMESTAMP('2026-10-01 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: M_Product_ID, own-line discount product (SeqNo=20, SeqNoGrid=20)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785595, 0, 549509, 555798, 654923 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-01 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Rabattprodukt (eigene Zeile)', 20, 20, 0, TO_TIMESTAMP('2026-10-01 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: Labels = customer discount products (SeqNo=30, form only). Type 'L', IsDisplayed='Y'; AD_Name_ID 585508 gives the
-- translated caption; Description is the mandated text (b) (AD_UI_Element has no _Trl table, so the description is German-only).
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, Labels_Selector_Field_ID, Labels_Tab_ID, MultiLine_LinesCount, AD_Name_ID, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 549509, 654927 /*From ID Server*/, 555798, 'L', TO_TIMESTAMP('2026-10-01 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Es dürfen nur Rabattprodukte aufgeführt werden, die ausschließlich für Warenboni verwendet werden; Verpackungsboni müssen ein eigenes Rabattprodukt verwenden.',
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 785599, 549511, 0, 585508, 'Übernommene Kundenrabattprodukte', 30, 0, 0, TO_TIMESTAMP('2026-10-01 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: IsActive (SeqNo=40, SeqNoGrid=30)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785596, 0, 549509, 555798, 654924 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-01 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 40, 30, 0, TO_TIMESTAMP('2026-10-01 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
