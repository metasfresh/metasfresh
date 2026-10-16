-- pain.001.001.09.ch.03 export: translated messages for the errors of the marshaller (and of SepaUtils, which the .03 and pain.008 marshallers share).
-- Header errors: {0} the SEPA_Export's DocumentNo. Line errors: {0} partner and bank account of the line (SepaUtils.createInfo).
-- The texts contain no apostrophes, because an apostrophe is a quote character in the message format.

-- 1. errors of the export header
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545937 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Export {0}: Es gibt keine aktiven Zeilen ohne Fehler, die exportiert werden können.','E',TO_TIMESTAMP('2026-10-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_NoLines')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545938 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Export {0}: Der Name des Auftraggebers (der zahlenden Organisation) fehlt. Er wird aus dem Namen des Geschäftspartners der Organisation übernommen. Bitte erfassen Sie diesen Namen und erstellen Sie den SEPA-Export neu.','E',TO_TIMESTAMP('2026-10-16 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_DebtorNameMissing')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545939 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Export {0}: Die Bank des Auftraggebers kann nicht bestimmt werden, weil weder ein BIC noch eine IBAN aus der Schweiz oder Liechtenstein erfasst ist. Bitte erfassen Sie den BIC oder die IBAN des Auftraggeberkontos.','E',TO_TIMESTAMP('2026-10-16 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_DebtorBankUnidentified')
;

-- the debtor account as the owner of a too short IBAN (parameter {1} of SEPA_Export_IbanTooShort)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545940 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Auftraggeberkonto des SEPA-Exports {0}','I',TO_TIMESTAMP('2026-10-16 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_DebtorAccountOfExport')
;

-- 2. errors of a payment (export line)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545941 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Der Betrag {1} ist ungültig; er muss größer als 0 sein.','E',TO_TIMESTAMP('2026-10-16 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_InvalidAmount')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545942 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Der Betrag {1} hat mehr Nachkommastellen, als die Währung {2} erlaubt (höchstens {3}).','E',TO_TIMESTAMP('2026-10-16 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_AmountTooManyDecimals')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545943 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Für eine Zahlung der Zahlungsart X (Fremdwährung oder Ausland) auf ein Konto ohne IBAN aus der Schweiz oder Liechtenstein ist für die Bank des Begünstigten weder ein BIC noch eine vollständige Bankadresse (Name, Ort und Land) erfasst. Bitte erfassen Sie den BIC oder die Adresse der Bank.','E',TO_TIMESTAMP('2026-10-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_CreditorBankUnidentified')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545944 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Der Name des Begünstigten fehlt. Bitte erfassen Sie den Kontoinhaber des Bankkontos oder den Namen des Geschäftspartners.','E',TO_TIMESTAMP('2026-10-16 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_CreditorNameMissing')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545945 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Das Bankkonto hat eine QR-IBAN, aber die Währung ist {1}. Zahlungen an eine QR-IBAN sind nur in CHF oder EUR möglich.','E',TO_TIMESTAMP('2026-10-16 10:00:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_QrIbanCurrencyNotSupported')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545946 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Das Format pain.001.001.09.ch.03 unterstützt keine ESR-Zahlungen mehr (der ESR wurde durch die QR-Rechnung abgelöst). Bitte entfernen Sie beim Bankkonto die ESR-Markierung, erfassen Sie eine QR-IBAN oder verwenden Sie eine ISO-11649-Referenz (RF).','E',TO_TIMESTAMP('2026-10-16 10:00:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_IsrNotSupported')
;

-- 3. the reasons of SEPA_Export_InvalidReference (its parameter {0}); {1} the reference or SEPA_Export_NoStructuredReference
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545947 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Das Bankkonto hat nur eine QR-IBAN, daher ist eine gültige QR-Referenz (27 Ziffern inkl. Prüfziffer) nötig. Angegebene Referenz: {1}.','I',TO_TIMESTAMP('2026-10-16 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_QrReferenceRequired')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545948 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}: Die Referenz {1} hat das Format einer QR-Referenz (27 Ziffern), aber ihre Prüfziffer ist falsch. Bitte prüfen Sie die Referenz.','I',TO_TIMESTAMP('2026-10-16 10:00:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_QrReferenceCheckDigitWrong')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545949 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','keine','I',TO_TIMESTAMP('2026-10-16 10:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_NoStructuredReference')
;

