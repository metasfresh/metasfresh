-- https://github.com/metasfresh/me03/issues/29369 — iter-3 review fix for Step 4 migration
-- Addresses two review findings on 5801560_sys_me03_29369_AD_Process_Para_IsPartialInvoice.sql:
--
-- 1. AD_Element.Help must be the canonical source of the tooltip text, not the literal Help on the
--    AD_Process_Para row. With IsCentrallyMaintained='Y' set on the para row, any future propagation
--    triggered by another migration touching AD_Element 584794 would silently wipe the literal Help.
--    Guard "AND Help IS NULL" makes this idempotent (5800220 already set it; this is a safety net).
--
-- 2. en_US Help on AD_Element_Trl must be explicitly set (already done by 5800220; guard here
--    ensures a fresh-rollout DB also gets it before any element-triggered propagation sweep).
--
-- 3. NULL out the literal Help on AD_Process_Para_ID=543194 so it derives its Help exclusively
--    from AD_Element via propagation (no stale literal can shadow the element value).
--
-- 4. Call update_TRL_Tables_On_AD_Element_TRL_Update to push Help from AD_Element_Trl to every
--    linked AD_Process_Para_Trl / AD_Field_Trl / AD_Tab_Trl / AD_Column_Trl row.

-- =====================================================================
-- A. Ensure AD_Element.Help is set (idempotent — already populated by
--    5800220; guard fires only on a fresh DB that somehow skipped it)
-- =====================================================================
UPDATE AD_Element
SET Help      = 'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
                || 'wie der Anzahlungsbetrag zugeordnet wird: '
                || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
                || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. '
                || 'Standard Y (Teilrechnung) auf Belegart-Ebene; pro Rechnung überschreibbar, '
                || 'solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
    Updated   = TO_TIMESTAMP('2026-05-08 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584794
  AND Help IS NULL;

-- =====================================================================
-- B. Ensure en_US translation on AD_Element_Trl.Help is set
--    (idempotent — already populated by 5800220 with IsTranslated='Y')
-- =====================================================================
UPDATE AD_Element_Trl
SET Help         = 'For a purchase invoice on a prepaid order, this flag determines how the prepayment '
                   || 'amount is allocated: Partial invoice (checked) → MIN(goods receipt × LC%, '
                   || 'remaining prepayment); Final invoice (unchecked) → consume all remaining '
                   || 'prepayment. Default Y (Partial) is set at the document-type level and can be '
                   || 'overridden per invoice while the document status is Draft (DR) or In-Progress (IP).',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-08 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584794
  AND AD_Language   = 'en_US'
  AND (Help IS NULL OR Help = '');

-- =====================================================================
-- C. Propagate AD_Element Help to every linked AD_Process_Para_Trl /
--    AD_Field_Trl / AD_Tab_Trl / AD_Column_Trl row.
-- =====================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794);

-- =====================================================================
-- D. NULL out the literal Help on AD_Process_Para_ID=543194 so that
--    the row derives Help exclusively from AD_Element via propagation.
--    The update_TRL_Tables_On_AD_Element_TRL_Update call above will
--    immediately repopulate it from AD_Element_Trl, so the net result
--    is identical text — but now coming from the canonical source.
-- =====================================================================
UPDATE AD_Process_Para
SET Help      = NULL,
    Updated   = TO_TIMESTAMP('2026-05-08 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_Para_ID    = 543194
  AND IsCentrallyMaintained = 'Y';

-- Re-propagate so the NULL is immediately overwritten from AD_Element_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794);
