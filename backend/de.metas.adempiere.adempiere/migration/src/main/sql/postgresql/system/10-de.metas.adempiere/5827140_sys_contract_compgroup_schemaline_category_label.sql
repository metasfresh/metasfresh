-- Compensation group schema line (window "Kompensationsgruppe Schema", tab "Kompensationszeilen"):
-- the "applies to" product category label is written as one word: "Gilt für Produktkategorie".
-- The label comes from the field-specific element 585496 (AD_Field 785582 via AD_Name_ID); en_US "Applies to product category" stays.

UPDATE AD_Element
SET Name      = 'Gilt für Produktkategorie',
    PrintName = 'Gilt für Produktkategorie',
    Updated   = TO_TIMESTAMP('2026-09-30 09:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 585496
;

UPDATE AD_Element_Trl
SET Name         = 'Gilt für Produktkategorie',
    PrintName    = 'Gilt für Produktkategorie',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-09-30 09:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 585496
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- propagate the element to AD_Field / AD_Field_Trl (and every other element-driven table), all languages
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585496, NULL)
;

UPDATE AD_UI_Element
SET Name      = 'Gilt für Produktkategorie',
    Updated   = TO_TIMESTAMP('2026-09-30 09:10:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 654910
;
