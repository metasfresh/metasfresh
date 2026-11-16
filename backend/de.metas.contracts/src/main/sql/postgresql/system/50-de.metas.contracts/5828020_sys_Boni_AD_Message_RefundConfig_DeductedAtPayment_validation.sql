-- Boni: error messages of the refund config validation of a bonus that is deducted at payment
-- IDs allocated from the central ID server on 2026-10-06: AD_Message 545899, 545900

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545899 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Ein Bonus, der bei der Zahlung abgezogen wird, braucht die Rückvergütungsbasis Prozent und ein Bonusprodukt.', 'E',
        TO_TIMESTAMP('2026-10-06 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.constracts.refund.C_Flatrate_RefundConfig_DeductedAtPaymentRequiresPercentageAndBonusProduct', 'DEDUCTED_AT_PAYMENT_NEEDS_PERCENT_BONUS')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545899
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='A bonus that is deducted at payment needs the refund base percentage and a bonus product.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545899
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 11:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545899
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545900 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Abzug bei Zahlung kann nicht mehr geändert werden, weil es zu dieser Vertragsbedingung bereits fertiggestellte Verträge gibt.', 'E',
        TO_TIMESTAMP('2026-10-06 11:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.constracts.refund.C_Flatrate_RefundConfig_DeductedAtPaymentNotChangeable', 'DEDUCTED_AT_PAYMENT_NOT_CHANGEABLE')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545900
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='Deducted at payment can no longer be changed, because there are already completed contracts with this contract condition.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 11:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545900
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 11:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545900
;
