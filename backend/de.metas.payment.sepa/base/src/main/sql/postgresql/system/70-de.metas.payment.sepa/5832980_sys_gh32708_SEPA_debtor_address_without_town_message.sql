-- SEPA direct debit export: refused if the debtor address has no town although it is mandatory, because the creditor's or the debtor's bank is in a SEPA country outside the EEA (e.g. Switzerland, United Kingdom).
-- Placeholders: {0} partner (value + name).
-- ID server allocations: script 5832980, AD_Message 545991

-- SepaDebtorAddressWithoutTown
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545991 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-04 14:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','SEPA-Lastschrift-Export nicht erstellt: Die Rechnungsadresse von {0} hat keinen Ort. Liegt die Bank des Zahlungspflichtigen oder des Zahlungsempfängers in einem SEPA-Land außerhalb des EWR (z. B. Schweiz, Vereinigtes Königreich), ist die Adresse des Zahlungspflichtigen mit Ort Pflicht. Eine Rechnung ohne vollständige Anschrift ist ohnehin zu berichtigen: bitte die Rechnung stornieren und mit vollständiger Adresse neu erstellen, oder die Zeile aus der Zahlungsauswahl entfernen.','E',TO_TIMESTAMP('2026-11-04 14:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa.SepaDebtorAddressWithoutTown');

UPDATE AD_Message SET ErrorCode='SEPA_DEBTOR_ADDRESS_WITHOUT_TOWN', Updated=TO_TIMESTAMP('2026-11-04 14:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545991;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545991
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='SEPA direct debit export not created: The invoice address of {0} has no town. If the bank of the debtor or of the creditor is in a SEPA country outside the EEA (e.g. Switzerland, United Kingdom), the debtor address with town is mandatory. An invoice without a complete address has to be corrected anyway: please reverse the invoice and create it again with the complete address, or remove the line from the pay selection.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 14:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545991;

UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-04 14:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545991;
