-- Direction filter for the three "add purchase document to transport order" pickers
-- (C_Order_AddTo_M_ShipperTransportation, C_OrderLine_AddTo_M_ShipperTransportation,
-- M_ReceiptSchedule_AddTo_M_ShipperTransportation), whose M_ShipperTransportation_ID parameter
-- currently uses the shared AD_Val_Rule 540248 (M_ShipperTransportation_Open) together with six other
-- consumers (add-shipment ×2, HU packing ×2, tour instance) that must keep exactly today's behaviour.
--
-- A shared rule cannot carry this clause: @C_Order_ID@ resolves only where the launching record (or,
-- for C_OrderLine/M_ReceiptSchedule, the record's own C_Order_ID column) makes it available, and adding
-- a direction predicate to 540248 would leave a predicate meaningless to the other six consumers. So this
-- is a FORKED rule - a new AD_Val_Rule built from 540248's own predicates (Processed + the DocSubType<>'DI'
-- exclusion, both left untouched from 540248) plus the direction clause, applied ONLY to the three AddTo
-- processes' AD_Process_Para rows. 540248 and 540468 themselves are not modified by this script.
--
-- Direction rule: a purchase order/line/receipt-schedule reaches an order via C_Order_ID (a header record
-- for C_Order_AddTo, a join column for the other two - either way @C_Order_ID@ resolves the same way), and
-- IsSOTrx on that order decides which TransportDirection values are compatible: a purchase order
-- (IsSOTrx='N') only fits Incoming or Dropship transport orders; a sales order (IsSOTrx='Y') only fits
-- Outgoing or Dropship. With no order in context (@C_Order_ID/0@ = 0) every open transport order is
-- offered, unchanged from 540248's own behaviour - this is what keeps a picker safe to use with no
-- selection yet.

-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_Val_Rule 540799 (new M_ShipperTransportation_Open_ForOrderDirection)

-- 2026-09-03T00:10:00.000Z
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Type, Code, EntityType)
VALUES (
  540799 /*From ID Server*/,
  0, 0, 'Y',
  TO_TIMESTAMP('2026-09-03 00:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-09-03 00:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'M_ShipperTransportation_Open_ForOrderDirection',
  'S',
  'M_ShipperTransportation.Processed=''N''
AND EXISTS (SELECT 1 FROM C_DocType dt
             WHERE dt.C_DocType_ID = M_ShipperTransportation.C_DocType_ID
               AND (dt.DocSubType IS NULL OR dt.DocSubType <> ''DI''))
AND ( @C_Order_ID/0@ = 0
   OR EXISTS (SELECT 1 FROM C_Order o
               WHERE o.C_Order_ID = @C_Order_ID/0@
                 AND ( (o.IsSOTrx = ''N'' AND M_ShipperTransportation.TransportDirection IN (''Incoming'',''Dropship''))
                    OR (o.IsSOTrx = ''Y'' AND M_ShipperTransportation.TransportDirection IN (''Outgoing'',''Dropship'')) )) )',
  'D'
)
;

-- Repoint ONLY the three AddTo processes' M_ShipperTransportation_ID parameter to the new rule.
-- 2026-09-03T00:10:01.000Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540799 /*From ID Server*/, Updated=TO_TIMESTAMP('2026-09-03 00:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=541688 /* C_Order_AddTo_M_ShipperTransportation.M_ShipperTransportation_ID */
;

-- 2026-09-03T00:10:02.000Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540799 /*From ID Server*/, Updated=TO_TIMESTAMP('2026-09-03 00:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=543171 /* C_OrderLine_AddTo_M_ShipperTransportation.M_ShipperTransportation_ID */
;

-- 2026-09-03T00:10:03.000Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540799 /*From ID Server*/, Updated=TO_TIMESTAMP('2026-09-03 00:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=543019 /* M_ReceiptSchedule_AddTo_M_ShipperTransportation.M_ShipperTransportation_ID */
;
