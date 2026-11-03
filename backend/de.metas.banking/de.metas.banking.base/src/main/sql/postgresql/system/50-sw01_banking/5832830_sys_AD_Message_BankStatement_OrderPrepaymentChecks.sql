-- Two error messages for completing a bank statement whose line carries a prepay sales order (the payment gets the order):
--   ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH: full prepayment only, the line amount / currency differs from the order total.
--     {0} = C_Order.DocumentNo, {1} = order total with currency, e.g. 123.52 EUR
--   ERR_BANKSTATEMENT_ORDER_ALREADY_PAID: the order is already carried by a payment (an order that simply does not wait for payment any more
--     gets ERR_BANKSTATEMENT_ORDER_NOT_WAITING, see 5832930).
--     {0} = C_Order.DocumentNo, {1} = C_Payment.DocumentNo of the carrying payment
-- Base text German, en_US override English, de_DE / de_CH marked translated.

-- ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545982 /*From ID Server*/, 0, TO_TIMESTAMP('2026-11-03 17:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Auftrag {0}: Vorauszahlungen werden nur in voller Höhe unterstützt. Betrag und Währung der Auszugsposition müssen dem Auftragsbetrag ({1}) entsprechen. Bitte den Auftrag in der Auszugsposition entfernen oder die Position korrigieren.',
        'E', TO_TIMESTAMP('2026-11-03 17:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH')
;

UPDATE AD_Message
SET ErrorCode = 'BANKSTATEMENT_ORDER_AMOUNT_MISMATCH', Updated = TO_TIMESTAMP('2026-11-03 17:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545982
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545982
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0}: only full prepayments are supported. The amount and currency of the bank statement line must match the order total ({1}). Please clear the order on the bank statement line or correct the line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545982
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:10:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545982
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:10:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545982
;

-- ERR_BANKSTATEMENT_ORDER_ALREADY_PAID
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545983 /*From ID Server*/, 0, TO_TIMESTAMP('2026-11-03 17:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Auftrag {0} ist bereits durch die Zahlung {1} bezahlt. Es wird keine weitere Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
        'E', TO_TIMESTAMP('2026-11-03 17:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ERR_BANKSTATEMENT_ORDER_ALREADY_PAID')
;

UPDATE AD_Message
SET ErrorCode = 'BANKSTATEMENT_ORDER_ALREADY_PAID', Updated = TO_TIMESTAMP('2026-11-03 17:11:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545983
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545983
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0} is already paid by payment {1}. No further payment is created. Please clear the order on the bank statement line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:11:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545983
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:11:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545983
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 17:11:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545983
;
