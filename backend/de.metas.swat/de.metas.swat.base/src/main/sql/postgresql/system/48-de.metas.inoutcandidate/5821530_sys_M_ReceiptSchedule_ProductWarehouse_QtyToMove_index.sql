-- Support an existence probe run once per row selection: filters M_ReceiptSchedule on
-- M_Product_ID, M_Warehouse_ID, IsActive and a non-zero QtyToMove. Without a matching index the
-- table is sequentially scanned on every probe -- structural, not a statistics artifact.
--
-- QtyToMove <> 0 is included in the partial predicate: Postgres's predicate-implication prover
-- matches the probe's own qty check against it and drops that check from the plan's Filter, so
-- rows with a zero quantity are never stored in the index at all.
--
-- Plain CREATE INDEX (the migration runner applies scripts inside one transaction, so
-- CREATE INDEX CONCURRENTLY is not usable). IF NOT EXISTS keeps it a no-op on any instance that
-- already carries the index.

CREATE INDEX IF NOT EXISTS m_receiptschedule_m_product_id_m_warehouse_id
    ON m_receiptschedule (M_Product_ID, M_Warehouse_ID)
    WHERE IsActive = 'Y' AND QtyToMove <> 0;

COMMENT ON INDEX m_receiptschedule_m_product_id_m_warehouse_id IS
    'F19011 - Material Cockpit v2: backs the precondition probe of AD_Process QtyDemand_QtySupply_V_to_ReceiptSchedule, which asks on every cockpit row selection whether any receipt schedule matches the row.';
