-- Migration: Improve forecast process parameter descriptions (me03#28675)
-- Make it clear that per-product configuration comes from PP_Product_Planning
-- and that process parameters are overrides.

-- ==========================================================================
-- Forecast_CalculationMethod element (584630):
-- Clarify that it's an override; per-product default comes from PP_Product_Planning
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Übersteuert die pro Produkt in den Produkt-Plandaten hinterlegte Berechnungsmethode. Wenn leer, wird die Einstellung aus den Produkt-Plandaten verwendet.',
  Help='Bestimmt, welcher Zeitraum der Verkaufshistorie für die Durchschnittsberechnung herangezogen wird. Dieser Parameter übersteuert die pro Produkt in den Produkt-Plandaten (Fenster "Produkt Plandaten") konfigurierte Methode. Wenn leer, wird die dort hinterlegte Methode verwendet. Produkte ohne konfigurierte Methode werden übersprungen. Optionen: Durchschnitt der letzten 52, 26 oder 12 Wochen; Durchschnitt des gesamten Vorjahres; oder phasengleicher Vergleich zum Vorjahr.',
  Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584630 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584630, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584630, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Overrides the calculation method configured per product in Product Planning. If empty, the per-product setting is used.',
  Help='Determines which period of sales history is used for the average calculation. This parameter overrides the method configured per product in Product Planning (window "Produkt Plandaten"). If empty, the per-product method is used. Products without a configured method are skipped. Options: Average of the last 52, 26, or 12 weeks; average of the entire previous calendar year; or same-period comparison to the previous year.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584630 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584630, 'en_US');

-- ==========================================================================
-- Forecast_PrecisionUnit element (584631):
-- Clarify that it's an override; mention PP_Product_Planning window name
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Übersteuert die pro Produkt in den Produkt-Plandaten hinterlegte Zeiteinheit. Standard: Woche.',
  Help='Legt fest, ob Frequenz und Bevorratungszeit in Wochen oder Monaten gezählt werden. Dieser Parameter übersteuert die pro Produkt in den Produkt-Plandaten (Fenster "Produkt Plandaten") konfigurierte Zeiteinheit. Frequenz und Bevorratungszeit werden dort pro Produkt eingestellt. Beispiel: Bei Zeiteinheit=Woche und Frequenz=4 wird der Prognosehorizont auf 4 Wochen berechnet; bei Zeiteinheit=Monat und Frequenz=4 auf 4 Monate.',
  Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584631, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'de_DE');

UPDATE AD_Element_Trl SET
  Description='Overrides the time unit configured per product in Product Planning. Default: Week.',
  Help='Specifies whether frequency and buffer time are counted in weeks or months. This parameter overrides the time unit configured per product in Product Planning (window "Produkt Plandaten"). Frequency and buffer time are configured there per product. Example: With time unit=Week and frequency=4, the forecast horizon covers 4 weeks; with time unit=Month and frequency=4, it covers 4 months.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'en_US');

-- ==========================================================================
-- Update process description to mention the window name explicitly
-- ==========================================================================
UPDATE AD_Process SET
  Description='Erzeugt Prognosezeilen basierend auf Verkaufshistorie und Produkt-Plandaten',
  Help='Erstellt automatisch Prognosezeilen für alle Produkte mit Prognosekonfiguration in den Produkt-Plandaten (Fenster "Produkt Plandaten"). Pro Produkt wird dort die Berechnungsmethode, Zeiteinheit, Frequenz und Bevorratungszeit eingestellt. Die Parameter dieses Prozesses übersteuern die Produkt-Plandaten-Einstellungen für alle betroffenen Produkte. Produkte ohne Berechnungsmethode (weder in den Plandaten noch als Prozessparameter) werden übersprungen. Die Berechnung basiert auf abgeschlossenen Kundenaufträgen.',
  Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593;

UPDATE AD_Process_Trl SET
  Description='Generates forecast lines based on sales history and product planning data',
  Help='Automatically creates forecast lines for all products with forecast configuration in Product Planning (window "Produkt Plandaten"). Per product, the calculation method, time unit, frequency, and buffer time are configured there. The parameters of this process override the product planning settings for all affected products. Products without a calculation method (neither in planning data nor as process parameter) are skipped. Calculation is based on completed sales orders.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593 AND AD_Language='en_US';

