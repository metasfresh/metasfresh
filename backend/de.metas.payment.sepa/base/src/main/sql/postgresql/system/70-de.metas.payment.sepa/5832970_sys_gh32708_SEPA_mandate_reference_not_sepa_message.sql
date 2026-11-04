-- SEPA direct debit export: refused if a mandate reference does not match the SEPA rules for it (1 to 35 characters of the SEPA character set) (it must match exactly what the debtor signed, so it is never converted).
-- Placeholders: {0} partner (value + name), {1} mandate reference.
-- An apostrophe is a quote character in the message format, so it is doubled.
-- ID server allocations: script 5832970, AD_Message 545990

-- SepaMandateReferenceNotSepa
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545990 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-04 10:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Lastschrift-Export nicht erstellt: Die Mandatsreferenz „{1}“ von {0} entspricht nicht den SEPA-Vorgaben (1 bis 35 Zeichen; erlaubt: A–Z, a–z, 0–9, Leerzeichen und + ? / - : ( ) . , ''''). Bitte die Mandatsreferenz am Mandat korrigieren.','E',TO_TIMESTAMP('2026-11-04 10:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SepaMandateReferenceNotSepa');

UPDATE AD_Message SET ErrorCode='SEPA_MANDATE_REFERENCE_NOT_SEPA', Updated=TO_TIMESTAMP('2026-11-04 10:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545990;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545990
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='SEPA direct debit export not created: The mandate reference “{1}” of {0} does not meet the SEPA rules (1 to 35 characters; allowed: A–Z, a–z, 0–9, space and + ? / - : ( ) . , ''''). Please correct the mandate reference of the mandate.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 10:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545990;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 10:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545990;
