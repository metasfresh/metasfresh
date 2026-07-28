-- gh30591 nShift Lieferweg: make C_Order_Carrier_Service.C_Order_ID mandatory.
--
-- Rows in C_Order_Carrier_Service are ONLY ever created by the order interceptor
-- (OrderLineShipmentScheduleHandler / C_OrderCarrierServiceRepository.deleteByOrderId + re-insert),
-- so C_Order_ID is always populated in practice. A precautionary NULL-check is included below.
--
-- AD_Column 592986: C_Order_Carrier_Service.C_Order_ID
-- AD_MigrationScript: 5816150 (From ID Server, 2026-07-24)

-- Precautionary NULL check — should return 0 rows; fails safe if not.
-- (No backfill needed: all rows carry C_Order_ID by construction of the insert path.)
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM C_Order_Carrier_Service WHERE C_Order_ID IS NULL) THEN
        RAISE EXCEPTION 'C_Order_Carrier_Service contains rows with C_Order_ID IS NULL — backfill required before setting NOT NULL';
    END IF;
END $$;

-- AD_Column: IsMandatory = Y
UPDATE AD_Column
SET IsMandatory = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-24 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Column_ID = 592986; -- C_Order_Carrier_Service.C_Order_ID

-- Physical NOT NULL constraint
INSERT INTO t_alter_column VALUES('c_order_carrier_service','c_order_id','NUMERIC(10)','NOT NULL',null);
