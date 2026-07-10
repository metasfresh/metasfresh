-- F01010.4 Invoice Accounting Overrides — AC2: fix Help on field 710156
--
-- AD_Field 710156 (C_ElementValue_ID in window 541659) had a stale Help value carried over
-- from element 198 ("Account Elements can be natural accounts or user defined values.").
-- The AD_Field.AD_Name_ID now points at element 585015 whose Help is correctly set in
-- AD_Field_Trl (de_DE/de_CH/en_US), but AD_Field.Help (base column) must be cleared so the
-- WebUI falls through to the element-propagated value rather than the stale override.

UPDATE AD_Field
SET    Help      = NULL,
       Updated   = TO_TIMESTAMP('2026-06-18 09:04:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 710156;

-- Propagate element 585015 translations again now that the field Help is clear
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585015 /*From ID Server*/);
