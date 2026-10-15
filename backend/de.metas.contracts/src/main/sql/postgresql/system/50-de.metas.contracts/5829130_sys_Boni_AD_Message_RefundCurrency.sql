-- Boni: a per-unit refund is issued in the currency of its refund config, whatever the currency of the sales
-- IDs allocated from the central ID server on 2026-10-10: AD_Message 545924, 545925, 545926; AD_MigrationScript 5829130

-- the refund candidate is no longer put into error for a per-unit amount in another currency than the sales; that message is not thrown anymore
-- (it came with migration 5829100 of the same, unmerged change, which is dropped; this cleans up the databases that ran it)
DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545923;
DELETE FROM AD_Message WHERE AD_Message_ID = 545923;

-- 1. the per-unit lines of a condition share one currency
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545924 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Alle Rückvergütungszeilen mit Betrag pro Einheit einer Vertragsbedingung müssen dieselbe Währung haben.', 'E',
        TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.C_Flatrate_RefundConfig_SameCurrency', 'REFUND_CONFIG_SAME_CURRENCY')
;

-- 2. the currency of a per-unit line is fixed once it has issued a refund
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545925 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Die Währung kann nicht mehr geändert werden: Diese Rückvergütungszeile oder eine andere Zeile mit Betrag pro Einheit derselben Vertragsbedingung hat bereits eine abgerechnete Rückvergütung.', 'E',
        TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.C_Flatrate_RefundConfig_CurrencyNotChangeable', 'REFUND_CONFIG_CURRENCY_NOT_CHANGEABLE')
;

-- 3. the refund candidate is in error if its pricing system has no price list in the refund's currency
--    {0} = product value and name, {1} = currency code, {2} = pricing system name
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545926 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Die Rückvergütung in {1} kann nicht abgerechnet werden: Das Preissystem {2} hat keine Preisliste in {1} für das Produkt {0}.', 'E',
        TO_TIMESTAMP('2026-10-10 15:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.RefundProductHasNoPriceInCurrency', 'REFUND_PRODUCT_HAS_NO_PRICE_IN_CURRENCY')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID IN (545924, 545925, 545926)
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='All refund lines with an amount per unit of a contract condition need the same currency.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-10 15:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545924
;

UPDATE AD_Message_Trl
SET MsgText='The currency cannot be changed anymore: this refund line, or another line with an amount per unit of the same contract condition, has already issued an invoiced refund.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-10 15:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545925
;

UPDATE AD_Message_Trl
SET MsgText='The refund in {1} cannot be invoiced: the pricing system {2} has no price list in {1} for the product {0}.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-10 15:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545926
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 15:30:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID IN (545924, 545925, 545926)
;
