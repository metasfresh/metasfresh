-- 2026-04-27 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: receipt + invoice associations on Delivery sub-rows.
-- M_InOut_ID and C_Invoice_ID are NULL on the LC row and on the remainder row;
-- NOT NULL on Delivery sub-rows is enforced by the Java sole-writer (OrderPayScheduleDeliveryService),
-- not at the DB level (LC and remainder rows legitimately lack them).

/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD COLUMN M_InOut_ID numeric DEFAULT NULL REFERENCES M_InOut(M_InOut_ID)');

/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD COLUMN C_Invoice_ID numeric DEFAULT NULL REFERENCES C_Invoice(C_Invoice_ID)');

-- Helpful partial indexes for fast schedule lookup by receipt / invoice.
CREATE INDEX IF NOT EXISTS C_OrderPaySchedule_M_InOut_ID
    ON C_OrderPaySchedule (M_InOut_ID) WHERE M_InOut_ID IS NOT NULL;

CREATE INDEX IF NOT EXISTS C_OrderPaySchedule_C_Invoice_ID
    ON C_OrderPaySchedule (C_Invoice_ID) WHERE C_Invoice_ID IS NOT NULL;
