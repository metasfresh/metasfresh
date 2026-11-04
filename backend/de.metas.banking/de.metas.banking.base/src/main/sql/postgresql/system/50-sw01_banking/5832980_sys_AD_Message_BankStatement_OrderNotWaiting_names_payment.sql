-- The one refusal message for completing a bank statement whose line carries an order that cannot be paid (ERR_BANKSTATEMENT_ORDER_NOT_WAITING, AD_Message 545988)
-- now also names the payment that carries the order (REQUIREMENTS AC-OM3: "order already paid / carried by payment <C_Payment.DocumentNo>").
-- {0} = C_Order.DocumentNo, {1} = C_Payment.DocumentNo of the carrying payment, "-" if no payment carries the order (e.g. an order closed by hand).
--
-- Impact: AD_Message 545988 MsgText + its AD_Message_Trl rows (de_DE, de_CH, en_US). NOT affected: all other AD_Message rows.

UPDATE AD_Message
SET MsgText = 'Auftrag {0} ist bereits bezahlt oder wartet nicht mehr auf Zahlung (Zahlung: {1}). Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
    Updated = TO_TIMESTAMP('2026-11-04 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988
;

UPDATE AD_Message_Trl
SET MsgText = 'Auftrag {0} ist bereits bezahlt oder wartet nicht mehr auf Zahlung (Zahlung: {1}). Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
    Updated = TO_TIMESTAMP('2026-11-04 10:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0} is already paid or no longer waiting for payment (payment: {1}). No payment is created. Please clear the order on the bank statement line.',
    Updated = TO_TIMESTAMP('2026-11-04 10:30:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988 AND AD_Language = 'en_US'
;
