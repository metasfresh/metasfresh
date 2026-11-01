-- SEPA_Protocol (AD_Element 577573) is shared by the SEPA Export process parameter and the SEPA_Export.SEPA_Protocol column (which also holds direct-debit exports),
-- so its Description must not be credit-transfer-specific. Help is dropped because it only repeated the Description.

UPDATE AD_Element_Trl SET Description = 'Version of the pain.001 credit-transfer file. Direct debit always uses pain.008.003.02.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'en_US')
;

UPDATE AD_Element_Trl SET Description = 'Version der pain.001-Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 09:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_CH'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_CH')
;

UPDATE AD_Element_Trl SET Description = 'Version der pain.001-Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = NULL, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 09:01:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 577573 AND AD_Language = 'de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(577573, 'de_DE')
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(577573, 'de_DE')
;

-- Propagate to the process parameter and the column (same statements the element propagation issues)
UPDATE AD_Column SET Description = 'Version der pain.001-Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = NULL WHERE AD_Element_ID = 577573
;
UPDATE AD_Process_Para SET Description = 'Version der pain.001-Überweisungsdatei. Lastschriften verwenden immer pain.008.003.02.', Help = NULL WHERE AD_Element_ID = 577573 AND IsCentrallyMaintained = 'Y'
;
/* DDL */ select update_Process_Para_Translation_From_AD_Element(577573)
;
/* DDL */ select update_Column_Translation_From_AD_Element(577573)
;
