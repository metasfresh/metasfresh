-- Task B1: Make 7 system-managed Delivery-Planning fields read-only (AC-11)
-- Window: Lieferplanung (AD_Window_ID=541632), Tab: Lieferplanung (AD_Tab_ID=546674)
-- Mechanism: AD_Field IsReadOnly='Y' for UI-only read-only;
--   AD_Column IsUpdateable is left as-is so system code can still write these columns.
-- Fields: Processed (708911), IsClosed (708910), M_Delivery_Planning_Type (708076),
--         QtyOrdered (708090), QtyTotalOpen (708091),
--         M_ShipperTransportation_ID (710345), M_Shipper_ID (708105)
-- ActualLoadQty (708100) is NOT touched (deferred to task 17).

UPDATE AD_Field
SET    IsReadOnly = 'Y',
       Updated    = TO_TIMESTAMP('2026-08-13 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 100
WHERE  AD_Field_ID IN (708911, 708910, 708076, 708090, 708091, 710345, 708105)
  AND  AD_Tab_ID = 546674;
