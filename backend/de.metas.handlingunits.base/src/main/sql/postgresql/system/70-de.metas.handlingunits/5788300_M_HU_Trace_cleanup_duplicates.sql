-- migration script: cleanup duplicate M_HU_Trace records
-- gh#27425 / gh#27585
--
-- Context: A bug in HUTraceEventsService.createAndAddEvents() caused duplicate M_HU_Trace
-- records when multiple M_HU_Assignment records resolved to the same VHU via different paths.
-- Each assignment had a different Updated timestamp => different EventTime => the idempotency
-- check didn't catch the duplicate. Fixed in Java code, but existing data needs cleanup.
--
-- This script:
-- 1. Deletes exact duplicate M_HU_Trace records (same VHU_ID, document FK, product, qty, type)
-- 2. Keeps the earliest record (lowest M_HU_Trace_ID) for each group of duplicates

-- Step 1: Delete duplicate M_HU_Trace records
-- Two records are considered duplicates when they have identical:
--   VHU_ID, M_Product_ID, Qty, HUTraceType, and the same document FK (M_InOut_ID / PP_Order_ID / M_Movement_ID / M_Inventory_ID)
-- but different M_HU_Trace_ID (and possibly different EventTime).
-- We keep the record with the smallest M_HU_Trace_ID.

DELETE FROM M_HU_Trace
WHERE M_HU_Trace_ID IN (
    SELECT dup.M_HU_Trace_ID
    FROM M_HU_Trace dup
    WHERE EXISTS (
        SELECT 1
        FROM M_HU_Trace keeper
        WHERE keeper.VHU_ID = dup.VHU_ID
          AND keeper.M_Product_ID = dup.M_Product_ID
          AND keeper.Qty = dup.Qty
          AND keeper.HUTraceType = dup.HUTraceType
          AND keeper.C_UOM_ID = dup.C_UOM_ID
          AND keeper.M_HU_Trace_ID < dup.M_HU_Trace_ID
          -- match on document FK (use IS NOT DISTINCT FROM for nullable columns)
          AND keeper.M_InOut_ID IS NOT DISTINCT FROM dup.M_InOut_ID
          AND keeper.PP_Order_ID IS NOT DISTINCT FROM dup.PP_Order_ID
          AND keeper.PP_Cost_Collector_ID IS NOT DISTINCT FROM dup.PP_Cost_Collector_ID
          AND keeper.M_Movement_ID IS NOT DISTINCT FROM dup.M_Movement_ID
          AND keeper.M_Inventory_ID IS NOT DISTINCT FROM dup.M_Inventory_ID
          AND keeper.M_ShipmentSchedule_ID IS NOT DISTINCT FROM dup.M_ShipmentSchedule_ID
          AND keeper.M_HU_Trx_Line_ID IS NOT DISTINCT FROM dup.M_HU_Trx_Line_ID
          AND keeper.VHU_Source_ID IS NOT DISTINCT FROM dup.VHU_Source_ID
          AND keeper.LotNumber IS NOT DISTINCT FROM dup.LotNumber
    )
);
