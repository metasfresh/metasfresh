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
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript 5821350 (this file)

-- ===========================================================================================
-- 1) Guard: the reference 5819000 created must be in place, otherwise pointing a column at it
--    would leave the field with a dangling lookup. Re-created here only if it is missing, so an
--    instance that somehow lacks it is repaired rather than broken.
-- ===========================================================================================
INSERT INTO AD_Reference
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, ValidationType, EntityType)
SELECT 542129, 0, 0, 'Y',
       TO_TIMESTAMP('2026-08-31 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-31 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'M_ShipperTransportation (DI Zoom)', 'T', 'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Reference WHERE AD_Reference_ID = 542129)
;

INSERT INTO AD_Ref_Table
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Key, AD_Display, AD_Window_ID, EntityType)
SELECT 542129, 0, 0, 'Y',
       TO_TIMESTAMP('2026-08-31 21:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-31 21:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       540030, 540426, 540439, 541657, 'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_Table WHERE AD_Reference_ID = 542129)
;

-- The zoom window is the whole point of the reference, so repair it if an instance carries the row
-- with a different target.
UPDATE AD_Ref_Table
   SET AD_Window_ID = 541657,
       Updated      = TO_TIMESTAMP('2026-08-31 21:00:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Reference_ID = 542129
   AND (AD_Window_ID IS NULL OR AD_Window_ID <> 541657)
;

-- ===========================================================================================
-- 2) The actual fix: DisplayType Table (18), so the reference above is consulted at all.
-- ===========================================================================================
UPDATE AD_Column
   SET AD_Reference_ID       = 18,
       AD_Reference_Value_ID = 542129,
       Updated               = TO_TIMESTAMP('2026-08-31 21:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy             = 100
 WHERE AD_Column_ID = 585602 /* M_Delivery_Planning.M_ShipperTransportation_ID */
;
