-- One refusal message for completing a bank statement whose line carries an order that cannot be paid because it is already paid or does not wait for payment any more
-- (human decision 2026-11-04: "unify"). Until now there were two messages:
--   ERR_BANKSTATEMENT_ORDER_ALREADY_PAID (AD_Message 545983): "order {0} is already assigned to payment {1}"
--   ERR_BANKSTATEMENT_ORDER_NOT_WAITING  (AD_Message 545988): "order {0} is no longer waiting for payment"
-- Now ERR_BANKSTATEMENT_ORDER_NOT_WAITING (AD_Message 545988, error code BANKSTATEMENT_ORDER_NOT_WAITING) covers both; {0} = C_Order.DocumentNo.
-- ERR_BANKSTATEMENT_ORDER_ALREADY_PAID is removed (it was created by 5832830 of the same unreleased change and is not used any more).
--
-- Impact: AD_Message 545988 MsgText + AD_Message_Trl (de_DE, de_CH, en_US) of 545988 get the unified text;
--   AD_Message 545983 (ERR_BANKSTATEMENT_ORDER_ALREADY_PAID) and its AD_Message_Trl rows are deleted.
-- NOT affected: ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH (545982), ERR_BANKSTATEMENT_ORDER_NOT_A_PREPAYMENT, all other AD_Message rows.

UPDATE AD_Message
SET MsgText = 'Auftrag {0} wartet nicht auf Zahlung: er ist bereits bezahlt oder abgeschlossen. Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
    Updated = TO_TIMESTAMP('2026-11-04 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988
;

UPDATE AD_Message_Trl
SET MsgText = 'Auftrag {0} wartet nicht auf Zahlung: er ist bereits bezahlt oder abgeschlossen. Es wird keine Zahlung erstellt. Bitte den Auftrag in der Auszugsposition entfernen.',
    Updated = TO_TIMESTAMP('2026-11-04 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988 AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0} is not waiting for payment: it is already paid or closed. No payment is created. Please clear the order on the bank statement line.',
    Updated = TO_TIMESTAMP('2026-11-04 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545988 AND AD_Language = 'en_US'
;

DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545983
;

DELETE FROM AD_Message WHERE AD_Message_ID = 545983 AND Value = 'ERR_BANKSTATEMENT_ORDER_ALREADY_PAID'
;
