-- 2026-04-27 Sharper descriptions for the M_Inventory_RecomputeCosts process and its parameters.
--
-- AD_Process has no AD_Element_ID in the current schema, so the process Name/Description/Help
-- live directly on AD_Process and on AD_Process_Trl. AD_Process_Para does have AD_Element_ID,
-- but the descriptions we want here are process-specific (not generic to the element), so we
-- override them on AD_Process_Para and AD_Process_Para_Trl directly.

-- B4 — process description (de_DE base + EN translation)
UPDATE AD_Process
SET    Description = 'Berechnet die laufenden Kosten für die Produkte der ausgewählten Inventuren neu, indem alle betroffenen Buchhaltungsbelege ab dem ältesten Bewegungsdatum der Auswahl chronologisch zurückgebucht und neu gebucht werden.',
       Help        = 'Anwendung: nach dem Verbuchen einer oder mehrerer Inventuren, die Bestand und/oder Kostenpreis korrigieren. Voraussetzung: jede Buchungsperiode zwischen dem ältesten Bewegungsdatum der Auswahl und heute muss offen sein. Der Prozess (1) löscht M_CostDetail-Zeilen ab dem Startdatum, (2) bucht jeden betroffenen Beleg zurück und (3) reiht ihn zur chronologischen Neubuchung in die Warteschlange ein. Sobald die Warteschlange leer ist, anschließend den Prozess „Rebuild FactAcct Summary" ausführen.',
       Updated     = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy   = 0
WHERE  AD_Process_ID = 585275       -- M_Inventory_RecomputeCosts
;

UPDATE AD_Process_Trl
SET    Description  = 'Recomputes the running cost for the products of the selected inventories by un-posting and chronologically re-posting every affected accounting document from the earliest selected MovementDate onwards.',
       Help         = 'Run after completing one or more inventories that adjust stock and/or cost price. Prerequisite: every accounting period between the earliest selected MovementDate and today must be open. The process (1) deletes M_CostDetail rows from the start date onwards, (2) un-posts every affected document and (3) enqueues it for chronological re-posting. Once the queue drains, run the "Rebuild FactAcct Summary" process.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_ID = 585275
  AND  AD_Language   = 'en_US'
;

-- B5 — process parameter descriptions (de_DE base + EN translation, both params)
UPDATE AD_Process_Para
SET    Description = 'Das Buchhaltungs-Schema, das neu berechnet werden soll. Wählen Sie das Schema, dessen Kostenrechnungsmethode zu Ihrer Kostenanpassung passt (typischerweise „Bestellpreis Durchschnitt").',
       Help        = NULL,
       Updated     = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy   = 0
WHERE  AD_Process_Para_ID = 542650  -- C_AcctSchema_ID on M_Inventory_RecomputeCosts
;

UPDATE AD_Process_Para_Trl
SET    Description  = 'The accounting schema to recompute. Pick the schema whose costing method matches your cost adjustment (typically "AveragePO").',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 542650
  AND  AD_Language        = 'en_US'
;

UPDATE AD_Process_Para
SET    Description = 'Optional. Wenn gesetzt, werden nur die Kostenarten dieser Methode neu berechnet. Leer lassen, um alle aktiven Material-Kostenarten neu zu berechnen (Standardfall).',
       Help        = NULL,
       Updated     = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy   = 0
WHERE  AD_Process_Para_ID = 1000001 -- CostingMethod on M_Inventory_RecomputeCosts
;

UPDATE AD_Process_Para_Trl
SET    Description  = 'Optional. If set, only cost elements of this costing method are recomputed. Leave empty to recompute all active material cost elements (the usual case).',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-27 14:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 1000001
  AND  AD_Language        = 'en_US'
;
