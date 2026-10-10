-- Boni: a per-unit refund amount in another currency than the refunded turnover puts the refund invoice candidate into error
-- IDs allocated from the central ID server on 2026-10-10: AD_Message 545923, AD_MigrationScript 5829100
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545923 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-10 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Die Rückvergütung der Vertragsbedingung {0} kann nicht berechnet werden: der Betrag pro Einheit ist in {1}, die vergüteten Umsätze sind in {2}. Bitte die Währung der Rückvergütungszeile in der Vertragsbedingung auf {2} korrigieren; die Rückvergütung wird danach neu berechnet.', 'E',
        TO_TIMESTAMP('2026-10-10 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.contracts.refund.RefundAmountCurrencyMismatch', 'REFUND_AMOUNT_CURRENCY_MISMATCH')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545923
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The refund of the contract conditions {0} cannot be computed: the amount per unit is in {1}, but the refunded turnover is in {2}. Please correct the currency of the refund line in the contract conditions to {2}; the refund is then computed again.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-10 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545923
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-10 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545923
;
