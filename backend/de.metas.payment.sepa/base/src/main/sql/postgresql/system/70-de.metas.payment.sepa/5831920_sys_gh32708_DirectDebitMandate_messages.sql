-- SEPA direct debit: messages of the mandate checks (type, validity, one-off use) done when a SEPA export of direct debits is created.
-- Placeholders: {0} partner (value + name), {1} IBAN or mandate reference, {2} / {3} dates or line count; see the texts.
-- The texts contain no apostrophes, because an apostrophe is a quote character in the message format.
-- ID server allocations: script 5831920, AD_Message 545968 - 545972

-- DirectDebitMandateCheckFailed
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545968 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 11:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Lastschrift-Export nicht erstellt. Folgende Zeilen haben kein gültiges Lastschriftmandat:','E',TO_TIMESTAMP('2026-10-28 11:30:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.DirectDebitMandateCheckFailed');

UPDATE AD_Message SET ErrorCode='DD_MANDATE_CHECK_FAILED', Updated=TO_TIMESTAMP('2026-10-28 11:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545968;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545968
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='SEPA direct debit export not created. These lines have no valid direct debit mandate:', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545968;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545968;

-- DirectDebitMandateMissing
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545969 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 11:30:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}, Bankkonto {1}: kein aktives Lastschriftmandat. Das unterschriebene Mandat des Zahlers auf diesem Bankkonto erfassen oder die Zeile aus der Zahlungsauswahl entfernen.','E',TO_TIMESTAMP('2026-10-28 11:30:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.DirectDebitMandateMissing');

UPDATE AD_Message SET ErrorCode='DD_MANDATE_MISSING', Updated=TO_TIMESTAMP('2026-10-28 11:30:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545969;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545969
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='{0}, bank account {1}: no active direct debit mandate. Enter the signed mandate of the payer on this bank account, or remove the line from the pay selection.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545969;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545969;

-- DirectDebitMandateExpired
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545970 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 11:30:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}, Mandat {1}: abgelaufen – letzte Nutzung bzw. Unterschrift am {2} liegt mehr als 36 Monate vor dem Einzugsdatum {3}. Wurde das Mandat später genutzt (z. B. im Vorsystem), das Datum „Letzte Nutzung“ korrigieren; sonst das alte Mandat deaktivieren und das neu unterschriebene Mandat erfassen; oder die Zeile aus der Zahlungsauswahl entfernen.','E',TO_TIMESTAMP('2026-10-28 11:30:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.DirectDebitMandateExpired');

UPDATE AD_Message SET ErrorCode='DD_MANDATE_EXPIRED', Updated=TO_TIMESTAMP('2026-10-28 11:30:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545970;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545970
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='{0}, mandate {1}: expired – last used or signed on {2}, more than 36 months before the collection date {3}. If the mandate was used more recently (e.g. in the previous system), correct its last-used date; otherwise deactivate the old mandate and enter the newly signed one; or remove the line from the pay selection.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545970;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545970;

-- DirectDebitMandateOneOffUsed
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545971 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 11:30:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}, Mandat {1}: einmaliges Mandat, bereits genutzt am {2}. Ein neues Mandat erfassen oder die Zeile aus der Zahlungsauswahl entfernen.','E',TO_TIMESTAMP('2026-10-28 11:30:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.DirectDebitMandateOneOffUsed');

UPDATE AD_Message SET ErrorCode='DD_MANDATE_ONEOFF_USED', Updated=TO_TIMESTAMP('2026-10-28 11:30:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545971;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545971
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='{0}, mandate {1}: one-off mandate, already used on {2}. Enter a new mandate, or remove the line from the pay selection.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545971;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545971;

-- DirectDebitMandateOneOffMultipleLines
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545972 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 11:30:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','{0}, Mandat {1}: einmaliges Mandat auf {2} Zeilen dieser Zahlungsauswahl; es erlaubt nur einen Einzug. Für die weiteren Einzüge ein neues Mandat erfassen oder die zusätzlichen Zeilen aus der Zahlungsauswahl entfernen.','E',TO_TIMESTAMP('2026-10-28 11:30:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.DirectDebitMandateOneOffMultipleLines');

UPDATE AD_Message SET ErrorCode='DD_MANDATE_ONEOFF_MULTI_LINES', Updated=TO_TIMESTAMP('2026-10-28 11:30:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545972;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545972
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='{0}, mandate {1}: one-off mandate on {2} lines of this pay selection; it allows a single collection. Enter a new mandate for the other collections, or remove the extra lines from the pay selection.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545972;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 11:30:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545972;
