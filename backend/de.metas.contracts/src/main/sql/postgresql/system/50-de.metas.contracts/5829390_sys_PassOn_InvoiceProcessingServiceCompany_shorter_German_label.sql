-- Shorter German label for the pass-on service company field: 'Servicegebühr übernehmen von' -> 'Servicegebühr von',
-- because the long label is cut off in the header of window Kompensationsgruppen-Vertragseinstellungen (542194)
-- and in window Vertragsbedingungen (540113).
--
-- Impact (both elements, all usages):
--   AD_Element 585538 (PassOn_InvoiceProcessingServiceCompany_ID)
--     AD_Column 593735 C_CompensationGroup_ContractSettings.PassOn_InvoiceProcessingServiceCompany_ID -> field 785684 (window 542194)
--     AD_Column 593737 C_Flatrate_Conditions.PassOn_InvoiceProcessingServiceCompany_ID (virtual column)
--   AD_Element 585539 (PassOn_InvoiceProcessingServiceCompany_Conditions)
--     AD_Field.AD_Name_ID of field 785685 (window 540113)
--   No process parameter, menu, tab, window, print format item or KPI field uses either element.
--
-- Description/Help unchanged. en_US label 'Pass on service fee of' unchanged.
-- fr_CH only holds the untranslated German copy; it gets the new German text, still IsTranslated='N'.

-- AD_Element 585538
UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', Updated=TO_TIMESTAMP('2026-10-14 10:00:02.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='fr_CH' AND IsTranslated='N'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'de_CH')
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'fr_CH')
;

-- AD_Element 585539
UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:03.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:04.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Name='Servicegebühr von', PrintName='Servicegebühr von', Updated=TO_TIMESTAMP('2026-10-14 10:00:05.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='fr_CH' AND IsTranslated='N'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'de_CH')
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'fr_CH')
;
