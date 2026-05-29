-- https://github.com/metasfresh/me03/issues/29625
-- Fix: AD_Field 778076 (AmountType in Mehrwertsteuercodes tab) showed "Amount Type"
-- in the German UI instead of "Betragsart".
-- Root cause: AD_Element 1602 was originally imported from ADempiere with an explicit
-- de_DE row in AD_Element_Trl containing Name='Amount Type'. The sync functions read
-- from AD_Element_Trl (not AD_Element) and kept overwriting AD_Field.Name to the
-- English name after each migration run.
-- Fix: correct the de_DE and de_CH Trl rows, then call the canonical orchestrator
-- update_trl_tables_on_ad_element_trl_update() which propagates the change to
-- AD_Element, AD_Column_Trl, AD_Field, AD_Field_Trl, AD_Process_Para_Trl,
-- AD_PrintFormatItem_Trl, AD_Tab_Trl, AD_Window_Trl, and AD_Menu_Trl.
-- Each sub-function sets target.Updated = e_trl.updated, so the after_migration
-- sync condition (target.updated <> e_trl.updated) evaluates to equal and leaves
-- the corrected values in place.

UPDATE AD_Element_Trl
SET    Name      = 'Betragsart',
       Updated   = TO_TIMESTAMP('2026-05-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 1602
  AND  AD_Language IN ('de_DE', 'de_CH');

SELECT update_trl_tables_on_ad_element_trl_update(1602);
