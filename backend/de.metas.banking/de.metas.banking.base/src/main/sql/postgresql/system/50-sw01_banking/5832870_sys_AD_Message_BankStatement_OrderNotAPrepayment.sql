-- Error message for completing a bank statement whose line carries an order that is not an active sales prepay order:
--   ERR_BANKSTATEMENT_ORDER_NOT_A_PREPAYMENT
--     {0} = C_Order.DocumentNo
-- Base text German, en_US override English, de_DE / de_CH marked translated.

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545984 /*From ID Server*/, 0, TO_TIMESTAMP('2026-11-03 19:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Auftrag {0} ist kein aktiver Vorauszahlungs-Verkaufsauftrag. Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
        'E', TO_TIMESTAMP('2026-11-03 19:55:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ERR_BANKSTATEMENT_ORDER_NOT_A_PREPAYMENT')
;

UPDATE AD_Message
SET ErrorCode = 'BANKSTATEMENT_ORDER_NOT_A_PREPAYMENT', Updated = TO_TIMESTAMP('2026-11-03 19:55:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545984
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545984
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0} is not an active sales prepay order. No payment is created. Please clear the order on the bank statement line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 19:55:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545984
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 19:55:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545984
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 19:55:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545984
;
