-- gh#28675: Fix AD_Element German text not taking effect
--
-- Root cause: Script 5793150 updated AD_Element.Description/Help directly
-- with German text, but did NOT update AD_Element_Trl for de_DE (base language).
-- The stale de_DE TRL row gets synced back to AD_Element, overwriting the change.
--
-- Fix: Update AD_Element_Trl for de_DE, then propagate via
-- update_ad_element_on_ad_element_trl_update() which syncs base-language
-- TRL back to AD_Element.

-- ==========================================================================
-- 1. Forecast_PrecisionUnit (AD_Element 584631)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Zeiteinheit für Prognoseberechnung und -horizont: Woche (W) oder Monat (M).',
  Help='Bestimmt die Einheit, in der Frequenz und Bevorratungszeit gemessen werden. Beispiel: Bei Zeiteinheit=Woche und Frequenz=4 beträgt der Prognosehorizont 4 Wochen. Standard: Woche.',
  Updated=TO_TIMESTAMP('2026-03-10 09:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584631 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584631, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584631, 'de_DE');

-- ==========================================================================
-- 2. Forecast_Frequency (AD_Element 584632)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Bestellzyklus in Prognose-Zeiteinheiten. Wenn leer, wird die Wiederbeschaffungszeit verwendet.',
  Help='Anzahl Wochen oder Monate, die der Bestand nach einer Bestellung reichen soll. Die Prognosemenge ergibt sich aus: Ø Absatz pro Zeiteinheit × (Frequenz + Bevorratungszeit). Beispiel: Frequenz=4 Wochen + Bevorratungszeit=2 Wochen → Prognosemenge deckt 6 Wochen Bedarf. Wenn leer, wird die Wiederbeschaffungszeit (Lieferzeit) als Frequenz verwendet.',
  Updated=TO_TIMESTAMP('2026-03-10 09:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584632 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584632, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584632, 'de_DE');

-- ==========================================================================
-- 3. Forecast_BufferTime (AD_Element 584633)
-- ==========================================================================
UPDATE AD_Element_Trl SET
  Description='Sicherheitspuffer in Zeiteinheiten, zusätzlich zur Frequenz.',
  Help='Erhöht den Prognosehorizont über den reinen Bestellzyklus hinaus. Die Prognosemenge ergibt sich aus: Ø Absatz pro Zeiteinheit × (Frequenz + Bevorratungszeit). Beispiel: Frequenz=4 + Bevorratungszeit=2 = 6 Wochen Bedarf als Prognosemenge.',
  Updated=TO_TIMESTAMP('2026-03-10 09:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584633 AND AD_Language='de_DE';

SELECT update_ad_element_on_ad_element_trl_update(584633, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584633, 'de_DE');

-- ==========================================================================
-- 4. Propagate Stichtag element (584644) to AD_Field 560570
--    The original script created element 584644 and set AD_Field.AD_Name_ID=584644,
--    but never called propagation functions. So AD_Field.Name stayed "Zugesagter Termin"
--    because the AD_Field interceptor (which normally does this) doesn't fire in SQL.
-- ==========================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584644, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584644, 'en_US');
