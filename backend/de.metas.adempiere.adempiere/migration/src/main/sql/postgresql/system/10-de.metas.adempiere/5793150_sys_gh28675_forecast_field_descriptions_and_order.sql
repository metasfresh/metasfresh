-- gh#28675: Improve forecast field descriptions, reorder UI elements,
--           add DatePromised range filter, hide BPartner and PriceList,
--           rename DatePromised to "Stichtag", overhaul process description
--
-- 1. PrecisionUnit: remove "overrides..." wording (process param is deactivated,
--    element now only serves the PP_Product_Planning field)
-- 2. Frequency & BufferTime: explain the formula so users understand the impact
-- 3. Move PrecisionUnit below Frequency and BufferTime in the UI
-- 4. Add DatePromised as a date range filter on the Forecast window
-- 5. Hide C_BPartner_ID and M_PriceList_ID fields
-- 6. Rename DatePromised to "Stichtag" via AD_Name_ID on AD_Field
-- 7. Overhaul process description to explain the role of DatePromised

-- ==========================================================================
-- 1. Fix Forecast_PrecisionUnit (AD_Element 584631)
-- ==========================================================================
UPDATE AD_Element SET
  Description='Zeiteinheit für Prognoseberechnung und -horizont: Woche (W) oder Monat (M).',
  Help='Bestimmt die Einheit, in der Frequenz und Bevorratungszeit gemessen werden. Beispiel: Bei Zeiteinheit=Woche und Frequenz=4 beträgt der Prognosehorizont 4 Wochen. Standard: Woche.',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631;

UPDATE AD_Element_Trl SET
  Description='Time unit for forecast calculation and horizon: Week (W) or Month (M).',
  Help='Determines the unit in which Frequency and Buffer Time are measured. Example: With time unit=Week and frequency=4, the forecast horizon covers 4 weeks. Default: Week.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'en_US');

-- ==========================================================================
-- 2. Improve Forecast_Frequency (AD_Element 584632)
-- ==========================================================================
UPDATE AD_Element SET
  Description='Bestellzyklus in Prognose-Zeiteinheiten. Wenn leer, wird die Wiederbeschaffungszeit verwendet.',
  Help='Anzahl Wochen oder Monate, die der Bestand nach einer Bestellung reichen soll. Die Prognosemenge ergibt sich aus: Ø Absatz pro Zeiteinheit × (Frequenz + Bevorratungszeit). Beispiel: Frequenz=4 Wochen + Bevorratungszeit=2 Wochen → Prognosemenge deckt 6 Wochen Bedarf. Wenn leer, wird die Wiederbeschaffungszeit (Lieferzeit) als Frequenz verwendet.',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584632;

UPDATE AD_Element_Trl SET
  Description='Order cycle in forecast time units. If empty, the lead time is used.',
  Help='Number of weeks or months the stock should last after an order. The forecast quantity is calculated as: avg. sales per time unit × (Frequency + Buffer Time). Example: Frequency=4 weeks + Buffer=2 weeks → forecast covers 6 weeks of demand. If empty, the lead time (DeliveryTime_Promised) is used as frequency.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584632 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584632, 'en_US');

-- ==========================================================================
-- 3. Improve Forecast_BufferTime (AD_Element 584633)
-- ==========================================================================
UPDATE AD_Element SET
  Description='Sicherheitspuffer in Zeiteinheiten, zusätzlich zur Frequenz.',
  Help='Erhöht den Prognosehorizont über den reinen Bestellzyklus hinaus. Die Prognosemenge ergibt sich aus: Ø Absatz pro Zeiteinheit × (Frequenz + Bevorratungszeit). Beispiel: Frequenz=4 + Bevorratungszeit=2 = 6 Wochen Bedarf als Prognosemenge.',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584633;

UPDATE AD_Element_Trl SET
  Description='Safety buffer in time units, added on top of the frequency.',
  Help='Extends the forecast horizon beyond the regular order cycle. The forecast quantity is calculated as: avg. sales per time unit × (Frequency + Buffer Time). Example: Frequency=4 + Buffer=2 = 6 weeks of demand as forecast quantity.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584633 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584633, 'en_US');

-- ==========================================================================
-- 4. Reorder UI elements: PrecisionUnit below Frequency and BufferTime
--    Current order: 10=Exclude, 30=PrecisionUnit, 40=Frequency, 50=BufferTime
--    New order:     10=Exclude, 30=Frequency, 40=BufferTime, 50=PrecisionUnit
-- ==========================================================================
UPDATE AD_UI_Element SET SeqNo=50,
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=648524; -- Forecast_PrecisionUnit: 30 -> 50

UPDATE AD_UI_Element SET SeqNo=30,
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=648525; -- Forecast_Frequency: 40 -> 30

UPDATE AD_UI_Element SET SeqNo=40,
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=648526; -- Forecast_BufferTime: 50 -> 40

