-- Make the delivery-instruction zoom on M_Delivery_Planning.M_ShipperTransportation_ID actually work.
--
-- 5819000 built an AD_Reference "M_ShipperTransportation (DI Zoom)" (542129) whose AD_Ref_Table carries
-- AD_Window_ID=541657, then pointed AD_Column 585602 at it with AD_Reference_ID=19 -- labelled "Table" in
-- that script, but 19 is TableDir; Table is 18 (DisplayType.Table=18, DisplayType.TableDir=19). Under
-- TableDir, MLookupFactory#getLookupInfo takes the getLookup_TableDir(ctxColumnName) branch, which derives
-- the lookup from the column NAME and never reads AD_Reference_Value_ID -- so the zoom window came from
-- AD_Table.AD_Window_ID (540020, Transport Auftrag) and clicking the field on a delivery planning opened
-- the transport-order window. The reference itself was correct and simply unreachable.
--
-- 5819000 is already on a base branch, so it cannot be edited; this corrects the display type forward.
-- The reference itself needs no repair: 5819000 inserts it unconditionally and runs first by prefix
-- order, so any instance reaching this script already has it. Confirmed on two instances -- the display
-- type was the only thing wrong on either.
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript 5821350 (this file)

-- ===========================================================================================
-- DisplayType Table (18), so the reference above is consulted at all.
-- ===========================================================================================
UPDATE AD_Column
   SET AD_Reference_ID       = 18,
       AD_Reference_Value_ID = 542129,
       Updated               = TO_TIMESTAMP('2026-08-31 21:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy             = 100
 WHERE AD_Column_ID = 585602 /* M_Delivery_Planning.M_ShipperTransportation_ID */
;
