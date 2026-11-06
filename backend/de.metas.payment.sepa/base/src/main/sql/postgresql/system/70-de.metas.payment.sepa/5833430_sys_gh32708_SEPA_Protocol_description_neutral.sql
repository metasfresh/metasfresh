-- SEPA_Protocol (AD_Element 577573) is shared by the SEPA Export process parameter and the SEPA_Export.SEPA_Protocol column.
-- The description listed format versions and stated that direct debit always uses pain.008.001.08; the format is now chosen
-- per transaction type from the field's own list, so the description no longer names versions.

UPDATE AD_Element_Trl SET Description = 'Format (version) of the SEPA payment file.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-06 17:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'en_US')
;

UPDATE AD_Element_Trl SET Description = 'Format (Version) der SEPA-Zahlungsdatei.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-06 17:10:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_CH')
;

UPDATE AD_Element_Trl SET Description = 'Format (Version) der SEPA-Zahlungsdatei.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-06 17:10:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_DE')
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(577573, 'de_DE')
;

-- Propagate to the process parameter and the column (same statements the element propagation issues)
UPDATE AD_Column SET Description = 'Format (Version) der SEPA-Zahlungsdatei.', Help = NULL WHERE AD_Element_ID = 577573
;
UPDATE AD_Process_Para SET Description = 'Format (Version) der SEPA-Zahlungsdatei.', Help = NULL WHERE AD_Element_ID = 577573 AND IsCentrallyMaintained = 'Y'
;
/* DDL */ select update_Process_Para_Translation_From_AD_Element(577573)
;
/* DDL */ select update_Column_Translation_From_AD_Element(577573)
;