-- 4. SepaUtils (shared with the .03 and pain.008 marshallers): {0} IBAN, QR-IBAN or account number, {1} C_BP_BankAccount_ID, {2} the country
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545950 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Bankkonto {0} (ID {1}): Die Adresse ist erfasst, aber das Land fehlt. Bitte erfassen Sie das Land der Adresse.','E',TO_TIMESTAMP('2026-10-16 10:00:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_BankAccountCountryMissing')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545951 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Bankkonto {0} (ID {1}): Das Land {2} ist kein gültiger zweistelliger ISO-Ländercode (z. B. CH). Bitte erfassen Sie den Ländercode.','E',TO_TIMESTAMP('2026-10-16 10:00:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_BankAccountCountryInvalid')
;

-- {0} the IBAN, {1} the record it belongs to
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545952 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-16 10:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Die IBAN {0} ({1}) ist zu kurz. Bitte prüfen Sie, ob es sich um eine gültige IBAN handelt.','E',TO_TIMESTAMP('2026-10-16 10:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_IbanTooShort')
;

-- 5. seed the translations of all system languages with the German base text
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID BETWEEN 545937 AND 545952
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 6. en_US
UPDATE AD_Message_Trl SET MsgText='SEPA export {0}: there are no active lines without errors that could be exported.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545937
;

UPDATE AD_Message_Trl SET MsgText='SEPA export {0}: the name of the debtor (the paying organisation) is missing. It is taken from the name of the business partner of the organisation. Please set that name and create the SEPA export again.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545938
;

UPDATE AD_Message_Trl SET MsgText='SEPA export {0}: the bank of the debtor cannot be determined, because neither a BIC nor an IBAN from Switzerland or Liechtenstein is set. Please set the BIC or the IBAN of the debtor account.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545939
;

UPDATE AD_Message_Trl SET MsgText='debtor account of SEPA export {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545940
;

UPDATE AD_Message_Trl SET MsgText='{0}: the amount {1} is invalid; it must be greater than 0.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545941
;

UPDATE AD_Message_Trl SET MsgText='{0}: the amount {1} has more decimal places than the currency {2} allows (at most {3}).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545942
;

UPDATE AD_Message_Trl SET MsgText='{0}: for a payment of payment type X (foreign currency or foreign country) to an account without an IBAN from Switzerland or Liechtenstein, the bank of the creditor has neither a BIC nor a complete bank address (name, town and country). Please set the BIC or the address of the bank.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545943
;

UPDATE AD_Message_Trl SET MsgText='{0}: the name of the creditor is missing. Please set the account holder of the bank account or the name of the business partner.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545944
;

UPDATE AD_Message_Trl SET MsgText='{0}: the bank account has a QR-IBAN, but the currency is {1}. Payments to a QR-IBAN are only possible in CHF or EUR.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:28','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545945
;

UPDATE AD_Message_Trl SET MsgText='{0}: the format pain.001.001.09.ch.03 no longer supports ISR payments (the ISR was replaced by the QR-bill). Please clear the ISR flag of the bank account, set a QR-IBAN or use an ISO 11649 creditor reference (RF).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:29','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545946
;

UPDATE AD_Message_Trl SET MsgText='{0}: the bank account has only a QR-IBAN, so a valid QR reference (27 digits incl. check digit) is required. Given reference: {1}.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545947
;

UPDATE AD_Message_Trl SET MsgText='{0}: the reference {1} has the format of a QR reference (27 digits), but its check digit is wrong. Please check the reference.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545948
;

UPDATE AD_Message_Trl SET MsgText='none', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545949
;

UPDATE AD_Message_Trl SET MsgText='Bank account {0} (ID {1}): the address is set, but the country is missing. Please set the country of the address.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:33','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545950
;

UPDATE AD_Message_Trl SET MsgText='Bank account {0} (ID {1}): the country {2} is not a valid two-letter ISO country code (e.g. CH). Please set the country code.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545951
;

UPDATE AD_Message_Trl SET MsgText='The IBAN {0} ({1}) is too short. Please check that it is a valid IBAN.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:35','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545952
;

-- 7. de_DE: the base text
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:40','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID BETWEEN 545937 AND 545952
;

-- 8. de_CH: Swiss business German; only 545941 contains a "ß" ("größer" -> "grösser"), the others equal the base text
UPDATE AD_Message_Trl SET MsgText='{0}: Der Betrag {1} ist ungültig; er muss grösser als 0 sein.', Updated=TO_TIMESTAMP('2026-10-16 10:00:50','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545941
;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-16 10:00:51','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID BETWEEN 545937 AND 545952
;
