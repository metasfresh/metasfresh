-- gh#28896: Mark phantom unprocessed M_QtyReservation rows as Processed='Y' when their
-- sales order line has already been fully shipped.
--
-- Background: a bug left active, unprocessed M_QtyReservation rows on order lines whose
-- shipment schedule is already fully shipped (M_ShipmentSchedule.Processed='Y'). These are
-- phantom reservations: the line is shipped, so no qty is actually reserved anymore. This
-- one-off data cleanup clears them by flagging Processed='Y', which removes them from the
-- "active reservations" filtering used by the cockpit.
--
-- Idempotent: the Processed='N' guard means re-running is a no-op once the rows are flagged.
-- Created: 2026-06-08 10:00

SELECT backup_table('m_qtyreservation', '_gh28896_mark_shipped_processed');

UPDATE M_QtyReservation qr
   SET Processed  = 'Y',
       Updated    = TO_TIMESTAMP('2026-06-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
 WHERE qr.IsActive  = 'Y'
   AND qr.Processed = 'N'
   AND EXISTS (SELECT 1
                 FROM M_ShipmentSchedule ss
                WHERE ss.C_OrderLine_ID = qr.C_OrderLine_ID
                  AND ss.Processed = 'Y')
;
