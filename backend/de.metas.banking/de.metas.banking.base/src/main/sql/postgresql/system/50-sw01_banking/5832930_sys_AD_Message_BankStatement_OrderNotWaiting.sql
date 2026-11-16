-- Error message for completing a bank statement whose line carries an order that does not wait for payment any more (e.g. already paid or closed)
-- while no active payment carries it (5832950: one message for "already paid" and "no longer waiting"):
--   ERR_BANKSTATEMENT_ORDER_NOT_WAITING
--     {0} = C_Order.DocumentNo
-- Base text German, en_US override English, de_DE / de_CH marked translated.

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545988 /*From ID Server*/, 0, TO_TIMESTAMP('2026-11-03 22:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Auftrag {0} wartet nicht mehr auf Zahlung (er ist bereits bezahlt oder abgeschlossen). Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
        'E', TO_TIMESTAMP('2026-11-03 22:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'ERR_BANKSTATEMENT_ORDER_NOT_WAITING')
;

UPDATE AD_Message
SET ErrorCode = 'BANKSTATEMENT_ORDER_NOT_WAITING', Updated = TO_TIMESTAMP('2026-11-03 22:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Message_ID = 545988
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0} is no longer waiting for payment (it is already paid or closed). No payment is created. Please clear the order on the bank statement line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 22:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545988
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 22:10:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_DE' AND AD_Message_ID = 545988
;

UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-03 22:10:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language = 'de_CH' AND AD_Message_ID = 545988
;
