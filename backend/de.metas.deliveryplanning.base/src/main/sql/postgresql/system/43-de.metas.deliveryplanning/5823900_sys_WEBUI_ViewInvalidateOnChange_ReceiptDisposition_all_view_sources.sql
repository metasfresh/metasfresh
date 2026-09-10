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
--   M_InOut, M_InOutLine     ATA = min movementdate of the completed receipts
--   C_Order                  PreparationDate -> ETD / ATD on unplanned rows
--   M_ReceiptSchedule_Alloc  links the schedule to the receipt lines behind ATA
--
-- Trade-off, stated deliberately: this is the COARSE mechanism (window + table, no link column), so any write to
-- one of these tables invalidates the window's open views even when it touches no column the view shows. C_Order
-- and M_InOut are high-traffic, so if this proves noisy the remedy is AD_ViewSource rows with
-- AD_ViewSource_Column narrowing -- which additionally needs the view to expose the source ids as link columns.
-- Correctness first; narrowing is an optimisation and is recorded as such.
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
SELECT 540008 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-09-10 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-09-10 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,
       542190,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine')
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID=542190
      AND existing.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine')
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
