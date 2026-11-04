-- SEPA direct debit export: refused if a mandatory text (name, document number) has no character of the SEPA character set.
-- Placeholders: {0} translated name of the element (Name, DocumentNo), {1} partner (value + name) or organisation, {2} original text.
-- The texts contain no apostrophes, because an apostrophe is a quote character in the message format.
-- ID server allocations: script 5832960, AD_Message 545989

-- SepaTextWithoutSepaCharacters
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545989 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-04 09:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Lastschrift-Export nicht erstellt: {0} von {1} enthält keine Zeichen des SEPA-Zeichensatzes („{2}“). Bitte in lateinischen Buchstaben erfassen.','E',TO_TIMESTAMP('2026-11-04 09:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SepaTextWithoutSepaCharacters');

UPDATE AD_Message SET ErrorCode='SEPA_TEXT_WITHOUT_SEPA_CHARACTERS', Updated=TO_TIMESTAMP('2026-11-04 09:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545989;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545989
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='SEPA direct debit export not created: {0} of {1} contains no characters of the SEPA character set (“{2}”). Please enter it in Latin letters.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 09:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545989;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 09:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545989;
