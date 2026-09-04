-- Task W6b: set the receipt-logistics window's selection filters, now that every filtered column
-- exists on the view (5822440..5822670). The mechanism is AD_Column.IsSelectionColumn +
-- SelectionColumnSeqNo (never a bespoke filter descriptor), matching Task W2's own convention.
--
-- The 9 columns Task W2 already configured are untouched here -- verified correct and already
-- mirroring the delivery-planning window seq-for-seq: ETA/ETD/ATD/ATA/DatePromised_Effective/
-- C_BPartner_ID/IsPlanned at seq 0 (filterable, not in the default panel -- the SAME arrangement as
-- M_Delivery_Planning's own ETA/ETD/ATA/ATD/C_BPartner_ID, and the reason ETA is "not in the default
-- filter set" on both windows), M_Warehouse_ID(50), M_Product_ID(60), C_Order_ID(80), POReference(90),
-- ContainerNo(180), AD_Org_ID(200, kept last). Dates already carry IsRangeFilter='Y' -> FilterOperator
-- 'B'. C_Order_ID (not a free-text OrderDocumentNo) is this window's deliberate order-document-number
-- filter -- an order picker sourced from the schedule, per 5822460's own header comment -- so no
-- separate OrderDocumentNo column is added here.
--
-- This script configures the 7 columns 5822660/5822670 just added:
--   seq 0   -- QtyToMove (mirrors M_Delivery_Planning.QtyTotalOpen/QtyTotalOpenPlanned, both seq 0:
--              a figure worth having on hand but not cluttering the default panel), Batch (NULL on
--              every unplanned row -- half this view's rows never have one -- so it does not belong
--              defaulted either), IsConfirmedBySupplier (mirrors M_ReceiptSchedule's own seq-0
--              placement on window 541954).
--   seq 190 -- M_Shipper_ID: a real, defaulted filter, same treatment as on the planning window
--              (M_Shipper_ID there sits at 160, its own real seq, not 0).
--   seq 192/194/196 -- IsBLReceived / IsBookingConfirmed / IsWENotice: the transport-confirmation
--              flags, mirroring their own defaulted placement (200/210/220) on M_ReceiptSchedule's
--              window 541954. Placed before AD_Org_ID(200), which stays last.
--
-- No IsRangeFilter here: none of these seven are dates. QtyToMove keeps FilterOperator 'E' (equals),
-- matching QtyTotalOpen/QtyTotalOpenPlanned on the planning window -- neither of those open-quantity
-- figures is a range filter there either.

UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=0, Updated=TO_TIMESTAMP('2026-09-04 11:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593501 /* QtyToMove */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=0, Updated=TO_TIMESTAMP('2026-09-04 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593502 /* Batch */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=0, Updated=TO_TIMESTAMP('2026-09-04 11:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593503 /* IsConfirmedBySupplier */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=190, Updated=TO_TIMESTAMP('2026-09-04 11:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593500 /* M_Shipper_ID */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=192, Updated=TO_TIMESTAMP('2026-09-04 11:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593504 /* IsBLReceived */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=194, Updated=TO_TIMESTAMP('2026-09-04 11:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593505 /* IsBookingConfirmed */;
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=196, Updated=TO_TIMESTAMP('2026-09-04 11:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593506 /* IsWENotice */;
