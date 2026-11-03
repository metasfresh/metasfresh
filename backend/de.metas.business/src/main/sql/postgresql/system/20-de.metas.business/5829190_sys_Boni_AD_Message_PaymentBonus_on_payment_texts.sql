-- Boni: the texts of the two messages for a payment bonus that a payment cannot book now match their parameters
-- PaymentBonusAboveOpenAmt {1} is what the payment leaves open (open amount minus Skonto and payment amount); PaymentBonusNotDeductible {1} is the payment bonus
-- AD_MigrationScript ID allocated from the central ID server on 2026-10-12

-- de.metas.invoice.paymentbonus.PaymentBonusNotDeductible
UPDATE AD_Message
SET MsgText='Für Rechnung {0} kann der Zahlungsbonus {1} nicht mehr abgezogen werden, z. B. weil er bereits gebucht wurde oder der Vertrag beendet ist.',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545927
;

UPDATE AD_Message_Trl
SET MsgText='Für Rechnung {0} kann der Zahlungsbonus {1} nicht mehr abgezogen werden, z. B. weil er bereits gebucht wurde oder der Vertrag beendet ist.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545927
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus {1} can no longer be deducted for invoice {0}, e.g. because it was already booked or the contract has ended.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545927
;

-- de.metas.invoice.paymentbonus.PaymentBonusAboveOpenAmt
UPDATE AD_Message
SET MsgText='Der Zahlungsbonus {0} ist höher als der Betrag {1}, den die Zahlung von Rechnung {2} offen lässt (offener Betrag abzüglich Skonto und Zahlbetrag).',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545928
;

UPDATE AD_Message_Trl
SET MsgText='Der Zahlungsbonus {0} ist höher als der Betrag {1}, den die Zahlung von Rechnung {2} offen lässt (offener Betrag abzüglich Skonto und Zahlbetrag).', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545928
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus {0} is higher than the amount {1} that the payment leaves open of invoice {2} (open amount minus Skonto and payment amount).', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-12 16:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545928
;
