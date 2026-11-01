-- Boni: a payment bonus typed in the WebUI payment allocation view for an invoice whose customer has no bonus to deduct
-- IDs allocated from the central ID server on 2026-10-08: AD_Message 545914

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545914 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Der Kunde hat für die Rechnung {0} keinen Bonus, den er bei der Zahlung abziehen kann.', 'E',
        TO_TIMESTAMP('2026-10-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusNothingToDeduct', 'PAYMENT_BONUS_NOTHING_TO_DEDUCT')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545914
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The customer has no bonus to deduct when paying invoice {0}.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-08 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545914
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-08 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545914
;
