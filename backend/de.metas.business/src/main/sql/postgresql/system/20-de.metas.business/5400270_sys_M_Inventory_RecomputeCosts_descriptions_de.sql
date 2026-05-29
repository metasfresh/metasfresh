-- 2026-04-28 Push the German texts of M_Inventory_RecomputeCosts process + parameters into
-- the de_DE / de_CH translation rows.
--
-- Migration 5799740 set Description / Help on AD_Process 585275 (base) and AD_Process_Para
-- 542650 / 1000001 (base). It also set the en_US AD_Process_Trl. But the German trl rows
-- (de_DE, de_CH) on AD_Process_Trl and AD_Process_Para_Trl still carried the pre-fix generic
-- text ("Berechnen Sie die Kosten ...", "Stammdaten für Buchhaltung", "Gibt an, wie die
-- Kosten berechnet werden"). The WebUI serves de_CH users from the de_CH trl row directly
-- (IsTranslated=Y), so they kept seeing the old text after the fix.
--
-- This migration fills both German trl rows with the new Description / Help.

-- AD_Process_Trl — German rows for M_Inventory_RecomputeCosts (AD_Process_ID = 585275)
UPDATE AD_Process_Trl
SET    Description  = 'Berechnet die laufenden Kosten für die Produkte der ausgewählten Inventuren neu, indem alle betroffenen Buchhaltungsbelege ab dem ältesten Bewegungsdatum der Auswahl chronologisch zurückgebucht und neu gebucht werden.',
       Help         = 'Anwendung: nach dem Verbuchen einer oder mehrerer Inventuren, die Bestand und/oder Kostenpreis korrigieren. Voraussetzung: jede Buchungsperiode zwischen dem ältesten Bewegungsdatum der Auswahl und heute muss offen sein. Der Prozess (1) löscht M_CostDetail-Zeilen ab dem Startdatum, (2) bucht jeden betroffenen Beleg zurück und (3) reiht ihn zur chronologischen Neubuchung in die Warteschlange ein. Sobald die Warteschlange leer ist, anschließend den Prozess „Rebuild FactAcct Summary" ausführen.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-28 06:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_ID = 585275
  AND  AD_Language   IN ('de_DE','de_CH');

-- AD_Process_Para_Trl — German rows for the C_AcctSchema_ID parameter (AD_Process_Para_ID = 542650)
UPDATE AD_Process_Para_Trl
SET    Description  = 'Das Buchhaltungs-Schema, das neu berechnet werden soll. Wählen Sie das Schema, dessen Kostenrechnungsmethode zu Ihrer Kostenanpassung passt (typischerweise „Bestellpreis Durchschnitt").',
       Help         = NULL,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-28 06:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 542650
  AND  AD_Language        IN ('de_DE','de_CH');

-- AD_Process_Para_Trl — German rows for the CostingMethod parameter (AD_Process_Para_ID = 1000001)
UPDATE AD_Process_Para_Trl
SET    Description  = 'Optional. Wenn gesetzt, werden nur die Kostenarten dieser Methode neu berechnet. Leer lassen, um alle aktiven Material-Kostenarten neu zu berechnen (Standardfall).',
       Help         = NULL,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-28 06:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 1000001
  AND  AD_Language        IN ('de_DE','de_CH');
