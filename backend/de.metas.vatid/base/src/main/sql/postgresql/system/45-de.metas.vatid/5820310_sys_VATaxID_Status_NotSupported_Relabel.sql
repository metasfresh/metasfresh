-- Relabel the VATaxIDStatus 'NotSupported' entry (AD_Ref_List 544333, AD_Reference 542125).
-- The internal Value / ValueName stays 'NotSupported'; only the displayed label changes. "Nicht
-- unterstützt" read as a feature limitation or an error, when the status actually means the VAT-ID's
-- country prefix (CH / GB / NO / non-EU) is outside VIES coverage: the offline format check is its only
-- validation and it still counts as holding a tax certificate.
--   de_DE: Außerhalb VIES-Abdeckung
--   de_CH: Ausserhalb VIES-Abdeckung   (Swiss spelling, ß -> ss)
--   en_US: Outside VIES coverage

UPDATE AD_Ref_List
SET Name = 'Außerhalb VIES-Abdeckung', Updated = now(), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544333;

UPDATE AD_Ref_List_Trl
SET Name = 'Außerhalb VIES-Abdeckung', IsTranslated = 'Y', Updated = now(), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544333 AND AD_Language = 'de_DE';

UPDATE AD_Ref_List_Trl
SET Name = 'Ausserhalb VIES-Abdeckung', IsTranslated = 'Y', Updated = now(), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544333 AND AD_Language = 'de_CH';

UPDATE AD_Ref_List_Trl
SET Name = 'Outside VIES coverage', IsTranslated = 'Y', Updated = now(), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544333 AND AD_Language = 'en_US';
