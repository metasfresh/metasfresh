-- AD_Message 545676 — Order_Reactivate_Blocked_By_PaySchedule_Activity
-- A proforma allocation no longer blocks order reactivation, so the message must stop
-- instructing the buyer to de-allocate the proforma invoice. Only a goods-receipt or
-- matched-invoice link on a pay-schedule row can still cause the block.

-- Base row (German base language)
UPDATE AD_Message
SET MsgText  = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren.',
    Updated  = TO_TIMESTAMP('2026-07-31 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545676;

-- de_DE translation
UPDATE AD_Message_Trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_DE';

-- de_CH translation (kept in sync with the German base text, per this message's existing convention)
UPDATE AD_Message_Trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_CH';

-- en_US translation
UPDATE AD_Message_Trl
SET MsgText      = 'Cannot reactivate the order because at least one payment-schedule row is no longer Pending. Please first reverse the goods receipts / invoices / payments.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 09:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'en_US';
