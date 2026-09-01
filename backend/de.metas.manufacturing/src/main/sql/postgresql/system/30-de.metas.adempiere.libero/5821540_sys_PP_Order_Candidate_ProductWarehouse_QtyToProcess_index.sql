-- Support an existence probe run once per row selection: filters PP_Order_Candidate on
-- M_Product_ID, M_Warehouse_ID, IsActive and a non-zero QtyToProcess. Without a matching index
-- the table is sequentially scanned on every probe -- structural, not a statistics artifact
-- (today it has only its primary key).
--
-- QtyToProcess <> 0 is included in the partial predicate: Postgres's predicate-implication
-- prover matches the probe's own qty check against it and drops that check from the plan's
-- Filter, so rows with a zero quantity are never stored in the index at all.
--
-- Plain CREATE INDEX (the migration runner applies scripts inside one transaction, so
-- CREATE INDEX CONCURRENTLY is not usable). IF NOT EXISTS keeps it a no-op on any instance that
-- already carries the index.

CREATE INDEX IF NOT EXISTS pp_order_candidate_m_product_id_m_warehouse_id
    ON pp_order_candidate (M_Product_ID, M_Warehouse_ID)
    WHERE IsActive = 'Y' AND QtyToProcess <> 0;
