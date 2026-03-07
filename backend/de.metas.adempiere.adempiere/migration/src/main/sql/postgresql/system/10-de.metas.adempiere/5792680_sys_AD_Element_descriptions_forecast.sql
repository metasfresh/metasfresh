-- Migration: Add Description and Help texts to Forecast Generator elements
-- Purpose: Improve usability by providing tooltips and help texts for all forecast-related fields and process parameters

-- ==========================================================================
-- AD_Element: Forecast_CalculationMethod (584630)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Berechnungsmethode für die Prognosemenge basierend auf historischen Verkaufsdaten',
  Help='Bestimmt, welcher Zeitraum der Verkaufshistorie für die Durchschnittsberechnung herangezogen wird. Optionen: Durchschnitt der letzten 52, 26 oder 12 Wochen; Durchschnitt des gesamten Vorjahres; oder phasengleicher Vergleich zum Vorjahr (gleicher Zeitraum, ein Jahr zurück).',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584630 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584630, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584630, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Calculation method for the forecast quantity based on historical sales data',
  Help='Determines which period of sales history is used for the average calculation. Options: Average of the last 52, 26, or 12 weeks; average of the entire previous calendar year; or phase-aligned comparison to the previous year (same period, one year back).',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584630 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584630, 'en_US');

-- ==========================================================================
-- AD_Element: Forecast_PrecisionUnit (584631)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Zeiteinheit für die Prognoseberechnung (Woche oder Monat)',
  Help='Legt fest, ob die Prognose in Wochen oder Monaten berechnet wird. Die Zeiteinheit beeinflusst die Frequenz und die Bevorratungszeit.',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584631, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Time unit for the forecast calculation (week or month)',
  Help='Specifies whether the forecast is calculated in weeks or months. The time unit affects the frequency and buffer time.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'en_US');

-- ==========================================================================
-- AD_Element: Forecast_Frequency (584632)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Anzahl Zeiteinheiten, die der Prognosehorizont abdeckt (Bestellzykluslänge)',
  Help='Gibt an, wie viele Wochen oder Monate die Prognose in die Zukunft reicht. Entspricht der Bestellzykluslänge: wie lange der Bestand nach einer Bestellung reichen soll. Wenn leer, wird die Wiederbeschaffungszeit (DeliveryTime_Promised) als Standardwert verwendet.',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584632 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584632, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584632, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Number of time units the forecast horizon covers (order cycle length)',
  Help='Specifies how many weeks or months the forecast extends into the future. Corresponds to the order cycle length: how long the stock should last after an order. If empty, the lead time (DeliveryTime_Promised) is used as default.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584632 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584632, 'en_US');

-- ==========================================================================
-- AD_Element: Forecast_BufferTime (584633)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Zusätzliche Bevorratungszeit in Zeiteinheiten, die zum Prognosehorizont addiert wird',
  Help='Sicherheitspuffer, der auf den Prognosehorizont (Frequenz) aufgeschlagen wird. Beispiel: Bei einer Frequenz von 4 Wochen und einer Bevorratungszeit von 8 Wochen beträgt der gesamte Prognosehorizont 12 Wochen.',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584633 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584633, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584633, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Additional buffer time in time units added to the forecast horizon',
  Help='Safety buffer added on top of the forecast horizon (frequency). Example: With a frequency of 4 weeks and a buffer time of 8 weeks, the total forecast horizon is 12 weeks.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584633 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584633, 'en_US');

-- ==========================================================================
-- AD_Element: IsExcludeFromForecast (584634)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Wenn aktiv, wird dieses Produkt bei der Prognosegenerierung übersprungen',
  Help='Produkte oder Produktkategorien mit diesem Kennzeichen werden vom Prognosegenerator ignoriert. Nützlich für Auslaufartikel oder Produkte, die nicht auf Basis historischer Daten prognostiziert werden sollen.',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584634 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584634, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584634, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='If set, this product is skipped during forecast generation',
  Help='Products or product categories with this flag are ignored by the forecast generator. Useful for discontinued items or products that should not be forecasted based on historical data.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584634 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584634, 'en_US');

-- ==========================================================================
-- AD_Element: IsDeleteExistingLines (584635)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Wenn aktiv, werden vor der Generierung alle bestehenden Prognosezeilen gelöscht',
  Help='Löscht alle bestehenden Prognosezeilen, bevor neue generiert werden. Nützlich, um eine Prognose komplett neu zu berechnen. Wenn nicht aktiv, werden neue Zeilen zu den bestehenden hinzugefügt.',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584635 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584635, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584635, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='If set, all existing forecast lines are deleted before generation',
  Help='Deletes all existing forecast lines before generating new ones. Useful to completely recalculate a forecast. If not set, new lines are added to the existing ones.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584635 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584635, 'en_US');

-- ==========================================================================
-- AD_Element: M_Forecast_GenerateLines (584636) — the process element
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Erzeugt Prognosezeilen basierend auf historischen Verkaufsdaten und Produktplanungskonfiguration',
  Help='Dieser Prozess erstellt automatisch Prognosezeilen (M_ForecastLine) für alle Produkte, die in der Produktionsplanung (PP_Product_Planning) eine Prognosekonfiguration haben. Die Berechnung basiert auf abgeschlossenen Kundenaufträgen und kann pro Produkt individuell konfiguriert werden (Berechnungsmethode, Zeiteinheit, Frequenz, Bevorratungszeit).',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584636 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584636, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584636, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Generates forecast lines based on historical sales data and product planning configuration',
  Help='This process automatically creates forecast lines (M_ForecastLine) for all products that have a forecast configuration in product planning (PP_Product_Planning). The calculation is based on completed sales orders and can be individually configured per product (calculation method, time unit, frequency, buffer time).',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584636 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584636, 'en_US');

-- ==========================================================================
-- AD_Process: M_Forecast_GenerateLines (585593)
-- AD_Process has no AD_Element_ID column, so we update it directly
-- ==========================================================================
UPDATE AD_Process SET
  Description='Erzeugt Prognosezeilen basierend auf historischen Verkaufsdaten und Produktplanungskonfiguration',
  Help='Dieser Prozess erstellt automatisch Prognosezeilen (M_ForecastLine) für alle Produkte, die in der Produktionsplanung (PP_Product_Planning) eine Prognosekonfiguration haben. Die Berechnung basiert auf abgeschlossenen Kundenaufträgen und kann pro Produkt individuell konfiguriert werden (Berechnungsmethode, Zeiteinheit, Frequenz, Bevorratungszeit).',
  Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593;

UPDATE AD_Process_Trl SET
  Description='Generates forecast lines based on historical sales data and product planning configuration',
  Help='This process automatically creates forecast lines (M_ForecastLine) for all products that have a forecast configuration in product planning (PP_Product_Planning). The calculation is based on completed sales orders and can be individually configured per product (calculation method, time unit, frequency, buffer time).',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-07 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593 AND AD_Language='en_US';
