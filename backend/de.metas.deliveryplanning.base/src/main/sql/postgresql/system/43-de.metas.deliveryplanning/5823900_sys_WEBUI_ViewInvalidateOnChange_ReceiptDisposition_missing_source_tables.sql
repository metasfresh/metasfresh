-- Completes grid invalidation for the receipt-disposition delivery-planning window (AD_Window 542190, backed by
-- the view RV_ReceiptDisposition_DeliveryPlanning).
--
-- 5822560 registered only M_Delivery_Planning and M_ReceiptSchedule, and its own comment said "an edit to
-- EITHER source table" -- but the view reads EIGHT. Authoritative list, from the database rather than by reading
-- the SQL by eye:
--
--   SELECT table_name FROM information_schema.view_table_usage
--    WHERE view_name = 'rv_receiptdisposition_deliveryplanning';
--   -> c_order, m_delivery_planning, m_inout, m_inoutline, m_receiptschedule,
--      m_receiptschedule_alloc, m_shippertransportation, m_shippingpackage
--
-- ConfiguredViewInvalidationListener filters the changed tables by the CONFIGURED trigger tables and returns 0
-- otherwise, so a write to any of the six unregistered tables left an open grid serving stale rows until the
-- user reloaded by hand. The most visible case: ContainerNo / IsBLReceived / IsBookingConfirmed / IsWENotice are
-- IsDisplayed='Y', IsReadOnly='N', IsUpdateable='Y' on BOTH delivery-instruction windows (540020, 541657) and
-- are shown in this grid, so editing a transport order changed nothing on screen.
--
-- Note the cucumber coverage could not have caught this: the acceptance scenarios read the view directly, which
-- proves the view COMPUTES the right value, never that the grid REFRESHES.
--
-- Why each of the six, individually:
--   M_ShipperTransportation  ContainerNo + the three flags (branch one via dp, branch two via the LATERAL)
--   M_ShippingPackage        no projected column, but its M_ShipperTransportation_ID / order-line FKs decide
--                            WHICH transport order a branch-two row reads -- it affects output via the join path
--   M_InOut                  ATA = min(movementdate) over the receipts in docstatus CO/CL. Registered for the
--                            REVERSAL case specifically: receipts are created ALREADY completed (measured:
--                            every inout carrying active receipt-allocs is CO or RE, none drafted), so receipt
--                            CREATION is already covered by the M_ReceiptSchedule_Alloc row below. What only
--                            M_InOut carries is CO -> RE: a reversal drops the receipt out of the CO/CL filter,
--                            changing ATA, while the allocs stay active (all 372 RE inouts here still have
--                            rsa.isactive='Y'). Not registered for M_InOutLine: it is a pure join bridge
--                            (rsa.m_inoutline_id -> iol.m_inout_id), contributes no value of its own, and its
--                            FKs do not change after creation - which the alloc row already covers.
--   C_Order                  PreparationDate -> ETD / ATD on unplanned rows
--   M_ReceiptSchedule_Alloc  links the schedule to the receipt lines behind ATA
--
-- Trade-off, stated deliberately: this is the COARSE mechanism (window + table, no link column), so any write to
-- one of these tables invalidates the window's open views even when it touches no column the view shows. C_Order
-- and M_InOut are high-traffic. KNOWN IMPRECISION, accepted deliberately: M_InOut serves BOTH directions, so a
-- SALES shipment write invalidates this purchase-side window's open views even though a sales inout can never
-- reach the ATA subselect (it is only reachable through m_receiptschedule_alloc, which links receipt schedules
-- to receipt lines). The mechanism is table-level with no predicate, so it cannot express "purchase only";
-- ConfiguredViewInvalidationListener's isWatchedByFrontend filter bounds the cost to views someone actually
-- has open. The alternative was to drop the row and lose live refresh on reversal.
--
-- If that proves noisy, the narrowing option is AD_ViewSource + AD_ViewSource_Column -- but note what it costs
-- HERE, because this window's shape makes it more than a config row. AD_ViewSource does not build a request
-- itself: ViewSourceCacheInvalidateRequestFactory filters the VIEW by a link column (so the view's internal join
-- depth is irrelevant -- only that it EXPOSES the source's id), then delegates the request SHAPE to the
-- window-based factories keyed on the target table, seeded from AD_Window_ParentChildTableNames_v1. There:
--
--   RV_ReceiptDisposition_DeliveryPlanning        parent with a NULL child  -> Direct factory
--                                                 -> request names the VIEW table
--   M_Delivery_Planning_Delivery_Instructions_V   CHILD of M_Delivery_Planning -> ParentChild factory
--                                                 -> root M_Delivery_Planning + child the view
--
-- The sibling view is a child TAB, so its request lands on M_Delivery_Planning's existing trigger row and the
-- loop closes with nothing extra. Ours is a standalone view window (the view IS the root, tabLevel 0), so its
-- request names the view table and reaches nothing unless a WEBUI_ViewInvalidateOnChange row for
-- window 542190 + the VIEW table (AD_Table 542644) is added too. So the narrowing route is THREE pieces per
-- source -- the exposed link column, the AD_ViewSource row, and that closing trigger row -- and it replaces
-- nothing below; it only makes invalidation more selective. Correctness first; narrowing is an optimisation.
--
-- DELIBERATE GAP - do not "complete" it without reading the M_InOut rationale above: of the view's eight source
-- tables, m_inoutline is intentionally NOT registered. After this script the anti-join
--
--   SELECT v.table_name FROM information_schema.view_table_usage v
--    WHERE v.view_name='rv_receiptdisposition_deliveryplanning'
--      AND NOT EXISTS (SELECT 1 FROM WEBUI_ViewInvalidateOnChange w JOIN AD_Table t ON t.AD_Table_ID=w.AD_Table_ID
--                       WHERE w.AD_Window_ID=542190 AND w.IsActive='Y' AND lower(t.TableName)=v.table_name);
--
-- returns exactly one row, m_inoutline, BY DESIGN. Recording that here because the two-table gap this script
-- fixes existed precisely because nobody wrote down whether it was intent or oversight.
--
-- Idempotent per row, and touches no row registered for another window.

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540005 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ShipperTransportation')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ShipperTransportation')
      AND existing.IsActive='Y'
)
;

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540006 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:03','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:03','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ShippingPackage')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ShippingPackage')
      AND existing.IsActive='Y'
)
;

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540007 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:04','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:04','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOut')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOut')
      AND existing.IsActive='Y'
)
;

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540009 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_Order')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_Order')
      AND existing.IsActive='Y'
)
;

INSERT INTO WEBUI_ViewInvalidateOnChange (WEBUI_ViewInvalidateOnChange_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID,AD_Table_ID)
SELECT 540010 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:07','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ReceiptSchedule_Alloc')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_ReceiptSchedule_Alloc')
      AND existing.IsActive='Y'
)
;
