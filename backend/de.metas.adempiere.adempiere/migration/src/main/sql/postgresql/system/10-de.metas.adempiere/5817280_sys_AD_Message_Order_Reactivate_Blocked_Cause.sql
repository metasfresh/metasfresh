-- AD_Message 545676 — Order_Reactivate_Blocked_By_PaySchedule_Activity
-- The text still described the block as "the payment-schedule row is no longer Pending" and told the
-- buyer to reverse the payments. Both are wrong: the block never looks at Status (an Awaiting_Pay row
-- with no link does not block, a Pending row carrying M_InOut_ID does), and a payment on its own never
-- sets M_InOut_ID / C_Invoice_ID, so reversing payments can never lift the block. Name the real cause
-- instead — a payment-schedule row linked to a goods receipt or a matched vendor invoice — and ask for
-- those two documents to be reversed.

-- Base row (German base language)
UPDATE AD_Message
SET MsgText   = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan mit einem Wareneingang oder einer abgeglichenen Eingangsrechnung verknüpft ist. Bitte zuerst die betroffenen Wareneingänge / Rechnungen stornieren.',
    Updated   = TO_TIMESTAMP('2026-07-31 16:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545676;

-- de_DE translation
UPDATE AD_Message_Trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan mit einem Wareneingang oder einer abgeglichenen Eingangsrechnung verknüpft ist. Bitte zuerst die betroffenen Wareneingänge / Rechnungen stornieren.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 16:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_DE';

-- de_CH translation (kept in sync with the German base text, per this message's existing convention)
UPDATE AD_Message_Trl
SET MsgText      = 'Beleg kann nicht reaktiviert werden, da mindestens eine Zeile im Zahlungsplan mit einem Wareneingang oder einer abgeglichenen Eingangsrechnung verknüpft ist. Bitte zuerst die betroffenen Wareneingänge / Rechnungen stornieren.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 16:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'de_CH';

-- en_US translation
UPDATE AD_Message_Trl
SET MsgText      = 'Cannot reactivate the order because at least one payment-schedule row is linked to a goods receipt or a matched vendor invoice. Please first reverse the goods receipts / invoices concerned.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-31 16:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Message_ID = 545676
  AND AD_Language   = 'en_US';

-- Re-sync the still-untranslated seed rows (IsTranslated='N', e.g. fr_CH) with the new German base
-- text. The message loader joins AD_Message_Trl without filtering on IsTranslated, so such a row is
-- served verbatim to its language and would otherwise keep showing the obsolete wording.
UPDATE AD_Message_Trl
SET MsgText   = (SELECT m.MsgText FROM AD_Message m WHERE m.AD_Message_ID = 545676),
    Updated   = TO_TIMESTAMP('2026-07-31 16:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545676
  AND IsTranslated  = 'N';
