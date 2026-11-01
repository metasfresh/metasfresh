-- Follow-up to 5829420: two en_US captions on tab Schema-Zeilen / Template Lines (544005) of window
-- Kompensationsgruppe Schema (540415) to Title Case (casing only). The tab has no UI section, its fields are displayed
-- through AD_Field.IsDisplayed / IsDisplayedGrid.
-- Element 579540 IsHideWhenPrinting is also shown in window 181 (Bestellung).

-- 583545 IsAllowSeparateInvoicing: Separate invoicing -> Separate Invoicing
UPDATE AD_Element_Trl SET Name='Separate Invoicing', PrintName='Separate Invoicing', Updated=TO_TIMESTAMP('2026-10-14 16:10:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=583545 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583545, 'en_US')
;

-- 579540 IsHideWhenPrinting: Hide when printing -> Hide When Printing
UPDATE AD_Element_Trl SET Name='Hide When Printing', PrintName='Hide When Printing', Updated=TO_TIMESTAMP('2026-10-14 16:10:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579540, 'en_US')
;
