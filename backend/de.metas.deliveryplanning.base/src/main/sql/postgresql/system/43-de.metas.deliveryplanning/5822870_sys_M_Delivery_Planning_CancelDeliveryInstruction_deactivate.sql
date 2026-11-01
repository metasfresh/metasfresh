-- Deactivate the "Lieferung stornieren" (cancel delivery) action on the Delivery Planning window.
-- DeliveryPlanningService#cancelDelivery calls voidLinkedDeliveryInstructions, which runs the Void
-- document action on EVERY delivery instruction linked to the invoking planning. Before the
-- aggregation work a delivery instruction belonged to exactly one planning, so voiding it from that
-- planning was closed and safe. Now one instruction can carry several plannings
-- (M_Delivery_Planning_Alloc): on a representative dataset 24 of the 114 instructions that carry a
-- planning carry more than one (20 with two, 4 with three). The action has no sibling awareness --
-- no guard, no warning -- so running it from one selected row terminally voids a document that one
-- or two UNSELECTED plannings still depend on. It is superseded by "Remove from delivery
-- instruction" (585655, which detaches only the selected planning and leaves the shared instruction
-- intact) followed by "Close" (585163).
--
-- The AD_Process (585195) and its only caller, DeliveryPlanningService#cancelDelivery, are left in
-- place -- only the two AD rows that expose the action are deactivated, so the change reverses by
-- flipping two flags back. There is no AD_Menu entry and no AD_Process_Para for this process, and
-- exactly one AD_Table_Process row points at it, so the process + that placement are the entire
-- surface. The other eleven active AD_Table_Process rows on M_Delivery_Planning are untouched.
--
-- IDs referenced (pre-existing, allocated when the action was first built -- gh#14444):
--   AD_Process       585195 (M_Delivery_Planning_CancelDeliveryInstruction)
--   AD_Table_Process 541342 (its placement on the M_Delivery_Planning window action menu)

UPDATE AD_Process
SET IsActive='N', Updated=TO_TIMESTAMP('2026-09-07 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585195
;

UPDATE AD_Table_Process
SET IsActive='N', Updated=TO_TIMESTAMP('2026-09-07 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_Process_ID=541342
;
