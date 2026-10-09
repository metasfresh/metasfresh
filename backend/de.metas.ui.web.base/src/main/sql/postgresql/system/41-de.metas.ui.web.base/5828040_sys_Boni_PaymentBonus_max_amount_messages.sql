-- Boni: the payment bonus is limited to what the customer pays (the open amount minus discount and fees), not to the open amount; the messages say so
-- 2026-10-06
UPDATE AD_Message
SET MsgText   = 'Der Zahlungsbonus {0} ist größer als der Betrag {1}, den der Kunde für die Rechnung {2} nach Skonto und Gebühren zahlt.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545901
;
UPDATE AD_Message_Trl
SET MsgText   = 'Der Zahlungsbonus {0} ist größer als der Betrag {1}, den der Kunde für die Rechnung {2} nach Skonto und Gebühren zahlt.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545901 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl
SET MsgText   = 'The payment bonus {0} is bigger than the amount {1} that the customer pays for invoice {2} after discount and fees.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545901 AND AD_Language = 'en_US'
;

UPDATE AD_Message
SET MsgText   = 'Zahlungsbonus {0} ist größer als der Betrag {1}, den der Kunde nach Skonto und Gebühren zahlt, und wurde nicht vorbelegt.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545902
;
UPDATE AD_Message_Trl
SET MsgText   = 'Zahlungsbonus {0} ist größer als der Betrag {1}, den der Kunde nach Skonto und Gebühren zahlt, und wurde nicht vorbelegt.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545902 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Message_Trl
SET MsgText   = 'Payment bonus {0} is bigger than the amount {1} that the customer pays after discount and fees, and was not pre-filled.',
    Updated   = TO_TIMESTAMP('2026-10-06 14:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545902 AND AD_Language = 'en_US'
;
