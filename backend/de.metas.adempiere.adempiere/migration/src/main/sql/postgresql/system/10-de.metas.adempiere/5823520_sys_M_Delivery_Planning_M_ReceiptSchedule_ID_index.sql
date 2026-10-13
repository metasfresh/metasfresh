-- Both branches of RV_ReceiptDisposition_DeliveryPlanning key off M_Delivery_Planning.M_ReceiptSchedule_ID --
-- the planned branch joins on it, the unplanned branch's NOT EXISTS probes it -- and there was no index, so
-- both seq-scanned the table on every page fetch.
--
-- Partial on IS NOT NULL: outgoing plannings carry no receipt schedule, and neither branch ever probes a NULL,
-- so those rows would only inflate the index. Measured locally: 4550 planning rows, 2631
-- carrying a schedule and 1919 not, so the partial index skips about 42% of the table.

-- 2026-09-09T17:30:00.000Z
CREATE INDEX IF NOT EXISTS m_delivery_planning_receiptschedule
    ON M_Delivery_Planning (M_ReceiptSchedule_ID)
    WHERE M_ReceiptSchedule_ID IS NOT NULL
;
