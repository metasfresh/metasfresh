-- MobileUI Manufacturing - the finished-goods label-scan setting (column IsFinishedGoodsCatchWeightScanRequired) no longer
-- depends on "Capture catch weight" (IsCaptureCatchWeightAtReceipt): without weight capture at receipt the scan pre-fills
-- lot and best-before date only, and the weight is captured later at picking.
-- So the field is shown regardless of IsCaptureCatchWeightAtReceipt, and the texts no longer say "only with Capture catch weight".
--
-- AFFECTED RECORDS
-- =====================================================================
--
-- 1) AD_Field 785692 (window MobileUI Manufacturing Configuration 541788, tab 547483):
--    DisplayLogic @IsCaptureCatchWeightAtReceipt/N@=Y (set by 5830500) -> none
--
-- 2) AD_Element 585547 (IsFinishedGoodsCatchWeightScanRequired) - only used by the two fields below (consistent meaning)
-- -----------------------------------------------------------------
--   Lang          | Name / PrintName (unchanged)
--   --------------+--------------------------------------------------------------------------------------------
--   de_DE, de_CH  | Fertigprodukt: Entnahme nur per Etikett-Scan
--   en_US         | Finished goods: receive by label scan only
--   other (N)     | German base text, IsTranslated stays 'N'
--   Description = Help, old -> new:
--   de_DE, de_CH  | Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge, MHD und Gewicht zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe. Nur mit „Catch Weight erfassen“.
--                 | -> Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.
--   en_US         | Catch-weight main product: label scan pre-fills lot, best-before date and weight for review; 1 piece per scan, no manual input. Only with "Capture catch weight".
--                 | -> Catch-weight main product: label scan pre-fills lot and best-before date (and the weight, with "Capture catch weight") for review; 1 piece per scan, no manual input.
--
-- 3) AD_Column 593753 (MobileUI_MFG_Config), 593754 (MobileUI_UserProfile_MFG) + _Trl: via propagation
-- 4) AD_Field 785692, 785693 (window Nutzer 108, tab 546679 mobile UI user profile - manufacturing) + _Trl: via propagation;
--    base Help set directly (propagation does not sync base AD_Field.Help)
-- 5) AD_UI_Element 655049 / 655050: Description/Help set directly (no _Trl table, not covered by propagation).
-- 6) NOT AFFECTED: nothing else - the element has no AD_Menu, AD_Tab, AD_Window, AD_Process_Para or AD_PrintFormatItem usage.

-- ============================================================================
-- DisplayLogic of the global configuration field: always shown
-- ============================================================================

-- 2026-10-22 15:00:00
UPDATE AD_Field SET DisplayLogic=NULL, Updated=TO_TIMESTAMP('2026-10-22 15:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID=785692
;

-- ============================================================================
-- AD_Element_Trl 585547
-- ============================================================================

-- 2026-10-22 15:00:10
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 15:00:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_DE'
;

-- 2026-10-22 15:00:11
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 15:00:11.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='de_CH'
;

-- 2026-10-22 15:00:12
UPDATE AD_Element_Trl SET Name='Finished goods: receive by label scan only', PrintName='Finished goods: receive by label scan only', Description='Catch-weight main product: label scan pre-fills lot and best-before date (and the weight, with "Capture catch weight") for review; 1 piece per scan, no manual input.', Help='Catch-weight main product: label scan pre-fills lot and best-before date (and the weight, with "Capture catch weight") for review; 1 piece per scan, no manual input.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-22 15:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language='en_US'
;

-- 2026-10-22 15:00:13 (all other languages that only hold an untranslated copy: German base text, stays IsTranslated='N')
UPDATE AD_Element_Trl SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', PrintName='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Updated=TO_TIMESTAMP('2026-10-22 15:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585547 AND AD_Language NOT IN ('de_DE','de_CH','en_US') AND IsTranslated='N'
;

-- 2026-10-22 15:00:20 (propagate to AD_Element, AD_Column(_Trl), AD_Field(_Trl))
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585547)
;

-- ============================================================================
-- Base AD_Field.Help (not synced by the propagation) and AD_UI_Element texts
-- ============================================================================

-- 2026-10-22 15:01:00
UPDATE AD_Field SET Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Updated=TO_TIMESTAMP('2026-10-22 15:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Field_ID IN (785692,785693)
;

-- 2026-10-22 15:01:10
UPDATE AD_UI_Element SET Name='Fertigprodukt: Entnahme nur per Etikett-Scan', Description='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Help='Catch-Weight-Hauptprodukt: Etikett-Scan füllt Charge und MHD (mit „Catch Weight erfassen“ auch das Gewicht) zur Prüfung vor; 1 Stück je Scan, keine manuelle Eingabe.', Updated=TO_TIMESTAMP('2026-10-22 15:01:10.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_UI_Element_ID IN (655049,655050)
;
