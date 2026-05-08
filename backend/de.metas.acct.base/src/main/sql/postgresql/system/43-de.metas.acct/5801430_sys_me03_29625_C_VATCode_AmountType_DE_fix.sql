-- https://github.com/metasfresh/me03/issues/29625
-- Fix: AD_Field 778076 (AmountType in Mehrwertsteuercodes tab) showed "Amount Type"
-- in the German UI instead of "Betragsart".
-- Root cause: AD_Element 1602 was originally imported from ADempiere with an English
-- base-language Name, AND has an explicit de_DE row in AD_Element_Trl also with
-- Name='Amount Type'. The update_fieldtranslation_from_ad_name_element() sync reads
-- from AD_Element_Trl (not AD_Element) and keeps overwriting AD_Field.Name to the
-- English name.
-- Fix: update AD_Element.Name and the de_DE/de_CH AD_Element_Trl rows to 'Betragsart',
-- then call update_fieldtranslation_from_ad_name_element(1602) to propagate to all
-- AD_Field and AD_Field_Trl rows. The function sets AD_Field.Updated = e_trl.updated,
-- so the after_migration sync condition (f.updated <> e_trl.updated) evaluates to
-- equal and leaves the corrected values in place.

UPDATE AD_Element
SET    Name      = 'Betragsart',
       Updated   = TO_TIMESTAMP('2026-05-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 1602;

-- Fix the de_DE and de_CH Trl rows — this is what the sync function actually reads
UPDATE AD_Element_Trl
SET    Name      = 'Betragsart',
       Updated   = TO_TIMESTAMP('2026-05-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 1602
  AND  AD_Language IN ('de_DE', 'de_CH');

-- Propagate corrected names to all AD_Field and AD_Field_Trl rows for element 1602.
-- The function sets f.updated = e_trl.updated, preventing after_migration from reverting.
SELECT update_fieldtranslation_from_ad_name_element(1602);