-- ==========================================================================
-- M_Product_Category_ID parameter: explain filtering role
-- Since this uses a standard element (453), we override the description
-- at the process-parameter level, not the element level
-- ==========================================================================
UPDATE AD_Process_Para SET
  Description='Wenn gesetzt, werden nur Produkte dieser Kategorie berücksichtigt. Wenn leer, werden alle Produkte aus den Produkt-Plandaten verwendet.',
  Help='Optionaler Filter: Beschränkt die Prognosegenerierung auf Produkte einer bestimmten Kategorie. Ohne Auswahl werden alle Produkte verarbeitet, die in den Produkt-Plandaten eine Prognosekonfiguration haben.',
  IsCentrallyMaintained='N',
  Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543143;

UPDATE AD_Process_Para_Trl SET
  Description='If set, only products of this category are considered. If empty, all products from Product Planning are used.',
  Help='Optional filter: Restricts forecast generation to products of a specific category. Without selection, all products with forecast configuration in Product Planning are processed.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543143 AND AD_Language='en_US';

-- ==========================================================================
-- M_Product_ID parameter: explain single-product override
-- ==========================================================================
UPDATE AD_Process_Para SET
  Description='Wenn gesetzt, wird nur für dieses eine Produkt eine Prognosezeile erstellt.',
  Help='Optionaler Filter: Beschränkt die Prognosegenerierung auf ein einzelnes Produkt. Nützlich zum Testen oder für Nachberechnungen einzelner Artikel.',
  IsCentrallyMaintained='N',
  Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543144;

UPDATE AD_Process_Para_Trl SET
  Description='If set, a forecast line is created only for this single product.',
  Help='Optional filter: Restricts forecast generation to a single product. Useful for testing or recalculating individual items.',
  IsTranslated='Y', Updated=TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543144 AND AD_Language='en_US';

-- ==========================================================================
-- Forecast_CalculationMethod parameter: set IsCentrallyMaintained=N
-- so the process-specific description shows (not the generic element one)
-- Actually, the element description IS the process-specific one now,
-- so we keep IsCentrallyMaintained=Y (inherits from element).
-- ==========================================================================
-- No change needed here, element-level descriptions are now correct.

-- ==========================================================================
-- Forecast_PrecisionUnit parameter: same as above
-- ==========================================================================
-- No change needed here, element-level descriptions are now correct.

-- ==========================================================================
-- 6. Add AD_UI_Elements for forecast fields in PP_Product_Planning (AD_Window 540750)
--    These fields exist as AD_Fields (displayed in grid) but have no UI elements,
--    so they don't show in the single-document (detail) view.
--    Add them in a new "Prognose" group in column 2 (AD_UI_Column_ID=542075),
--    between "time" (SeqNo=20) and "org" (SeqNo=30) → use SeqNo=25.
-- ==========================================================================

-- AD_UI_ElementGroup: Prognose (in column 2)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 542075, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'forecast', 25, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);

-- UI Element: IsExcludeFromForecast (Field 774860)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 774860, 542102, 648522, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Von Prognose ausschliessen', 10, 0, 0, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);

-- UI Element: Forecast_CalculationMethod (Field 774856)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 774856, 542102, 648523, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Berechnungsmethode Prognose', 20, 0, 0, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);

-- UI Element: Forecast_PrecisionUnit (Field 774857)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 774857, 542102, 648524, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Prognose Zeiteinheit', 30, 0, 0, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);

-- UI Element: Forecast_Frequency (Field 774858)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 774858, 542102, 648525, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Prognose Frequenz', 40, 0, 0, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);

-- UI Element: Forecast_BufferTime (Field 774859)
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Org_ID, AD_Field_ID, AD_Tab_ID, AD_UI_Element_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNo_SideList, SeqNoGrid, Updated, UpdatedBy)
VALUES (0, 0, 774859, 542102, 648526, 554991, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Bevorratungszeit', 50, 0, 0, TO_TIMESTAMP('2026-03-08 23:00','YYYY-MM-DD HH24:MI'), 100);
