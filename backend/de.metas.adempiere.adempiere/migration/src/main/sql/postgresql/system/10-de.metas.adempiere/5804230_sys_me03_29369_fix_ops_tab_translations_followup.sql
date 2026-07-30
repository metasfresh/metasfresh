-- me03 #29369 — corrective follow-up to 5804210
--
-- After 5804210 applied, two issues remained on the live AD trl state:
--
-- (a) AD_Field_Trl(777250 / DueAmt_Actual, en_US) still showed the German fallback
--     "Tatsächlich fälliger Betrag" with IsTranslated='N'. Root cause: the cascade
--     (update_trl_tables_on_ad_element_trl_update) skips rows where
--     AD_Field_Trl.Updated = AD_Element_Trl.Updated — and en_US's element trl row
--     was untouched by 5804210, so its timestamp matched the field trl's. The
--     en_US AD_Element_Trl row had carried the correct "Actual due amount" value
--     since 2026-04-24 but the propagation to AD_Field_Trl never ran (timestamps
--     identical at that moment). Fix: bump AD_Element_Trl(en_US).Updated so the
--     cascade's `f.updated < e_trl.updated` check passes, then re-run the cascade.
--
-- (b) AD_Element 577683 / OffsetDays — 5804210 directly UPDATEd AD_Element.Name
--     to 'Versatztage', which was promptly overwritten back to 'Offset days' by
--     the base-language back-sync inside update_trl_tables_on_ad_element_trl_update
--     (it reads AD_Element_Trl(de_DE).Name — which 5804210 did NOT touch — and
--     writes it back into AD_Element.Name). Fix: update AD_Element_Trl(de_DE) +
--     (de_CH for consistency) to 'Versatztage' and let the cascade write
--     AD_Element.Name + AD_Field_Trl(de_DE) for us.

-- ============================================================================
-- (a) DueAmt_Actual — bump AD_Element_Trl(en_US).Updated to retrigger cascade
-- ============================================================================
-- Name already correct ("Actual due amount" since 2026-04-24); only the
-- timestamp needs to advance so the cascade no longer no-ops the en_US row.
UPDATE AD_Element_Trl
   SET Updated   = TO_TIMESTAMP('2026-05-22 16:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID = 584784
   AND AD_Language   = 'en_US'
;

-- ============================================================================
-- (b) OffsetDays — fix the German trl rows; let the cascade sync everything
-- ============================================================================
UPDATE AD_Element_Trl
   SET Name         = 'Versatztage',
       PrintName    = 'Versatztage',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-05-22 16:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 577683
   AND AD_Language   IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- (c) Cascade — propagates to AD_Field_Trl, AD_Column_Trl, AD_Tab_Trl,
--     AD_Menu_Trl AND writes back to AD_Element.Name from the de_DE base trl row.
-- ============================================================================
SELECT update_trl_tables_on_ad_element_trl_update(584784, NULL);
SELECT update_trl_tables_on_ad_element_trl_update(577683, NULL);
