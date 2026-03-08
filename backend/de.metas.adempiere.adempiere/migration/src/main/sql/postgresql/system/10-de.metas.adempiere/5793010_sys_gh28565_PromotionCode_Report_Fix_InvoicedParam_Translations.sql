-- 2026-03-08
-- gh#28565: Fix Invoiced parameter translations for de_DE, de_CH, en_GB
-- The parameter still showed "Aktiv" / "Der Eintrag ist im System aktiv" from the old
-- AD_Element 348 (IsActive). Migration 5792970 only fixed en_US. Fix all remaining languages.

-- de_DE and de_CH: "Aktiv" → "Fakturiert"
UPDATE AD_Process_Para_Trl
SET Name        = 'Fakturiert',
    Description = 'Filtert nach Fakturierungsstatus: Ja = nur fakturierte, Nein = nur nicht-fakturierte. Nicht gesetzt = alle Aufträge.',
    Help        = 'Dropdown mit drei Zuständen: Ja zeigt nur Aufträge die bereits fakturiert sind, Nein zeigt nur nicht-fakturierte Aufträge, '
                      || 'und wenn nichts gewählt ist werden alle Aufträge unabhängig vom Fakturierungsstatus angezeigt.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 16:00',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543142 AND AD_Language IN ('de_DE', 'de_CH');

-- en_GB: same as en_US
UPDATE AD_Process_Para_Trl
SET Name        = 'Invoiced',
    Description = 'Filter by invoicing status: Yes = invoiced only, No = not-invoiced only. Leave empty for all orders.',
    Help        = 'Dropdown with three states: Yes shows only orders that have been invoiced, No shows only orders not yet invoiced, '
                      || 'and when nothing is selected all orders are shown regardless of invoicing status.',
    IsTranslated = 'Y',
    Updated     = '2026-03-08 16:00',
    UpdatedBy   = 100
WHERE AD_Process_Para_ID = 543142 AND AD_Language = 'en_GB';
