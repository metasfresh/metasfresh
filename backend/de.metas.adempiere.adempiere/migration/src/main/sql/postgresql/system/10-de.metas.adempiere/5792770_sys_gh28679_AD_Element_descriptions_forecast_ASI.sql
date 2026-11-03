-- Migration: Update forecast generator descriptions to document ASI propagation from PP_Product_Planning
-- gh#28679: When a forecast line has no ASI, the system enriches it from PP_Product_Planning

-- ==========================================================================
-- AD_Element: M_Forecast_GenerateLines (584636) — add ASI propagation info
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Help='Dieser Prozess erstellt automatisch Prognosezeilen (M_ForecastLine) für alle Produkte, die in der Produktionsplanung (PP_Product_Planning) eine Prognosekonfiguration haben. Die Berechnung basiert auf abgeschlossenen Kundenaufträgen und kann pro Produkt individuell konfiguriert werden (Berechnungsmethode, Zeiteinheit, Frequenz, Bevorratungszeit). Falls in der Produktionsplanung eine Merkmalsausprägung (ASI) hinterlegt ist, wird diese automatisch auf die Prognosezeile übertragen.',
  Updated=TO_TIMESTAMP('2026-03-07 17:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584636 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584636, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584636, 'de_DE');

UPDATE AD_Element_Trl SET
  Help='This process automatically creates forecast lines (M_ForecastLine) for all products that have a forecast configuration in product planning (PP_Product_Planning). The calculation is based on completed sales orders and can be individually configured per product (calculation method, time unit, frequency, buffer time). If the product planning record has an attribute set instance (ASI), it is automatically propagated to the forecast line.',
  Updated=TO_TIMESTAMP('2026-03-07 17:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584636 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584636, 'en_US');

-- ==========================================================================
-- AD_Process: M_Forecast_GenerateLines (585593) — same update
-- Note: AD_Process has no AD_Element_ID column, so we update it directly
-- ==========================================================================
UPDATE AD_Process SET
  Help='Dieser Prozess erstellt automatisch Prognosezeilen (M_ForecastLine) für alle Produkte, die in der Produktionsplanung (PP_Product_Planning) eine Prognosekonfiguration haben. Die Berechnung basiert auf abgeschlossenen Kundenaufträgen und kann pro Produkt individuell konfiguriert werden (Berechnungsmethode, Zeiteinheit, Frequenz, Bevorratungszeit). Falls in der Produktionsplanung eine Merkmalsausprägung (ASI) hinterlegt ist, wird diese automatisch auf die Prognosezeile übertragen.',
  Updated=TO_TIMESTAMP('2026-03-07 17:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593;

UPDATE AD_Process_Trl SET
  Help='This process automatically creates forecast lines (M_ForecastLine) for all products that have a forecast configuration in product planning (PP_Product_Planning). The calculation is based on completed sales orders and can be individually configured per product (calculation method, time unit, frequency, buffer time). If the product planning record has an attribute set instance (ASI), it is automatically propagated to the forecast line.',
  Updated=TO_TIMESTAMP('2026-03-07 17:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_ID=585593 AND AD_Language='en_US';
