-- 2026-03-08
-- gh#28565: Fix Promotion Code Evaluation report
-- 1) Fix SQLStatement: @Invoiced/null@ passes unquoted Y/N → PostgreSQL error "column n does not exist"
-- 2) Add Description/Help to AD_Element for the process
-- 3) Fix Invoiced parameter: wrong AD_Element 348 (IsActive → shows "Aktiv"), missing description
-- 4) Add descriptions to all process parameters

-- ============================================================
-- 1) Fix SQLStatement — quote the Invoiced parameter using NULLIF
-- ============================================================
UPDATE AD_Process
SET SQLStatement = 'SELECT * FROM report.report_promotion_code_evaluation(@C_PromotionCode_ID/null@, @C_PromotionCode2_ID/null@, NULLIF(''@Invoiced/null@'', ''null''))',
    Updated       = '2026-03-08 12:30',
    UpdatedBy     = 100
WHERE AD_Process_ID = 585592;

-- ============================================================
-- 2) Add Description/Help to AD_Element 584621 (process name element)
--    Descriptions are propagated to AD_Process and AD_Menu automatically
-- ============================================================
UPDATE AD_Element
SET Description = 'Zeigt abgeschlossene Aufträge mit Aktionskennzeichen und deren Fakturierungsstatus als Excel-Export.',
    Help        = 'Listet alle abgeschlossenen Aufträge (DocStatus CO/CL) auf, bei denen mindestens ein Aktionskennzeichen gesetzt ist. '
                      || 'Pro Auftrag werden die Aktionskennzeichen (Wert und Bezeichnung), Auftragsnummer, Datum, Kunde, Auftragssumme '
                      || 'sowie — falls bereits fakturiert — Rechnungsnummer, Rechnungsdatum und Rechnungssumme angezeigt. '
                      || 'Die drei optionalen Filter erlauben die Einschränkung auf ein bestimmtes Aktionskennzeichen oder den Fakturierungsstatus.',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Element_ID = 584621;

UPDATE AD_Element_Trl
SET Description = 'Shows completed sales orders with promotion codes and their invoicing status as an Excel export.',
    Help        = 'Lists all completed/closed sales orders (DocStatus CO/CL) that have at least one promotion code set. '
                      || 'For each order: promotion codes (value and description), order number, date, customer, order total, '
                      || 'and — if already invoiced — invoice number, invoice date, and invoice total. '
                      || 'Three optional filters allow narrowing by specific promotion code or invoicing status.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Element_ID = 584621 AND AD_Language = 'en_US';

-- Propagate element changes to AD_Process, AD_Menu, etc.
SELECT update_ad_element_on_ad_element_trl_update(584621, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584621, 'en_US');
SELECT update_ad_element_on_ad_element_trl_update(584621, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584621, 'de_DE');

-- ============================================================
-- 3) Fix Invoiced parameter — use IsCentrallyMaintained=N and set correct Name/Description
--    AD_Element 348 (IsActive) is wrong for this param, but changing AD_Element_ID
--    on an existing record is risky. Instead, disable central maintenance and set directly.
-- ============================================================
UPDATE AD_Process_Para
SET IsCentrallyMaintained = 'N',
    Name                  = 'Fakturiert',
    Description           = 'Filtert nach Fakturierungsstatus: Ja = nur fakturierte Aufträge, Nein = nur nicht-fakturierte, leer = alle',
    Help                  = 'Wenn gesetzt, werden nur Aufträge angezeigt die bereits fakturiert (Y) oder noch nicht fakturiert (N) sind. '
                                || 'Wenn nicht gesetzt, werden alle Aufträge unabhängig vom Fakturierungsstatus angezeigt.',
    Updated               = '2026-03-08 12:30',
    UpdatedBy             = 100
WHERE AD_Process_Para_ID = 543142;

UPDATE AD_Process_Para_Trl
SET Name        = 'Invoiced',
    Description = 'Filter by invoicing status: Yes = only invoiced orders, No = only not-yet-invoiced, empty = all',
    Help        = 'When set, only orders that are already invoiced (Y) or not yet invoiced (N) are shown. '
                      || 'When not set, all orders are shown regardless of invoicing status.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543142 AND AD_Language = 'en_US';

-- ============================================================
-- 4) Add descriptions to C_PromotionCode_ID parameter
-- ============================================================
UPDATE AD_Process_Para
SET Description = 'Optional: nur Aufträge mit diesem Aktionskennzeichen 1 anzeigen',
    Help        = 'Wenn gesetzt, werden nur Aufträge angezeigt deren erstes Aktionskennzeichen (C_PromotionCode_ID) dem gewählten Wert entspricht.',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543140;

UPDATE AD_Process_Para_Trl
SET Description = 'Optional: show only orders with this promotion code 1',
    Help        = 'When set, only orders whose first promotion code (C_PromotionCode_ID) matches the selected value are shown.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543140 AND AD_Language = 'en_US';

-- ============================================================
-- 5) Add descriptions to C_PromotionCode2_ID parameter
-- ============================================================
UPDATE AD_Process_Para
SET Description = 'Optional: nur Aufträge mit diesem Aktionskennzeichen 2 anzeigen',
    Help        = 'Wenn gesetzt, werden nur Aufträge angezeigt deren zweites Aktionskennzeichen (C_PromotionCode2_ID) dem gewählten Wert entspricht.',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543141;

UPDATE AD_Process_Para_Trl
SET Description = 'Optional: show only orders with this promotion code 2',
    Help        = 'When set, only orders whose second promotion code (C_PromotionCode2_ID) matches the selected value are shown.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 12:30',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543141 AND AD_Language = 'en_US';
