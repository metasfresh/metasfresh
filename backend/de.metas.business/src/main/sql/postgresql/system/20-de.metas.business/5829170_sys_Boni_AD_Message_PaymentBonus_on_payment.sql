-- Boni: messages for a payment bonus that a payment cannot book (bonus no longer deductible / above the open amount minus Skonto)
-- IDs allocated from the central ID server on 2026-10-12: AD_Message 545927, 545928

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545927 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-12 11:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Für Rechnung {0} kann kein Zahlungsbonus mehr abgezogen werden: {1}', 'E',
        TO_TIMESTAMP('2026-10-12 11:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.invoice.paymentbonus.PaymentBonusNotDeductible', 'PAYMENT_BONUS_NOT_DEDUCTIBLE')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545927
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='No payment bonus can be deducted for invoice {0} any more: {1}', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 11:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545927
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-12 11:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545927
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545928 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-12 11:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Der Zahlungsbonus {0} ist höher als der offene Betrag abzüglich Skonto {1} der Rechnung {2}', 'E',
        TO_TIMESTAMP('2026-10-12 11:12:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.invoice.paymentbonus.PaymentBonusAboveOpenAmt', 'PAYMENT_BONUS_ABOVE_OPEN_AMT')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545928
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus {0} is higher than the open amount minus Skonto {1} of invoice {2}', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 11:12:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545928
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-12 11:12:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545928
;
