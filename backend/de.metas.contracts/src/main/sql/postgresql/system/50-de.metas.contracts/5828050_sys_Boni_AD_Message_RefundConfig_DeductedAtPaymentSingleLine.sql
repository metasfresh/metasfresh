-- Boni: a condition that is deducted at payment has exactly one active refund config line, with minimum quantity 0
-- IDs allocated from the central ID server on 2026-10-06: AD_Message 545906
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545906 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Eine Vertragsbedingung mit Abzug bei Zahlung hat genau eine aktive Rückvergütungszeile, mit Mindestmenge 0. Ein weiterer Bonus braucht eine eigene Vertragsbedingung.', 'E',
        TO_TIMESTAMP('2026-10-06 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.constracts.refund.C_Flatrate_RefundConfig_DeductedAtPaymentSingleLine', 'DEDUCTED_AT_PAYMENT_SINGLE_LINE')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545906
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='A contract condition that is deducted at payment has exactly one active refund line, with minimum quantity 0. Another bonus needs a condition of its own.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545906
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 15:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545906
;
