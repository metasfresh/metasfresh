-- Run mode: WEBUI

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- Make the costing method mandatory: an empty value recomputed ALL active material cost elements, which is very slow and can deadlock.
-- 2026-11-13T10:00:00.000Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-11-13 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=1000001
;

