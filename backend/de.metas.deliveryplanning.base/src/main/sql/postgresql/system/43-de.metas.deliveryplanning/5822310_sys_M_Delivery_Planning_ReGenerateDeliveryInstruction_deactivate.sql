-- Deactivate the "Lieferanweisungen anpassen" (re-plan / regenerate) action on the Delivery
-- Planning window. DeliveryPlanningService#regenerateDeliveryInstructions voids EVERY
-- instruction linked to the invoking planning and then generates one fresh instruction for that
-- planning alone. Before the aggregation work, a delivery instruction belonged to exactly one
-- planning, so void-then-regenerate was closed and safe. Now one instruction can carry many
-- plannings (M_Delivery_Planning_Alloc), so running this action from a single planning row voids
-- the SHARED instruction -- silently de-instructing every other planning on it -- and replaces it
-- with a fresh single-planning instruction. Its own checkSingleSelection precondition confirms the
-- single-item design: it dates from 2023-01-25, while the four actions that supersede it (Combine
-- into / Add to / Remove from / Move to delivery instruction) were all created 2026-08-27..31.
--
-- The AD_Process (585191) and its only caller, DeliveryPlanningService#regenerateDeliveryInstructions,
-- are left in place -- only the two AD rows that expose the action are deactivated, so the change
-- reverses by flipping two flags back. There is no AD_Menu entry and no AD_Process_Para for this
-- process, so the process + its AD_Table_Process placement are the entire surface. The other twelve
-- active AD_Table_Process rows on M_Delivery_Planning are untouched.
--
-- IDs referenced (pre-existing, allocated when the action was first built -- gh#14444):
--   AD_Process       585191 (M_Delivery_Planning_ReGenerateDeliveryInstruction)
--   AD_Table_Process 541339 (its placement on the M_Delivery_Planning window action menu)

UPDATE AD_Process
SET IsActive='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID=585191
;

UPDATE AD_Table_Process
SET IsActive='N', Updated=TO_TIMESTAMP('2026-09-03 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_Process_ID=541339
;
