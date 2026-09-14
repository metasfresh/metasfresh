-- gh31292: document the exclusions of the "Umsatz mit Gutschriften (Excel)" report (AD_Process 585439).
-- The report (function sales_invoice_excel_report -> view rv_sales_invoice_report) shows only:
--   * completed sales invoices (docstatus='CO', issotrx='Y'); credit memos included with negated qty/amounts;
--   * invoice lines linked to an invoice candidate -- lines without an invoice candidate get a NULL delivery
--     date (taken from the candidate) and are dropped by the delivery-date range filter, so manually created
--     invoice lines never appear.

-- en_US
UPDATE AD_Process_Trl
SET Description='Exports sales invoice line details including product, customer, sales rep, delivery/invoice dates, quantities, and amounts.
Filterable by invoice date and delivery date ranges.
Note: only completed sales invoices whose lines are linked to an invoice candidate are included.',
    Help='The report lists one row per sales invoice line. The following invoices and lines are excluded:
- Only completed invoices are included; drafts, reversed and voided invoices are excluded.
- Only sales invoices are included; vendor/purchase invoices are excluded. Credit memos are included with negated quantities and amounts.
- Only invoice lines linked to an invoice candidate are shown. Invoice lines without an invoice candidate (e.g. manually created invoice lines) are not displayed, because the delivery date used for filtering is taken from the invoice candidate.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585439
;

UPDATE AD_Process base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- de_CH
UPDATE AD_Process_Trl
SET Description='Exportiert Verkaufsrechnungs-Positionsdetails inkl. Produkt, Kunde, Vertriebsmitarbeiter, Liefer-/Rechnungsdatum, Mengen und Beträge.
Filterbar nach Rechnungs- und Lieferdatum.
Hinweis: Es werden nur abgeschlossene Verkaufsrechnungen berücksichtigt, deren Positionen einem Rechnungskandidaten zugeordnet sind.',
    Help='Der Report listet je Verkaufsrechnungsposition eine Zeile auf. Folgende Rechnungen und Positionen werden ausgeschlossen:
- Es werden nur abgeschlossene Rechnungen berücksichtigt; Entwürfe, stornierte und ungültige Rechnungen werden ausgeschlossen.
- Es werden nur Verkaufsrechnungen berücksichtigt; Lieferanten-/Eingangsrechnungen werden ausgeschlossen. Gutschriften werden mit negierten Mengen und Beträgen ausgewiesen.
- Es werden nur Rechnungspositionen mit zugeordnetem Rechnungskandidaten angezeigt. Rechnungspositionen ohne Rechnungskandidaten (z. B. manuell erfasste Positionen) werden nicht angezeigt, da das zum Filtern verwendete Lieferdatum aus dem Rechnungskandidaten stammt.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-04 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Process_ID=585439
;

UPDATE AD_Process base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- de_DE
UPDATE AD_Process_Trl
SET Description='Exportiert Verkaufsrechnungs-Positionsdetails inkl. Produkt, Kunde, Vertriebsmitarbeiter, Liefer-/Rechnungsdatum, Mengen und Beträge.
Filterbar nach Rechnungs- und Lieferdatum.
Hinweis: Es werden nur abgeschlossene Verkaufsrechnungen berücksichtigt, deren Positionen einem Rechnungskandidaten zugeordnet sind.',
    Help='Der Report listet je Verkaufsrechnungsposition eine Zeile auf. Folgende Rechnungen und Positionen werden ausgeschlossen:
- Es werden nur abgeschlossene Rechnungen berücksichtigt; Entwürfe, stornierte und ungültige Rechnungen werden ausgeschlossen.
- Es werden nur Verkaufsrechnungen berücksichtigt; Lieferanten-/Eingangsrechnungen werden ausgeschlossen. Gutschriften werden mit negierten Mengen und Beträgen ausgewiesen.
- Es werden nur Rechnungspositionen mit zugeordnetem Rechnungskandidaten angezeigt. Rechnungspositionen ohne Rechnungskandidaten (z. B. manuell erfasste Positionen) werden nicht angezeigt, da das zum Filtern verwendete Lieferdatum aus dem Rechnungskandidaten stammt.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-08-04 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Process_ID=585439
;

UPDATE AD_Process base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;
