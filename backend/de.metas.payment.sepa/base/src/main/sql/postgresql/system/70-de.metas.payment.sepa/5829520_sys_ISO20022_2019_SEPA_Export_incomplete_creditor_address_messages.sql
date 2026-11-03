-- pain.001.001.09.ch.03 export: messages for an incomplete creditor address (town or country missing).
-- Payment type X fails with SEPA_Export_CreditorAddressIncomplete; D and S leave the address out and warn with SEPA_Export_CreditorAddressOmitted.
-- Parameters of both: {0} partner "search key (name)", {1} address source (SEPA_Export_AddressOf*), {2} missing parts (SEPA_Export_MissingAddressPart*).

-- 1. the incomplete address of a foreign payment (error)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545930 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (es fehlt: {2}). Für Auslandszahlungen (Zahlungsart X) verlangt das Format pain.001.001.09.ch.03 Ort und Land. Bitte vervollständigen Sie die Adresse.','E',TO_TIMESTAMP('2026-10-15 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_CreditorAddressIncomplete')
;

-- 2. the incomplete address that was left out of a domestic or SEPA payment (warning)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545931 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (es fehlt: {2}) und wurde nicht in die Zahlungsdatei übernommen, da das Format pain.001.001.09.ch.03 nur Adressen mit Ort und Land zulässt. Bitte vervollständigen Sie die Adresse.','I',TO_TIMESTAMP('2026-10-15 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_CreditorAddressOmitted')
;

-- 3. the address sources
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545932 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Standort {0}','I',TO_TIMESTAMP('2026-10-15 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_AddressOfLocation')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545933 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Bankkonto {0}','I',TO_TIMESTAMP('2026-10-15 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_AddressOfBankAccount')
;

-- 4. the missing address parts
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545934 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Ort','I',TO_TIMESTAMP('2026-10-15 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_MissingAddressPartTown')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545935 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Land','I',TO_TIMESTAMP('2026-10-15 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_MissingAddressPartCountry')
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545936 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-15 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Ort und Land','I',TO_TIMESTAMP('2026-10-15 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SEPA_Export_MissingAddressPartsTownAndCountry')
;

-- 5. seed the translations of all system languages with the German base text
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID IN (545930,545931,545932,545933,545934,545935,545936)
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 6. en_US
UPDATE AD_Message_Trl SET MsgText='Business partner {0}, {1}: the address is incomplete (missing: {2}). For foreign payments (payment type X), the format pain.001.001.09.ch.03 requires town and country. Please complete the address.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545930
;

UPDATE AD_Message_Trl SET MsgText='Business partner {0}, {1}: the address is incomplete (missing: {2}) and was left out of the payment file, because the format pain.001.001.09.ch.03 only allows addresses with town and country. Please complete the address.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545931
;

UPDATE AD_Message_Trl SET MsgText='location {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545932
;

UPDATE AD_Message_Trl SET MsgText='bank account {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545933
;

UPDATE AD_Message_Trl SET MsgText='town', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545934
;

UPDATE AD_Message_Trl SET MsgText='country', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545935
;

UPDATE AD_Message_Trl SET MsgText='town and country', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545936
;

-- 7. de_DE: the base text
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID IN (545930,545931,545932,545933,545934,545935,545936)
;

-- 8. de_CH: Swiss business German (the texts contain no "ß", so they equal the base text)
UPDATE AD_Message_Trl SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (es fehlt: {2}). Für Auslandszahlungen (Zahlungsart X) verlangt das Format pain.001.001.09.ch.03 Ort und Land. Bitte vervollständigen Sie die Adresse.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545930
;

UPDATE AD_Message_Trl SET MsgText='Geschäftspartner {0}, {1}: Die Adresse ist unvollständig (es fehlt: {2}) und wurde nicht in die Zahlungsdatei übernommen, da das Format pain.001.001.09.ch.03 nur Adressen mit Ort und Land zulässt. Bitte vervollständigen Sie die Adresse.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545931
;

UPDATE AD_Message_Trl SET MsgText='Standort {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545932
;

UPDATE AD_Message_Trl SET MsgText='Bankkonto {0}', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:33','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545933
;

UPDATE AD_Message_Trl SET MsgText='Ort', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545934
;

UPDATE AD_Message_Trl SET MsgText='Land', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:35','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545935
;

UPDATE AD_Message_Trl SET MsgText='Ort und Land', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-15 10:00:36','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545936
;
