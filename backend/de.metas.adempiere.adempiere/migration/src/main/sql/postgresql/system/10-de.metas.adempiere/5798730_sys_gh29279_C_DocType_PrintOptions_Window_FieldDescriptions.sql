-- Per-field descriptions / help for window "Document Type Printing Options"
-- (AD_Window_ID=541004, Tab=543206). Descriptions follow the user-facing
-- semantics documented in mf15-documentation#96.
--
-- Fields touched on tab 543206:
--   AD_Field 625830  → C_DocType_ID               (AD_UI_Element 575095)
--   AD_Field 626425  → DocumentFlavor             (AD_UI_Element 575346)
--   AD_Field 625832  → PRINTER_OPTS_IsPrintLogo   (AD_UI_Element 575098)
--   AD_Field 625833  → PRINTER_OPTS_IsPrintTotals (AD_UI_Element 575099)
--
-- Per-field overrides only — AD_Element of PRINTER_OPTS_IsPrintLogo /
-- PRINTER_OPTS_IsPrintTotals is reused across many AD_Process_Para rows,
-- so we deliberately do not touch the shared AD_Element descriptions.


-- =============================================================================
-- 1) AD_Field.Description + Help (base language de_DE)
-- =============================================================================

-- C_DocType_ID — Belegart
UPDATE AD_Field
SET Description = 'Die Belegart, für die diese Druckoptionen gelten (z. B. Lieferung, Rechnung, Auftrag).',
    Help        = 'Das Belegart-Feld bestimmt, auf welche Dokumenttypen diese Druckoptionen angewendet werden. Jede Belegart kann bis zu drei Konfigurationen haben: eine für Flavor "Print", eine für "EMail" und einen universellen Standard (ohne Flavor).',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625830;

-- DocumentFlavor — Flavor
UPDATE AD_Field
SET Description = 'Ausgabekanal für diese Konfiguration: EMail, Print oder leer (universeller Standard).',
    Help        = 'Legt fest, ob diese Einstellungen beim Drucken (Print), beim Versand per E-Mail (EMail) oder als universeller Standard (leer) angewendet werden. Ein spezifischer Flavor hat Vorrang vor dem universellen Standard.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 626425;

-- PRINTER_OPTS_IsPrintLogo — Logo drucken
UPDATE AD_Field
SET Description = 'Ob das Firmenlogo auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird das hinterlegte Briefkopf-/Logobild beim Rendern des Dokuments in die PDF-Ausgabe übernommen. Beim manuellen Drucken dient dieser Wert als Vorbelegung im Druckdialog und kann vom Benutzer noch überschrieben werden.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625832;

-- PRINTER_OPTS_IsPrintTotals — Gesamtbeträge drucken
UPDATE AD_Field
SET Description = 'Ob der Summenblock auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird die Summen-/Gesamtbeträge-Sektion im gedruckten oder versendeten Dokument gerendert. Nützlich z. B. um Gesamtbeträge aus E-Mail-Ausgaben wegzulassen, wenn diese bereits im Mailtext stehen.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625833;


-- =============================================================================
-- 2) AD_Field_Trl (en_US) — create if missing, then fill with English text
-- =============================================================================

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                          AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT 'en_US', f.AD_Field_ID, f.Description, f.Help, f.Name, 'N',
       f.AD_Client_ID, f.AD_Org_ID,
       TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), 0
FROM AD_Field f
WHERE f.AD_Field_ID IN (625830, 626425, 625832, 625833)
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Field_ID = f.AD_Field_ID AND tt.AD_Language = 'en_US');

-- C_DocType_ID (EN)
UPDATE AD_Field_Trl
SET Description  = 'The document type this configuration applies to (e.g., Lieferung, Rechnung, Auftrag).',
    Help         = 'Determines which document types these print options apply to. Each document type can have up to three configurations: one for Flavor "Print", one for "EMail", and a universal default (no flavor).',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625830 AND AD_Language = 'en_US';

-- DocumentFlavor (EN)
UPDATE AD_Field_Trl
SET Description  = 'Output channel for this configuration: EMail, Print, or empty (universal default).',
    Help         = 'Controls whether these settings apply when printing (Print), emailing (EMail), or as the universal default (empty). A specific flavor takes precedence over the universal default.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 626425 AND AD_Language = 'en_US';

-- PRINTER_OPTS_IsPrintLogo (EN)
UPDATE AD_Field_Trl
SET Description  = 'Whether the company logo is included in the generated document.',
    Help         = 'If enabled, the configured letterhead/logo image is rendered into the PDF output. On manual prints this value pre-fills the print dialog checkbox and can be overridden by the user.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625832 AND AD_Language = 'en_US';

