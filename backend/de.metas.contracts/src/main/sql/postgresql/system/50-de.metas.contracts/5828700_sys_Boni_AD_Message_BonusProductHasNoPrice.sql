-- Boni: the bonus that the customer may deduct at payment cannot be computed, because the bonus product has no price in the invoice's price list (its tax category is unknown)
-- IDs allocated from the central ID server on 2026-10-09: AD_Message 545919

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545919 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Das Bonusprodukt hat keinen Preis in der Preisliste der Rechnung, daher ist seine Steuer nicht bekannt.', 'E',
        TO_TIMESTAMP('2026-10-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.paymentdeduction.BonusProductHasNoPrice', 'BONUS_PRODUCT_HAS_NO_PRICE')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545919
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The bonus product has no price in the price list of the invoice, so its tax is unknown.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-09 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545919
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-09 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545919
;
