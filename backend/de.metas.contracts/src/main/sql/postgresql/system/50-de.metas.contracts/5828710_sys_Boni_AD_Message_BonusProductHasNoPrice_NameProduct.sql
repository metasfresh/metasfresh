-- Boni: the "bonus product has no price" error names the bonus product ({0} = product value and name), so the user knows which product to price

UPDATE AD_Message
SET MsgText='Das Bonusprodukt {0} hat keinen Preis in der Preisliste der Rechnung, daher ist seine Steuer nicht bekannt.',
    Updated=TO_TIMESTAMP('2026-10-09 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545919
;

-- languages without an own translation (e.g. fr_CH) carry the German base text
UPDATE AD_Message_Trl
SET MsgText='Das Bonusprodukt {0} hat keinen Preis in der Preisliste der Rechnung, daher ist seine Steuer nicht bekannt.',
    Updated=TO_TIMESTAMP('2026-10-09 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545919 AND (IsTranslated = 'N' OR AD_Language IN ('de_DE', 'de_CH'))
;

UPDATE AD_Message_Trl
SET MsgText='The bonus product {0} has no price in the price list of the invoice, so its tax is unknown.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-09 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545919
;
