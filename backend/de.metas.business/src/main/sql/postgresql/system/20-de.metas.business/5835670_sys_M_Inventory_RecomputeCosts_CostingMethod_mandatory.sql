-- Run mode: WEBUI

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- Make the costing method mandatory: an empty value recomputed ALL active material cost elements, which is very slow and can deadlock.
-- 2026-11-13T10:00:00.000Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=1000001
;

-- Texts: the parameter is no longer optional, so drop the "Optional ... leave empty" wording (see 5799780, 5799740, 5400270).
UPDATE AD_Element
SET    Description = 'Pflichtfeld. Es werden nur die Kostenarten dieser Kostenrechnungsmethode neu berechnet. Wählen Sie die Kostenrechnungsmethode des gewählten Buchhaltungs-Schemas.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Element_ID = 584789
;

UPDATE AD_Element_Trl
SET    Description = 'Required. Only the cost elements of this costing method are recomputed. Pick the costing method of the selected accounting schema.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Element_ID = 584789 AND AD_Language = 'en_US'
;

UPDATE AD_Element_Trl
SET    Description = 'Pflichtfeld. Es werden nur die Kostenarten dieser Kostenrechnungsmethode neu berechnet. Wählen Sie die Kostenrechnungsmethode des gewählten Buchhaltungs-Schemas.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Element_ID = 584789 AND AD_Language IN ('de_DE','de_CH','fr_CH')
;

UPDATE AD_Process_Para
SET    Description = 'Pflichtfeld. Es werden nur die Kostenarten dieser Kostenrechnungsmethode neu berechnet. Wählen Sie die Kostenrechnungsmethode des gewählten Buchhaltungs-Schemas.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Process_Para_ID = 1000001
;

UPDATE AD_Process_Para_Trl
SET    Description = 'Required. Only the cost elements of this costing method are recomputed. Pick the costing method of the selected accounting schema.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Process_Para_ID = 1000001 AND AD_Language = 'en_US'
;

UPDATE AD_Process_Para_Trl
SET    Description = 'Pflichtfeld. Es werden nur die Kostenarten dieser Kostenrechnungsmethode neu berechnet. Wählen Sie die Kostenrechnungsmethode des gewählten Buchhaltungs-Schemas.',
       Updated     = TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy   = 100
WHERE  AD_Process_Para_ID = 1000001 AND AD_Language IN ('de_DE','de_CH','fr_CH')
;

