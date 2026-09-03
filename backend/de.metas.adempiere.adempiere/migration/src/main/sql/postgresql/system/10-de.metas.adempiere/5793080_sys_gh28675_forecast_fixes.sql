-- Migration: Forecast process fixes (me03#28675)
-- 1. Add tooltips (Description) to AD_Ref_List entries for ForecastCalculationMethod and ForecastPrecisionUnit
-- 2. Make M_Forecast.M_Warehouse_ID mandatory
-- 3. Undisplay C_BPartner_ID and M_PriceList_ID from Prognose window (not used by forecast generator)

-- ==========================================================================
-- 1. AD_Ref_List tooltips for ForecastCalculationMethod (AD_Reference 542072)
-- ==========================================================================

-- AVG_52_WEEKS (544159)
UPDATE AD_Ref_List SET Description='Durchschnittliche Verkaufsmenge der letzten 52 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544159;
UPDATE AD_Ref_List_Trl SET Description='Durchschnittliche Verkaufsmenge der letzten 52 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544159 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Average sales quantity over the last 52 weeks, projected onto the forecast horizon', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544159 AND AD_Language='en_US';

-- AVG_26_WEEKS (544160)
UPDATE AD_Ref_List SET Description='Durchschnittliche Verkaufsmenge der letzten 26 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544160;
UPDATE AD_Ref_List_Trl SET Description='Durchschnittliche Verkaufsmenge der letzten 26 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544160 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Average sales quantity over the last 26 weeks, projected onto the forecast horizon', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544160 AND AD_Language='en_US';

-- AVG_12_WEEKS (544161)
UPDATE AD_Ref_List SET Description='Durchschnittliche Verkaufsmenge der letzten 12 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544161;
UPDATE AD_Ref_List_Trl SET Description='Durchschnittliche Verkaufsmenge der letzten 12 Wochen, hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544161 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Average sales quantity over the last 12 weeks, projected onto the forecast horizon', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544161 AND AD_Language='en_US';

-- AVG_PREV_CALENDAR_YEAR (544162)
UPDATE AD_Ref_List SET Description='Durchschnittliche wöchentliche Verkaufsmenge des gesamten Vorjahres (Jan-Dez), hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544162;
UPDATE AD_Ref_List_Trl SET Description='Durchschnittliche wöchentliche Verkaufsmenge des gesamten Vorjahres (Jan-Dez), hochgerechnet auf den Prognosehorizont', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544162 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Average weekly sales quantity of the entire previous calendar year (Jan-Dec), projected onto the forecast horizon', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544162 AND AD_Language='en_US';

-- AVG_SAME_PERIOD_PREV_YEAR (544163)
UPDATE AD_Ref_List SET Description='Verkaufsmenge des gleichen Zeitraums im Vorjahr (z.B. KW10 2025 für KW10 2026), ohne Durchschnittsbildung', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544163;
UPDATE AD_Ref_List_Trl SET Description='Verkaufsmenge des gleichen Zeitraums im Vorjahr (z.B. KW10 2025 für KW10 2026), ohne Durchschnittsbildung', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544163 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Sales quantity of the same period in the previous year (e.g. CW10 2025 for CW10 2026), without averaging', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544163 AND AD_Language='en_US';

-- ==========================================================================
-- AD_Ref_List tooltips for ForecastPrecisionUnit (AD_Reference 542073)
-- ==========================================================================

-- WEEK (544164)
UPDATE AD_Ref_List SET Description='Frequenz und Bevorratungszeit werden in Wochen (7-Tage-Blöcke) interpretiert. Historische Verkaufsdaten werden wochenweise ausgewertet.', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544164;
UPDATE AD_Ref_List_Trl SET Description='Frequenz und Bevorratungszeit werden in Wochen (7-Tage-Blöcke) interpretiert. Historische Verkaufsdaten werden wochenweise ausgewertet.', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544164 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Frequency and buffer time are interpreted in weeks (7-day blocks). Historical sales data is evaluated on a weekly basis.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544164 AND AD_Language='en_US';

-- MONTH (544165)
UPDATE AD_Ref_List SET Description='Frequenz und Bevorratungszeit werden in Monaten interpretiert (ca. 4,33 Wochen pro Monat). Historische Verkaufsdaten werden monatsweise ausgewertet.', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544165;
UPDATE AD_Ref_List_Trl SET Description='Frequenz und Bevorratungszeit werden in Monaten interpretiert (ca. 4,33 Wochen pro Monat). Historische Verkaufsdaten werden monatsweise ausgewertet.', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544165 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Description='Frequency and buffer time are interpreted in months (approx. 4.33 weeks per month). Historical sales data is evaluated on a monthly basis.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Ref_List_ID=544165 AND AD_Language='en_US';

-- ==========================================================================
-- 2. Make M_Forecast.M_Warehouse_ID mandatory (AD_Column_ID=557885)
-- ==========================================================================

-- First update existing forecasts that have no warehouse (set to org's default if possible)
-- Since we can't determine a sensible default here, we only make it mandatory in the AD
-- The DefaultValue=@M_Warehouse_ID@ already provides a context default in the UI
UPDATE AD_Column SET IsMandatory='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Column_ID=557885;

-- ==========================================================================
-- 3. Undisplay C_BPartner_ID and M_PriceList_ID from Prognose header tab
--    These fields are only used by the import feature, not by the forecast generator.
--    C_BPartner_ID is copied to forecast lines by the interceptor, but that's
--    also only relevant for imported forecasts.
-- ==========================================================================

-- Prognose window (328), header tab (653): C_BPartner_ID (AD_Field_ID=560567)
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=560567;

-- Prognose window (328), header tab (653): M_PriceList_ID (AD_Field_ID=56278)
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=56278;

-- Also undisplay C_BPartner_ID from Position (line) tab (654) since it's auto-copied from header
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Field_ID=554032;

-- ==========================================================================
-- 4. Make process parameter Forecast_PrecisionUnit mandatory with default 'W'
--    AD_Process_Para_ID=543146 (on process 585593)
-- ==========================================================================
UPDATE AD_Process_Para SET IsMandatory='Y', DefaultValue='W', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Process_Para_ID=543146;

-- ==========================================================================
-- 5. Update Forecast_PrecisionUnit element description and help
--    Explain: what it controls, default value, where frequency is configured
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Zeiteinheit für Frequenz und Bevorratungszeit (Woche oder Monat). Bestimmt die Granularität der Prognoseberechnung.',
  Help='Legt fest, ob Frequenz und Bevorratungszeit in Wochen oder Monaten gezählt werden. Standard: Woche. Die Frequenz und Bevorratungszeit werden pro Produkt in der Produktionsplanung (PP_Product_Planning) konfiguriert. Beispiel: Bei Zeiteinheit=Woche und Frequenz=4 wird der Prognosehorizont auf 4 Wochen berechnet; bei Zeiteinheit=Monat und Frequenz=4 auf 4 Monate.',
  Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584631, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Time unit for frequency and buffer time (week or month). Determines the granularity of the forecast calculation.',
  Help='Specifies whether frequency and buffer time are counted in weeks or months. Default: Week. Frequency and buffer time are configured per product in Product Planning (PP_Product_Planning). Example: With time unit=Week and frequency=4, the forecast horizon covers 4 weeks; with time unit=Month and frequency=4, it covers 4 months.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'en_US');
