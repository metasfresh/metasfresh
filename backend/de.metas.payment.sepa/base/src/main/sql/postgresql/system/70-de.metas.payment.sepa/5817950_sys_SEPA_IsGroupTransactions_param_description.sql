-- Update the description of the SEPA Export process parameter "IsGroupTransactions"
-- (AD_Process_Para 543086, process C_PaySelection_SEPA_XmlExport) to state that QR-IBAN
-- (QRR) payments are NOT aggregated even when grouping is on ("bei nicht QR-Überweisungen"),
-- and to correct the spelling "SEPA-Gutschiftung" -> "SEPA-Gutschrift".
-- This parameter has no AD_Element_ID, so its description lives directly on
-- AD_Process_Para / AD_Process_Para_Trl (direct update is correct here).

UPDATE AD_Process_Para
SET Description = 'Ermöglicht die Gruppierung mehrerer Rechnungen pro Lieferant zu einer einzigen aggregierten SEPA-Gutschrift. Wenn aktiviert (Standard: Ja), erstellt der SEPA-Export bei nicht QR-Überweisungen eine Transaktion pro Partner statt pro Rechnung, wodurch Bankgebühren und Unübersichtlichkeit in Kontoauszügen reduziert werden. Die interne Nachverfolgbarkeit bleibt in metasfresh vollständig erhalten, ergänzt durch einen detaillierten Excel-Prüfbericht mit rechnungsgenauen Aufschlüsselungen pro Zahlung.',
    Updated = TO_TIMESTAMP('2026-08-07 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543086;

UPDATE AD_Process_Para_Trl
SET Description = 'Ermöglicht die Gruppierung mehrerer Rechnungen pro Lieferant zu einer einzigen aggregierten SEPA-Gutschrift. Wenn aktiviert (Standard: Ja), erstellt der SEPA-Export bei nicht QR-Überweisungen eine Transaktion pro Partner statt pro Rechnung, wodurch Bankgebühren und Unübersichtlichkeit in Kontoauszügen reduziert werden. Die interne Nachverfolgbarkeit bleibt in metasfresh vollständig erhalten, ergänzt durch einen detaillierten Excel-Prüfbericht mit rechnungsgenauen Aufschlüsselungen pro Zahlung.',
    Updated = TO_TIMESTAMP('2026-08-07 10:00:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543086
  AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Process_Para_Trl
SET Description = 'Enables grouping of multiple invoices per vendor into a single aggregated SEPA credit transfer. When checked (default: Yes), the SEPA export creates one transaction per partner instead of one per invoice for non-QR credit transfers, reducing bank fees and statement clutter. Internal traceability remains fully preserved in metasfresh, with a detailed Excel audit report providing invoice-level breakdowns per payment.',
    Updated = TO_TIMESTAMP('2026-08-07 10:00:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 543086
  AND AD_Language = 'en_US';
