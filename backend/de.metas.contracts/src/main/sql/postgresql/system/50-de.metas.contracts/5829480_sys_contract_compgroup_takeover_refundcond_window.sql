-- Contract compensation groups: take-over tab "Übernahme aus dem Verkaufsauftrag" (AD_Tab_ID=549509, window 542194) gets
--   * the field "Nachkommastellen der Summe" (PercentagePrecision), right after the own-line discount product
--   * the customer refund conditions (junction C_CompensationGroup_ContractSettings_TakeOver_RefundCond) as an inline Labels
--     multi-select, right after the customer discount products. Same construction as the customer discount products Labels:
--     the WebUI renders only one child tab level, so the hidden backing tab is TabLevel=2 (a TabLevel=1 one would be rendered
--     as a stray tab of the root).
-- ============================================================================
-- 1) AD_Element: customer refund conditions (hidden labels tab + Labels widget caption) + description
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585542 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Rückvergütungsbedingungen (Kunde)', 'Rückvergütungsbedingungen (Kunde)',
        'Rückvergütungsbedingungen des Kunden, deren Prozentsatz zusätzlich in die Übernahmezeile übernommen wird. Es zählt nur ein Rückvergütungsvertrag ohne Produkt, dessen Bedingung genau eine aktive Rückvergütungseinstellung hat: ohne Produkt, Basis Prozent, Mindestmenge 0, ohne Einschränkung auf Verpackungsoptionen (eine Produktkategorie als Basis ist erlaubt). Gestaffelte, betragsbasierte, produktbezogene und auf Verpackungsoptionen eingeschränkte Rückvergütungen werden ignoriert. Verpackungs-Rückvergütungen (z. B. „RV Verpackung …“) nicht eintragen. Eine eingetragene Bedingung zählt unabhängig davon, ob sie bei Zahlung abgezogen wird. Jede Bedingung höchstens einmal je Einstellung.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585542, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585542
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Customer refund conditions', PrintName = 'Customer refund conditions',
    Description = 'Customer refund conditions whose percentage is additionally taken over into the take-over line. Only a refund contract without product counts whose condition has exactly one active refund setting: no product, base percentage, minimum quantity 0, no restriction to packing options (a product category as base is allowed). Tiered, amount-based, product-specific and packing-option-restricted refunds are ignored. Do not list packaging refunds (e.g. "RV Verpackung …"). A listed condition counts whether or not it is deducted at payment. Each condition at most once per settings.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585542 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: text is already the German base text (no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585542 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 2) AD_Tab: hidden labels backing tab over the junction table -- TabLevel=2, no Parent_Column_ID
