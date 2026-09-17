-- 2026-05-19
-- https://github.com/metasfresh/me03/issues/29369
-- PR review follow-up (PR https://github.com/metasfresh/metasfresh/pull/23780) — AD-metadata corrections
-- on iter-3 records inserted by 5800170/5800180/5800230/5800260/5800400. Those scripts are already on
-- the CI seed (cf. 5802080's preamble for the same precedent) and therefore immutable. This corrective
-- script applies the three findings raised in the latest reviewer pass:
--
--   C1  AD_Element + AD_Column INSERTs used CreatedBy=0 / UpdatedBy=0 (System) instead of 100
--       (metasfresh) — convention for dictionary changes. Affected IDs:
--           AD_Element  584792 (BaseAmt), 584793 (Wareneingang)
--           AD_Column   592430 (BaseAmt), 592431 (M_InOut_ID), 592432 (C_Invoice_ID),
--                       592433 (IsPartialInvoice on C_DocType), 592434 (IsPartialInvoice on C_Invoice)
--           AD_Message  545674 (IsPartialInvoiceReadOnlyAfterComplete), 545675 (proforma-prepay guard)
--
--   C2  AD_Message.MsgText (base column) was written in English on 545674 + 545675; per the
--       base-language-is-German rule, swap base text to German and leave English on en_US Trl.
--       AD_Message 545675 was already partly corrected by 5802080 (which left English on the base
--       column); we now fix it for real.
--
--   C3  AD_Element_Trl rows for de_DE / de_CH on elements 584792 (BaseAmt) and 584793 (Wareneingang)
--       were marked IsTranslated='N' even though the text IS the canonical German. Flip to 'Y' so
--       the WebUI propagation treats them as actively translated. Same pattern as 5800220 / 5803280
--       already use.
--
-- Same script applies one fix per CreatedBy/UpdatedBy reset for each affected row; one fix per
-- AD_Message base-column German swap; one fix per IsTranslated flip. Timestamps are monotonically
-- incrementing per statement so propagation guards don't mis-fire.

-- ============================================================================
-- C1 — reset CreatedBy / UpdatedBy from 0 (System) to 100 (metasfresh) on the iter-3 AD dictionary rows
-- ============================================================================

UPDATE AD_Element
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:01', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Element_ID IN (584792, 584793);

UPDATE AD_Element_Trl
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:02', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Element_ID IN (584792, 584793);

UPDATE AD_Column
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:03', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Column_ID IN (592430, 592431, 592432, 592433, 592434);

UPDATE AD_Column_Trl
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:04', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Column_ID IN (592430, 592431, 592432, 592433, 592434);

UPDATE AD_Message
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:05', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Message_ID IN (545674, 545675);

UPDATE AD_Message_Trl
SET    CreatedBy = 100, UpdatedBy = 100,
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:06', 'YYYY-MM-DD HH24:MI:SS')
WHERE  AD_Message_ID IN (545674, 545675);

-- ============================================================================
-- C2 — AD_Message.MsgText base column: English → German
-- ============================================================================

-- AD_Message 545674 (IsPartialInvoiceReadOnlyAfterComplete)
UPDATE AD_Message
SET    MsgText   = 'Teilrechnung-Kennzeichen kann nach Abschluss der Rechnung nicht mehr geändert werden.',
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:07', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Message_ID = 545674;

-- The en_US Trl row was already in English. Re-affirm IsTranslated='Y' to be safe.
UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-19 22:00:08', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Message_ID = 545674 AND AD_Language = 'en_US';

-- de_DE / de_CH already carry the German text; flip IsTranslated='Y'.
UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-19 22:00:09', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Message_ID = 545674 AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Message 545675 (IsAutoAllocateAvailableAmt_ProformaPrepayment_Forbidden)
-- 5802080 had already updated the en_US Trl + the base to the user-friendly English wording.
-- Now we move the German user-friendly wording into the base column (per base-is-German rule).
UPDATE AD_Message
SET    MsgText   = 'Die Option ''Verfügbaren Betrag automatisch zuordnen'' kann bei einer Anzahlung zu einer Proforma-Rechnung nicht aktiviert werden.',
       Updated   = TO_TIMESTAMP('2026-05-19 22:00:10', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Message_ID = 545675;

-- en_US Trl already carries the English; re-affirm IsTranslated='Y'.
UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-19 22:00:11', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Message_ID = 545675 AND AD_Language = 'en_US';

UPDATE AD_Message_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-19 22:00:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Message_ID = 545675 AND AD_Language IN ('de_DE', 'de_CH');

-- ============================================================================
-- C3 — AD_Element_Trl de_DE / de_CH: IsTranslated 'N' → 'Y' for German rows that carry German text
-- ============================================================================

UPDATE AD_Element_Trl
SET    IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-19 22:00:13', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID IN (584792, 584793)
  AND  AD_Language IN ('de_DE', 'de_CH');

-- Re-propagate translations from the corrected AD_Element_Trl to dependent AD tables
-- so AD_Column_Trl / AD_Field_Trl pick up the IsTranslated flips.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584792);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584793);
