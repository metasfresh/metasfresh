-- 2026-03-09
-- Update IsInheritPackingInstruction description to document that a compatible
-- M_HU_PI_Item_Product must exist for each sub-article, otherwise inheritance is skipped.

-- Fix base element: must be German (de_DE is base language)
UPDATE AD_Element
SET Name = 'Packvorschrift vererben',
    PrintName = 'Packvorschrift vererben',
    Description = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle Unterartikel übertragen, die aus dem Kompensationsgruppen-Schema erstellt werden. Voraussetzung: Für jeden Unterartikel muss eine kompatible Packvorschrift-Zuordnung (M_HU_PI_Item_Product) für denselben TU-Typ existieren, sonst wird die Vererbung für diesen Artikel übersprungen.',
    Updated = TO_TIMESTAMP('2026-03-09 19:55', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584629;

-- de_DE: mirror of base (IsTranslated=N)
UPDATE AD_Element_Trl
SET Name = 'Packvorschrift vererben',
    PrintName = 'Packvorschrift vererben',
    Description = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle Unterartikel übertragen, die aus dem Kompensationsgruppen-Schema erstellt werden. Voraussetzung: Für jeden Unterartikel muss eine kompatible Packvorschrift-Zuordnung (M_HU_PI_Item_Product) für denselben TU-Typ existieren, sonst wird die Vererbung für diesen Artikel übersprungen.',
    IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-03-09 19:55', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584629 AND AD_Language = 'de_DE';

-- de_CH: same German text (IsTranslated=N)
UPDATE AD_Element_Trl
SET Name = 'Packvorschrift vererben',
    PrintName = 'Packvorschrift vererben',
    Description = 'Wenn gesetzt, wird die Packvorschrift des Hauptartikels auf alle Unterartikel übertragen, die aus dem Kompensationsgruppen-Schema erstellt werden. Voraussetzung: Für jeden Unterartikel muss eine kompatible Packvorschrift-Zuordnung (M_HU_PI_Item_Product) für denselben TU-Typ existieren, sonst wird die Vererbung für diesen Artikel übersprungen.',
    IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-03-09 19:55', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584629 AND AD_Language = 'de_CH';

-- en_US: English translation
UPDATE AD_Element_Trl
SET Name = 'Inherit Packing Instruction',
    PrintName = 'Inherit Packing Instruction',
    Description = 'If set, the packing instruction from the main article is applied to all sub-articles created from the compensation group schema template. Prerequisite: Each sub-article must have a compatible packing instruction assignment (M_HU_PI_Item_Product) for the same TU type, otherwise inheritance is skipped for that article.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-03-09 19:55', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Element_ID = 584629 AND AD_Language = 'en_US';

-- Propagate element changes to all dependent AD records
SELECT update_ad_element_on_ad_element_trl_update(584629, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'de_DE');

SELECT update_ad_element_on_ad_element_trl_update(584629, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584629, 'en_US');