--    (never rendered as a tab: its Labels UI element lives on the take-over tab, see section 6)
-- ============================================================================
INSERT INTO AD_Tab (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Tab_ID, AD_Table_ID, AD_Window_ID, AllowQuickInput, Created, CreatedBy, EntityType, HasTree, ImportFields, InternalName, IsActive, IsAdvancedTab, IsCheckParentsChanged, IsGenericZoomTarget, IsGridModeOnly, IsInfoTab, IsInsertRecord, IsQueryOnLoad, IsReadOnly, IsRefreshAllOnActivate, IsRefreshViewOnChangeEvents, IsSearchActive, IsSearchCollapsed, IsSingleRow, IsSortTab, IsTranslationTab, MaxQueryRecords, Name, Processing, SeqNo, TabLevel, Updated, UpdatedBy)
VALUES (0, 585542, 0, 549519 /*From ID Server*/, 542656, 542194, 'Y', TO_TIMESTAMP('2026-10-15 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'N', 'N', 'C_CompensationGroup_ContractSettings_TakeOver_RefundCond', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Rückvergütungsbedingungen (Kunde)', 'N', 50, 2, TO_TIMESTAMP('2026-10-15 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Tab_Trl (AD_Language, AD_Tab_ID, CommitWarning, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Tab t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Tab_ID = 549519
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Tab_ID = t.AD_Tab_ID)
;
/* DDL */ select update_tab_translation_from_ad_element(585542)
;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 549519
;
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549519)
;
-- ============================================================================
-- 3) AD_Field: PercentagePrecision on the take-over tab (column's own element 585540)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593738, 785686 /*From ID Server*/, 0, 549509, TO_TIMESTAMP('2026-10-15 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Nachkommastellen der Summe', TO_TIMESTAMP('2026-10-15 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785686
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785686
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785686)
;
-- ============================================================================
-- 4) AD_Field: labels selector on the hidden labels tab = refund condition (C_Flatrate_Conditions_ID of the junction;
--    its column carries the "Type_Conditions = Refund" validation rule). No AD_UI_Element -- the Labels element of section 6 references it.
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593748, 785687 /*From ID Server*/, 0, 549519, TO_TIMESTAMP('2026-10-15 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'de.metas.contracts', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Vertragsbedingungen', TO_TIMESTAMP('2026-10-15 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Field_ID = 785687
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(541423 /*C_Flatrate_Conditions_ID element*/)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785687
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785687)
;
-- ============================================================================
-- 5) AD_Element 585540 (PercentagePrecision): final description in all languages, then propagate to the column and the new field
-- ============================================================================
UPDATE AD_Element_Trl
SET Description = 'Anzahl Nachkommastellen, auf die der summierte Prozentsatz der Übernahmezeile kaufmännisch gerundet wird (Standard 2). Die eigene Zeile des Lieferanten und die Zeile der Zahlungsdienstleister-Gebühr werden nicht gerundet.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 11:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585540 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Element_Trl
SET Description = 'Number of decimals to which the summed percentage of the take-over line is rounded half up (default 2). The vendor''s own line and the payment-service fee line are not rounded.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 11:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585540 AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585540)
;
-- ============================================================================
-- 6) UI layout of the take-over tab (existing primary group 555798):
--    10 category, 20 own-line discount product, 25 PercentagePrecision (new), 30 customer discount products,
--    35 customer refund conditions (new), 40 IsActive
-- ============================================================================
-- UI Element: PercentagePrecision (SeqNo=25, SeqNoGrid=25 -- between the own-line discount product (20) and IsActive (30))
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 785686, 0, 549509, 555798, 655044 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-10-15 11:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Nachkommastellen der Summe', 25, 25, 0, TO_TIMESTAMP('2026-10-15 11:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- UI Element: Labels = customer refund conditions (SeqNo=35, form only). Type 'L'; AD_Name_ID 585542 gives the translated caption;
-- Description is the German text (AD_UI_Element has no _Trl table; the translated texts live on AD_Element 585542).
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, Labels_Selector_Field_ID, Labels_Tab_ID, MultiLine_LinesCount, AD_Name_ID, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 549509, 655045 /*From ID Server*/, 555798, 'L', TO_TIMESTAMP('2026-10-15 11:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Rückvergütungsbedingungen des Kunden, deren Prozentsatz zusätzlich in die Übernahmezeile übernommen wird. Es zählt nur ein Rückvergütungsvertrag ohne Produkt, dessen Bedingung genau eine aktive Rückvergütungseinstellung hat: ohne Produkt, Basis Prozent, Mindestmenge 0, ohne Einschränkung auf Verpackungsoptionen (eine Produktkategorie als Basis ist erlaubt). Gestaffelte, betragsbasierte, produktbezogene und auf Verpackungsoptionen eingeschränkte Rückvergütungen werden ignoriert. Verpackungs-Rückvergütungen (z. B. „RV Verpackung …“) nicht eintragen. Eine eingetragene Bedingung zählt unabhängig davon, ob sie bei Zahlung abgezogen wird. Jede Bedingung höchstens einmal je Einstellung.',
        'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 785687, 549519, 0, 585542, 'Rückvergütungsbedingungen (Kunde)', 35, 0, 0, TO_TIMESTAMP('2026-10-15 11:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
