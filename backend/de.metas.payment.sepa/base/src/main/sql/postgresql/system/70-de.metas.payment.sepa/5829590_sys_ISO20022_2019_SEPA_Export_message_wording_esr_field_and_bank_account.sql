-- pain.001.001.09.ch.03 export: wording fixes of messages added by 5829540.
-- 545946: name the field the user has to change by its label (C_BP_BankAccount.IsEsrAccount, AD_Element 541939: "ESR Konto" / en_US "ESR Account").
-- 545950/545951: {0} is now the whole bank account info ("<IBAN> (ID n)", or "ID n" if the account has no number), so there is no empty gap; the country moves from {2} to {1}.

-- 1. SEPA_Export_IsrNotSupported
UPDATE AD_Message SET MsgText='{0}: Das Format pain.001.001.09.ch.03 unterstützt keine ESR-Zahlungen mehr (der ESR wurde durch die QR-Rechnung abgelöst). Bitte deaktivieren Sie beim Bankkonto das Feld „ESR Konto“, erfassen Sie eine QR-IBAN oder verwenden Sie eine ISO-11649-Referenz (RF).', Updated=TO_TIMESTAMP('2026-10-16 15:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545946
;

UPDATE AD_Message_Trl SET MsgText='{0}: Das Format pain.001.001.09.ch.03 unterstützt keine ESR-Zahlungen mehr (der ESR wurde durch die QR-Rechnung abgelöst). Bitte deaktivieren Sie beim Bankkonto das Feld „ESR Konto“, erfassen Sie eine QR-IBAN oder verwenden Sie eine ISO-11649-Referenz (RF).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545946
;

UPDATE AD_Message_Trl SET MsgText='{0}: the format pain.001.001.09.ch.03 no longer supports ISR payments (the ISR was replaced by the QR-bill). Please clear the field "ESR Account" of the bank account, set a QR-IBAN or use an ISO 11649 creditor reference (RF).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545946
;

-- 2. SEPA_Export_BankAccountCountryMissing
UPDATE AD_Message SET MsgText='Bankkonto {0}: Die Adresse ist erfasst, aber das Land fehlt. Bitte erfassen Sie das Land der Adresse.', Updated=TO_TIMESTAMP('2026-10-16 15:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545950
;

UPDATE AD_Message_Trl SET MsgText='Bankkonto {0}: Die Adresse ist erfasst, aber das Land fehlt. Bitte erfassen Sie das Land der Adresse.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545950
;

UPDATE AD_Message_Trl SET MsgText='Bank account {0}: the address is set, but the country is missing. Please set the country of the address.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545950
;

-- 3. SEPA_Export_BankAccountCountryInvalid
UPDATE AD_Message SET MsgText='Bankkonto {0}: Das Land {1} ist kein gültiger zweistelliger ISO-Ländercode (z. B. CH). Bitte erfassen Sie den Ländercode.', Updated=TO_TIMESTAMP('2026-10-16 15:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545951
;

UPDATE AD_Message_Trl SET MsgText='Bankkonto {0}: Das Land {1} ist kein gültiger zweistelliger ISO-Ländercode (z. B. CH). Bitte erfassen Sie den Ländercode.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545951
;

UPDATE AD_Message_Trl SET MsgText='Bank account {0}: the country {1} is not a valid two-letter ISO country code (e.g. CH). Please set the country code.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 15:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545951
;