-- ==========================================================================
-- 5. Add DatePromised as date range filter on Forecast window (M_Forecast)
--    AD_Column 557887 = M_Forecast.DatePromised
--    AD_UI_Element 549293 = DatePromised on tab 653
-- ==========================================================================
UPDATE AD_Column SET
  FilterOperator='B',
  IsSelectionColumn='Y',
  SelectionColumnSeqNo=10,
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Column_ID=557887; -- M_Forecast.DatePromised

UPDATE AD_UI_Element SET
  IsAllowFiltering='Y',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549293; -- DatePromised on Forecast tab

-- ==========================================================================
-- 6. Hide C_BPartner_ID and M_PriceList_ID from the Forecast window
-- ==========================================================================
UPDATE AD_UI_Element SET
  IsDisplayed='N',
  IsDisplayedGrid='N',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=549291; -- C_BPartner_ID

UPDATE AD_UI_Element SET
  IsDisplayed='N',
  IsDisplayedGrid='N',
  Updated=TO_TIMESTAMP('2026-03-09 10:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_UI_Element_ID=543146; -- M_PriceList_ID

-- ==========================================================================
-- 7. Rename DatePromised to "Stichtag" (reference date) in the Forecast window
--    via AD_Field.AD_Name_ID -> dedicated AD_Element
-- ==========================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName,
                        Description, Help,
                        Updated, UpdatedBy)
VALUES (0, 0, 584644, 'Forecast_DatePromised',
        TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y',
        'Stichtag',
        'Stichtag',
        'Bezugsdatum der Prognose. Die Verkaufshistorie wird rückwirkend ab diesem Datum ermittelt.',
        'Alle Berechnungsmethoden verwenden dieses Datum als Endpunkt des Vergleichszeitraums. Beispiel: Bei Stichtag 01.06. und Methode "Ø letzte 52 Wochen" werden die Verkäufe vom 01.06. des Vorjahres bis 01.06. ausgewertet. Die erzeugten Prognosezeilen erhalten dieses Datum als Liefertermin.',
        TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 0, 0, 584644,
       'Stichtag', 'Stichtag',
       'Bezugsdatum der Prognose. Die Verkaufshistorie wird rückwirkend ab diesem Datum ermittelt.',
       'Alle Berechnungsmethoden verwenden dieses Datum als Endpunkt des Vergleichszeitraums. Beispiel: Bei Stichtag 01.06. und Methode "Ø letzte 52 Wochen" werden die Verkäufe vom 01.06. des Vorjahres bis 01.06. ausgewertet. Die erzeugten Prognosezeilen erhalten dieses Datum als Liefertermin.',
       'N', TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), 100
FROM AD_Language l
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Element_ID=584644);

UPDATE AD_Element_Trl SET
  Name='Reference Date',
  PrintName='Reference Date',
  Description='Reference date for the forecast. Sales history is computed backwards from this date.',
  Help='All calculation methods use this date as the end of the comparison period. Example: With reference date June 1 and method "Avg last 52 weeks", sales from June 1 of the previous year through June 1 are evaluated. Generated forecast lines receive this date as their promised date.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584644 AND AD_Language='en_US';

-- Point AD_Field.AD_Name_ID to the new element (overrides label in the Forecast window)
UPDATE AD_Field SET
  AD_Name_ID=584644,
  Updated=TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=560570; -- M_Forecast.DatePromised field on Forecast tab 653

-- ==========================================================================
-- 8. Overhaul process description to clarify the role of DatePromised
--    AD_Process 585593 = M_Forecast_GenerateLines
--    Process Name/Description/Help come from AD_Process directly (no AD_Element link)
-- ==========================================================================
UPDATE AD_Process SET
  Description='Berechnet die Prognosemenge pro Produkt anhand der Verkaufshistorie. Der Stichtag (Zugesagter Termin) der Prognose bestimmt, ab wann rückwirkend ausgewertet wird.',
  Help='Die Verkaufshistorie wird rückwirkend vom Stichtag der Prognose berechnet. Beispiel: Stichtag 01.06., Methode "Ø letzte 52 Wochen" → Auswertungszeitraum 01.06. Vorjahr bis 01.06. Pro Produkt wird in den Produkt-Plandaten die Berechnungsmethode, Frequenz und Bevorratungszeit konfiguriert. Die Prognosemenge ergibt sich aus: Ø Absatz pro Zeiteinheit × (Frequenz + Bevorratungszeit). Produkte ohne Berechnungsmethode werden übersprungen. Nur abgeschlossene Kundenaufträge fliessen in die Berechnung ein.',
  Updated=TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593;

UPDATE AD_Process_Trl SET
  Description='Calculates the forecast quantity per product based on sales history. The reference date (Date Promised) of the forecast determines the end of the comparison period.',
  Help='Sales history is computed backwards from the forecast''s reference date. Example: Reference date June 1, method "Avg last 52 weeks" → evaluation period June 1 previous year through June 1. Per product, the calculation method, frequency, and buffer time are configured in Product Planning. Forecast quantity = avg. sales per time unit × (Frequency + Buffer Time). Products without a calculation method are skipped. Only completed sales orders are included.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-09 12:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593 AND AD_Language='en_US';
