-- Update AD_Message 545679 (DropshipWarehouse_MissingVendorOnLine) wording for already-applied DBs.
-- The BEFORE_COMPLETE validation now auto-fills the vendor from the canonical lookup, so the
-- message no longer needs to say "Either ... or ..."; updated to reflect both resolution paths.
-- Created: 2026-05-14 10:00

-- Base column holds German (per metasfresh convention; en_US Trl holds English).
UPDATE AD_Message
SET MsgText   = 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.',
    Updated   = TO_TIMESTAMP('2026-05-14 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679
;

UPDATE AD_Message_Trl
SET MsgText   = 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.',
    Updated   = TO_TIMESTAMP('2026-05-14 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679
  AND AD_Language   = 'de_DE'
;

UPDATE AD_Message_Trl
SET MsgText   = 'Streckengeschäft-Lager: Auf folgenden Auftragspositionen fehlt ein Lieferant — Positionen: {0}. Bitte einen Lieferant pro Position setzen oder einen Standard-Lieferant für das Produkt im Bereich „Lieferant" pflegen.',
    Updated   = TO_TIMESTAMP('2026-05-14 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679
  AND AD_Language   = 'de_CH'
;

UPDATE AD_Message_Trl
SET MsgText   = 'Dropship warehouse: vendor is missing on the following order line(s) — lines: {0}. Set a vendor on the line, or maintain a default vendor for the product.',
    Updated   = TO_TIMESTAMP('2026-05-14 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545679
  AND AD_Language   = 'en_US'
;
