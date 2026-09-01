-- Support an existence probe run once per row selection: filters M_ShipmentSchedule on
-- M_Product_ID, IsActive and a non-zero QtyReserved (plus AD_Org_ID and a warehouse OR-clause
-- kept in the Filter -- see below). Without a matching index the table is sequentially scanned
-- on every probe -- structural, not a statistics artifact; the table's existing indexes each
-- fail to seek this shape for a specific reason (low-selectivity warehouse column, leading on
-- the raw AttributeSetInstance id instead of the derived storage-attributes key, a Processed
-- predicate the probe does not filter, or a non-leading product column).
--
-- Deliberately ONE column (M_Product_ID) -- the warehouse predicate is an OR over
-- M_Warehouse_ID and M_Warehouse_Override_ID, and an OR cannot become an index condition on a
-- single composite index, so it always stays in the Filter; a second column buys nothing.
--
-- QtyReserved <> 0 is included in the partial predicate: Postgres's predicate-implication
-- prover matches the probe's own qty check against it and drops that check from the plan's
-- Filter, so rows with a zero quantity are never stored in the index at all.
--
-- Plain CREATE INDEX (the migration runner applies scripts inside one transaction, so
-- CREATE INDEX CONCURRENTLY is not usable). IF NOT EXISTS keeps it a no-op on any instance that
-- already carries the index.

CREATE INDEX IF NOT EXISTS m_shipmentschedule_m_product_id
    ON m_shipmentschedule (M_Product_ID)
    WHERE IsActive = 'Y' AND QtyReserved <> 0;
