-- Field descriptions / help for window "Document Type Printing Options"
-- (AD_Window_ID=541004, Tab=543206). Texts follow mf15-documentation#96.
--
-- Approach: update the linked AD_Element (base language de_DE) and
-- AD_Element_Trl (en_US), then run the propagation functions so that the
-- correct texts fan out to AD_Column / AD_Field / AD_Process_Para / etc.
-- Never update AD_Field.Description or AD_UI_Element.Description directly
-- — those get overwritten by the next element propagation.
--
-- Elements touched:
--   AD_Element 578565  → DocumentFlavor             (only C_DocType_PrintOptions + AD_Archive)
--   AD_Element 578551  → PRINTER_OPTS_IsPrintLogo   (C_DocType_PrintOptions + many AD_Process_Para)
--   AD_Element 578552  → PRINTER_OPTS_IsPrintTotals (C_DocType_PrintOptions + many AD_Process_Para)
--
-- C_DocType_ID is deliberately NOT touched — its shared AD_Element already
-- has a sensible generic description ("Die Belegart bestimmt den Nummernkreis
-- und die Vorgaben für die Belegverarbeitung.").


-- =============================================================================
-- 1) DocumentFlavor — AD_Element 578565
-- =============================================================================

UPDATE AD_Element
SET Description = 'Ausgabekanal für diese Konfiguration: EMail, Print oder leer (universeller Standard).',
    Help        = 'Legt fest, ob diese Einstellungen beim Drucken (Print), beim Versand per E-Mail (EMail) oder als universeller Standard (leer) angewendet werden. Ein spezifischer Flavor hat Vorrang vor dem universellen Standard.',
    Updated     = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578565;

-- Ensure en_US translation row exists
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', e.AD_Element_ID, e.Name, e.PrintName, e.Description, e.Help, 'N',
       e.AD_Client_ID, e.AD_Org_ID,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Element e
WHERE e.AD_Element_ID = 578565
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Element_ID = e.AD_Element_ID AND tt.AD_Language = 'en_US');

UPDATE AD_Element_Trl
SET Name         = 'Flavor',
    PrintName    = 'Flavor',
    Description  = 'Output channel for this configuration: EMail, Print, or empty (universal default).',
    Help         = 'Controls whether these settings apply when printing (Print), emailing (EMail), or as the universal default (empty). A specific flavor takes precedence over the universal default.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578565 AND AD_Language = 'en_US';


-- =============================================================================
-- 2) PRINTER_OPTS_IsPrintLogo — AD_Element 578551
-- =============================================================================

UPDATE AD_Element
SET Description = 'Ob das Firmenlogo auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird das hinterlegte Briefkopf-/Logobild beim Rendern des Dokuments in die PDF-Ausgabe übernommen. Beim manuellen Drucken dient dieser Wert als Vorbelegung im Druckdialog und kann vom Benutzer noch überschrieben werden.',
    Updated     = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578551;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', e.AD_Element_ID, e.Name, e.PrintName, e.Description, e.Help, 'N',
       e.AD_Client_ID, e.AD_Org_ID,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Element e
WHERE e.AD_Element_ID = 578551
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Element_ID = e.AD_Element_ID AND tt.AD_Language = 'en_US');

UPDATE AD_Element_Trl
SET Description  = 'Whether the company logo is included in the generated document.',
    Help         = 'If enabled, the configured letterhead/logo image is rendered into the PDF output. On manual prints this value pre-fills the print dialog checkbox and can be overridden by the user.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578551 AND AD_Language = 'en_US';


-- =============================================================================
-- 3) PRINTER_OPTS_IsPrintTotals — AD_Element 578552
-- =============================================================================

UPDATE AD_Element
SET Description = 'Ob der Summenblock auf dem generierten Dokument angezeigt wird.',
    Help        = 'Wenn aktiviert, wird die Summen-/Gesamtbeträge-Sektion im gedruckten oder versendeten Dokument gerendert. Nützlich z. B. um Gesamtbeträge aus E-Mail-Ausgaben wegzulassen, wenn diese bereits im Mailtext stehen.',
    Updated     = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578552;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', e.AD_Element_ID, e.Name, e.PrintName, e.Description, e.Help, 'N',
       e.AD_Client_ID, e.AD_Org_ID,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0,
       TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), 0, 'Y'
FROM AD_Element e
WHERE e.AD_Element_ID = 578552
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Element_ID = e.AD_Element_ID AND tt.AD_Language = 'en_US');

UPDATE AD_Element_Trl
SET Description  = 'Whether the totals section is included in the generated document.',
    Help         = 'If enabled, the totals/summary section is rendered in the printed or emailed document. Useful e.g. to omit totals from email output when they are already in the email body.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-04-21 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_Element_ID = 578552 AND AD_Language = 'en_US';


-- =============================================================================
-- 4) Propagate to AD_Column / AD_Field / AD_Process_Para / AD_Column_Trl / …
-- =============================================================================

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578565);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578551);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578552);
