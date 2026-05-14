-- 2026-05-14
-- https://github.com/metasfresh/me03/issues/29369
-- PR review fixes (https://github.com/metasfresh/metasfresh/pull/23780 comments #6 + #22):
--   * #6  user-friendly text for AD_Message 545675 (IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden)
--   * #22 refresh TRL tables for AD_Element 584794 (IsPartialInvoice) — the original migration
--        5801560 inserted AD_Process_Para/AD_Process_Para_Trl rows but did not call
--        update_TRL_Tables_On_AD_Element_TRL_Update; that migration is already applied on the
--        CI seed (immutable), hence this follow-up.

-- ============================================================================
-- Fix #6: user-friendly text for AD_Message 545675
-- ============================================================================

UPDATE AD_Message
SET MsgText = 'The ''Auto-allocate available amount'' option cannot be enabled on a prepayment for a proforma invoice.',
    Updated = TO_TIMESTAMP('2026-05-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545675 /*From ID Server*/
;

UPDATE AD_Message_Trl
SET MsgText = 'The ''Auto-allocate available amount'' option cannot be enabled on a prepayment for a proforma invoice.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545675 /*From ID Server*/
  AND AD_Language = 'en_US'
;

UPDATE AD_Message_Trl
SET MsgText = 'Die Option ''Verfügbaren Betrag automatisch zuordnen'' kann bei einer Anzahlung zu einer Proforma-Rechnung nicht aktiviert werden.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545675 /*From ID Server*/
  AND AD_Language = 'de_DE'
;

UPDATE AD_Message_Trl
SET MsgText = 'Die Option ''Verfügbaren Betrag automatisch zuordnen'' kann bei einer Anzahlung zu einer Proforma-Rechnung nicht aktiviert werden.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Message_ID = 545675 /*From ID Server*/
  AND AD_Language = 'de_CH'
;

-- ============================================================================
-- Fix #22: refresh TRL tables for AD_Element 584794 (IsPartialInvoice)
-- ============================================================================
-- Universal coding rule introduced by PR review on
-- https://github.com/metasfresh/metasfresh/pull/23780:
-- after AD_Process_Trl / AD_Process_Para_Trl inserts, always call this function
-- so non-en_US base systems propagate translations correctly.

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794 /*From ID Server*/);
