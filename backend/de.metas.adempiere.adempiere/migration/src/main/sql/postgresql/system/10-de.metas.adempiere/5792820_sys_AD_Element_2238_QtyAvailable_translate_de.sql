-- Translate AD_Element 2238 (QtyAvailable) to German
UPDATE ad_element_trl
SET name        = 'Verfügbare Menge',
    printname   = 'Verfügbare Menge',
    description = 'Verfügbare Menge (Lagerbestand - Reserviert)',
    help        = 'Verfügbare Menge = Lagerbestand minus reservierte Menge',
    istranslated = 'Y',
    updated     = '2026-03-07 10:00',
    updatedby   = 0
WHERE ad_element_id = 2238
  AND ad_language   = 'de_DE';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2238, 'de_DE');
