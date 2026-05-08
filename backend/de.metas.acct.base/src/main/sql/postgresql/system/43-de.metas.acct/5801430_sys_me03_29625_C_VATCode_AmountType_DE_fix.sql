-- https://github.com/metasfresh/me03/issues/29625
-- Fix: AD_Field 778076 (AmountType in Mehrwertsteuercodes tab) showed "Amount Type"
-- in the German UI instead of "Betragsart".
-- Root cause: AD_Element 1602 was originally imported from ADempiere with an English
-- base-language Name, AND has an explicit de_DE row in AD_Element_Trl also with
-- Name='Amount Type'. The update_fieldtranslation_from_ad_name_element() sync reads
-- from AD_Element_Trl (not AD_Element) and keeps overwriting AD_Field.Name to the
-- English name.
-- Fix: update both AD_Element.Name and the de_DE AD_Element_Trl row to 'Betragsart',
-- then explicitly fix AD_Field.Name to match (all with the same timestamp so the
-- after_migration sync leaves the corrected value in place).

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

-- Fix AD_Field directly with the same timestamp as the Trl row above,
-- so after_migration sync (f.updated == e_trl.updated) leaves it unchanged
UPDATE AD_Field
SET    Name      = 'Betragsart',
       Updated   = TO_TIMESTAMP('2026-05-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 778076;

-- Fix de_CH AD_Field_Trl row (same fix as de_DE base row)
UPDATE AD_Field_Trl
SET    IsTranslated = 'Y',
       Name         = 'Betragsart',
       Updated      = TO_TIMESTAMP('2026-05-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Language = 'de_CH'
  AND  AD_Field_ID = 778076;

-- Ensure en_US translation remains correct
UPDATE AD_Field_Trl
SET    IsTranslated = 'Y',
       Name         = 'Amount Type',
       Updated      = TO_TIMESTAMP('2026-05-08 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Language = 'en_US'
  AND  AD_Field_ID = 778076;
