-- 2026-03-09
-- me03#26915: Fix IsInheritPackingInstruction translations
-- Base language is de_DE, so AD_Element must have German text.
-- The original script (5792610) incorrectly set English text on the base AD_Element record.

-- Fix base AD_Element: set German text (de_DE is the base language)
UPDATE AD_Element
SET Name        = 'Packvorschrift vererben',
    PrintName   = 'Packvorschrift vererben',
    Description = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle aus dem Kompensationsgruppen-Schema erstellten Unterartikel uebertragen.',
    Updated     = TO_TIMESTAMP('2026-03-09 17:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584629;

-- Fix de_DE translation row (base language row mirrors the element)
UPDATE AD_Element_Trl
SET Name         = 'Packvorschrift vererben',
    PrintName    = 'Packvorschrift vererben',
    Description  = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle aus dem Kompensationsgruppen-Schema erstellten Unterartikel uebertragen.',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-03-09 17:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584629
  AND AD_Language = 'de_DE';

-- Ensure en_US translation is correct
UPDATE AD_Element_Trl
SET Name         = 'Inherit Packing Instruction',
    PrintName    = 'Inherit Packing Instruction',
    Description  = 'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-03-09 17:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584629
  AND AD_Language = 'en_US';

-- Propagate: de_DE element text → AD_Column, AD_Field, AD_Process_Para, AD_UI_Element names
SELECT update_ad_element_on_ad_element_trl_update(584629, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'de_DE');

-- Propagate: en_US
SELECT update_ad_element_on_ad_element_trl_update(584629, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'en_US');
