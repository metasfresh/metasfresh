-- pain.001.001.09.ch.03 export: wording of the incomplete-creditor-address messages (5829520).
-- German: "(fehlende Angaben: ...)" instead of "(es fehlt: ...)", whose singular verb does not fit "Ort und Land".
-- Payment type X also covers foreign-currency payments to a CH/LI IBAN (V1), so it is described as "foreign currency or foreign country", not as "foreign payment".

-- 1. SEPA_Export_CreditorAddressIncomplete
UPDATE AD_Message SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (fehlende Angaben: {2}). Für Zahlungen der Zahlungsart X (Fremdwährung oder Ausland) verlangt das Format pain.001.001.09.ch.03 Ort und Land. Bitte vervollständigen Sie die Adresse.', Updated=TO_TIMESTAMP('2026-10-16 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545930
;

UPDATE AD_Message_Trl SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (fehlende Angaben: {2}). Für Zahlungen der Zahlungsart X (Fremdwährung oder Ausland) verlangt das Format pain.001.001.09.ch.03 Ort und Land. Bitte vervollständigen Sie die Adresse.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545930
;

UPDATE AD_Message_Trl SET MsgText='Business partner {0}, {1}: the address is incomplete (missing: {2}). For payments of payment type X (foreign currency or foreign country), the format pain.001.001.09.ch.03 requires town and country. Please complete the address.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545930
;

-- 2. SEPA_Export_CreditorAddressOmitted (en_US unchanged)
UPDATE AD_Message SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (fehlende Angaben: {2}) und wurde nicht in die Zahlungsdatei übernommen, da das Format pain.001.001.09.ch.03 nur Adressen mit Ort und Land zulässt. Bitte vervollständigen Sie die Adresse.', Updated=TO_TIMESTAMP('2026-10-16 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545931
;

UPDATE AD_Message_Trl SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (fehlende Angaben: {2}) und wurde nicht in die Zahlungsdatei übernommen, da das Format pain.001.001.09.ch.03 nur Adressen mit Ort und Land zulässt. Bitte vervollständigen Sie die Adresse.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545931
;