-- PRINTER_OPTS_IsPrintTotals (EN)
UPDATE AD_Field_Trl
SET Description  = 'Whether the totals section is included in the generated document.',
    Help         = 'If enabled, the totals/summary section is rendered in the printed or emailed document. Useful e.g. to omit totals from email output when they are already in the email body.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Field_ID = 625833 AND AD_Language = 'en_US';


-- =============================================================================
-- 3) AD_UI_Element.Description + Help (base language de_DE)
-- =============================================================================

-- C_DocType_ID
UPDATE AD_UI_Element
SET Description = 'Die Belegart, für die diese Druckoptionen gelten (z. B. Lieferung, Rechnung, Auftrag).',
    Help        = 'Das Belegart-Feld bestimmt, auf welche Dokumenttypen diese Druckoptionen angewendet werden. Jede Belegart kann bis zu drei Konfigurationen haben: eine für Flavor "Print", eine für "EMail" und einen universellen Standard (ohne Flavor).',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575095;

-- DocumentFlavor
UPDATE AD_UI_Element
SET Description = 'Ausgabekanal für diese Konfiguration: EMail, Print oder leer (universeller Standard).',
    Help        = 'Legt fest, ob diese Einstellungen beim Drucken (Print), beim Versand per E-Mail (EMail) oder als universeller Standard (leer) angewendet werden. Ein spezifischer Flavor hat Vorrang vor dem universellen Standard.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575346;

-- PRINTER_OPTS_IsPrintLogo
UPDATE AD_UI_Element
SET Description = 'Ob das Firmenlogo auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird das hinterlegte Briefkopf-/Logobild beim Rendern des Dokuments in die PDF-Ausgabe übernommen. Beim manuellen Drucken dient dieser Wert als Vorbelegung im Druckdialog und kann vom Benutzer noch überschrieben werden.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575098;

-- PRINTER_OPTS_IsPrintTotals
UPDATE AD_UI_Element
SET Description = 'Ob der Summenblock auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird die Summen-/Gesamtbeträge-Sektion im gedruckten oder versendeten Dokument gerendert. Nützlich z. B. um Gesamtbeträge aus E-Mail-Ausgaben wegzulassen, wenn diese bereits im Mailtext stehen.',
    Updated     = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575099;


-- =============================================================================
-- 4) AD_UI_Element_Trl (en_US) — create if missing, then fill with EN text
-- =============================================================================

INSERT INTO AD_UI_Element_Trl (AD_Language, AD_UI_Element_ID, Description, Help, Name, IsTranslated,
                               AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT 'en_US', ue.AD_UI_Element_ID, ue.Description, ue.Help, ue.Name, 'N',
       ue.AD_Client_ID, ue.AD_Org_ID,
       TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), 0
FROM AD_UI_Element ue
WHERE ue.AD_UI_Element_ID IN (575095, 575346, 575098, 575099)
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Element_Trl tt
                  WHERE tt.AD_UI_Element_ID = ue.AD_UI_Element_ID AND tt.AD_Language = 'en_US');

-- C_DocType_ID (EN)
UPDATE AD_UI_Element_Trl
SET Description  = 'The document type this configuration applies to (e.g., Lieferung, Rechnung, Auftrag).',
    Help         = 'Determines which document types these print options apply to. Each document type can have up to three configurations: one for Flavor "Print", one for "EMail", and a universal default (no flavor).',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575095 AND AD_Language = 'en_US';

-- DocumentFlavor (EN)
UPDATE AD_UI_Element_Trl
SET Description  = 'Output channel for this configuration: EMail, Print, or empty (universal default).',
    Help         = 'Controls whether these settings apply when printing (Print), emailing (EMail), or as the universal default (empty). A specific flavor takes precedence over the universal default.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575346 AND AD_Language = 'en_US';

-- PRINTER_OPTS_IsPrintLogo (EN)
UPDATE AD_UI_Element_Trl
SET Description  = 'Whether the company logo is included in the generated document.',
    Help         = 'If enabled, the configured letterhead/logo image is rendered into the PDF output. On manual prints this value pre-fills the print dialog checkbox and can be overridden by the user.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575098 AND AD_Language = 'en_US';

-- PRINTER_OPTS_IsPrintTotals (EN)
UPDATE AD_UI_Element_Trl
SET Description  = 'Whether the totals section is included in the generated document.',
    Help         = 'If enabled, the totals/summary section is rendered in the printed or emailed document. Useful e.g. to omit totals from email output when they are already in the email body.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-20 18:30','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 575099 AND AD_Language = 'en_US';
