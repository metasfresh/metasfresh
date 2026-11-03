-- 2026-07-06
-- AD_Message 545676 — Order_Reactivate_Blocked_By_PaySchedule_Activity
-- Add proforma de-allocation as an alternative remediation step in the block message.

-- Base row (German base language)
UPDATE ad_message
SET MsgText = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren oder die Proformarechnung-Zuordnung aufheben.',
    Updated  = TO_TIMESTAMP('2026-07-06 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545676;

-- de_DE translation
UPDATE ad_message_trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren oder die Proformarechnung-Zuordnung aufheben.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-06 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_DE';

-- de_CH translation
UPDATE ad_message_trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan nicht mehr Pending ist. Bitte zuerst die Wareneingänge / Rechnungen / Zahlungen stornieren oder die Proformarechnung-Zuordnung aufheben.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-06 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_CH';

-- en_US translation
UPDATE ad_message_trl
SET MsgText      = 'Cannot reactivate the order because at least one payment-schedule row is no longer Pending. Please first reverse the goods receipts / invoices / payments, or de-allocate the proforma invoice.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-06 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'en_US';
