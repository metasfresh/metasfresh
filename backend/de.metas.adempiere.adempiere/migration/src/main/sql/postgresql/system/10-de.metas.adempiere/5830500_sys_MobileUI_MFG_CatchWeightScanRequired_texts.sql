-- MobileUI Manufacturing - clearer UI texts for the finished-goods catch-weight scan setting (column IsFinishedGoodsCatchWeightScanRequired, unchanged)
-- and show it in the global configuration window only when "Capture catch weight" (IsCaptureCatchWeightAtReceipt) is set.
-- Behaviour is unchanged; only Name/PrintName/Description/Help and one field's DisplayLogic change.
--
-- AFFECTED RECORDS
-- =====================================================================
--
-- 1) AD_Element 585547 (IsFinishedGoodsCatchWeightScanRequired) - only used by the two fields below (consistent meaning)
-- -----------------------------------------------------------------
--   Lang          | Name / PrintName (old -> new)
--   --------------+--------------------------------------------------------------------------------------------
--   de_DE, de_CH  | Fertigprodukt: Catch-Weight-Scan erforderlich -> Fertigprodukt: Entnahme nur per Etikett-Scan
--   en_US         | Finished goods: require catch-weight scan     -> Finished goods: receive by label scan only
--   other (N)     | German base text, IsTranslated stays 'N'
--   Description and Help: replaced by one short text (Description = Help) in each language.
--
-- 2) AD_Column 593753 (MobileUI_MFG_Config), 593754 (MobileUI_UserProfile_MFG) + _Trl: via propagation
-- 3) AD_Field 785692 (window MobileUI Manufacturing Configuration 541788, tab 547483) + _Trl: via propagation;
--    base Help set directly (propagation does not sync base AD_Field.Help);
--    DisplayLogic (none) -> @IsCaptureCatchWeightAtReceipt/N@=Y
-- 4) AD_Field 785693 (window Nutzer 108, tab 546679 mobile UI user profile - manufacturing) + _Trl: via propagation;
--    base Help set directly; NO DisplayLogic - in the user profile an empty value means "inherit", so the field stays always visible.
-- 5) AD_UI_Element 655049 / 655050: Name/Description/Help set directly (no _Trl table, not covered by propagation).
-- 6) NOT AFFECTED: nothing else - the element has no AD_Menu, AD_Tab, AD_Window, AD_Process_Para or AD_PrintFormatItem usage.

-- ============================================================================
-- DisplayLogic on the global configuration field only
-- ============================================================================

-- 2026-10-22 10:00:00
UPDATE AD_Field SET DisplayLogic='@IsCaptureCatchWeightAtReceipt/N@=Y', Updated=TO_TIMESTAMP('2026-10-22 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=785692
;

-- ============================================================================
-- AD_Element_Trl 585547
-- ============================================================================

-- 2026-10-22 10:00:10
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 10:00:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_DE'
;

-- 2026-10-22 10:00:11
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 10:00:11.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_CH'
;

-- 2026-10-22 10:00:12
UPDATE AD_Element_Trl SET Name='Finished goods: receive by label scan only', PrintName='Finished goods: receive by label scan only', Description='Catch-weight main product: label scan pre-fills lot, best-before date and weight for review; 1 piece per scan, no manual input. Only with "Capture catch weight".', Help='Catch-weight main product: label scan pre-fills lot, best-before date and weight for review; 1 piece per scan, no manual input. Only with "Capture catch weight".', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='en_US'
;

-- 2026-10-22 10:00:13 (all other languages that only hold an untranslated copy: German base text, stays IsTranslated='N')
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Updated=TO_TIMESTAMP('2026-10-22 10:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language NOT IN ('de_DE','de_CH','en_US') AND IsTranslated='N'
;

-- 2026-10-22 10:00:20 (propagate to AD_Element, AD_Column(_Trl), AD_Field(_Trl))
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585547)
;

-- ============================================================================
-- Base AD_Field.Help (not synced by the propagation) and AD_UI_Element texts
-- ============================================================================

-- 2026-10-22 10:01:00
UPDATE AD_Field SET Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Updated=TO_TIMESTAMP('2026-10-22 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID IN (785692,785693)
;

-- 2026-10-22 10:01:10
UPDATE AD_UI_Element SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.', Updated=TO_TIMESTAMP('2026-10-22 10:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID IN (655049,655050)
;
