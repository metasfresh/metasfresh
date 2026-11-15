-- Completing a bank statement whose line carries a hand-picked prepay order no longer requires the line amount to equal the order total
-- (human decision 2026-11-04: "Allow any amount"); only the currency must match. The automatic order match still needs the full amount.
-- So AD_Message 545982 (created by 5832830 as ERR_BANKSTATEMENT_ORDER_AMOUNT_MISMATCH, error code BANKSTATEMENT_ORDER_AMOUNT_MISMATCH) now speaks of the currency only,
-- and its key + error code are renamed to ERR_BANKSTATEMENT_ORDER_CURRENCY_MISMATCH / BANKSTATEMENT_ORDER_CURRENCY_MISMATCH (both new in this change, not yet released).
-- {0} = C_Order.DocumentNo, {1} = currency code of the order (was: order total + currency).
--
-- Impact: AD_Message 545982 MsgText, Value, ErrorCode + all its AD_Message_Trl rows. NOT affected: all other AD_Message rows.

UPDATE AD_Message
SET MsgText = 'Auftrag {0}: Die Währung der Auszugsposition muss der Auftragswährung ({1}) entsprechen. Bitte den Auftrag in der Auszugsposition entfernen oder die Position korrigieren.',
    Value = 'ERR_BANKSTATEMENT_ORDER_CURRENCY_MISMATCH', ErrorCode = 'BANKSTATEMENT_ORDER_CURRENCY_MISMATCH',
    Updated = TO_TIMESTAMP('2026-11-04 10:40:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545982
;

UPDATE AD_Message_Trl
SET MsgText = 'Auftrag {0}: Die Währung der Auszugsposition muss der Auftragswährung ({1}) entsprechen. Bitte den Auftrag in der Auszugsposition entfernen oder die Position korrigieren.',
    Updated = TO_TIMESTAMP('2026-11-04 10:40:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545982 AND AD_Language <> 'en_US'
;

UPDATE AD_Message_Trl
SET MsgText = 'Order {0}: the currency of the bank statement line must be the order currency ({1}). Please clear the order on the bank statement line or correct the line.',
    Updated = TO_TIMESTAMP('2026-11-04 10:40:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Message_ID = 545982 AND AD_Language = 'en_US'
;
