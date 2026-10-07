-- gh#25618 — Bestand pro Woche / Stock per week
-- Partial indexes on MD_Candidate backing the per-week view's shipments / receipts subqueries.
-- After dropping the row_number() PK (migration 5806110), the WHERE M_Product_ID = ... predicate
-- is pushed down to these MD_Candidate scans; these partial indexes let the planner satisfy the
-- DEMAND/SHIPMENT and SUPPLY/PURCHASE per-week sums with an Index Scan instead of a Seq Scan.
--
-- The predicate columns are all IMMUTABLE (IsActive, MD_Candidate_Type, MD_Candidate_BusinessCase),
-- so they are valid in a partial-index WHERE clause. The view also filters on
-- MD_Candidate_Status IS DISTINCT FROM 'simulated', but that status filter is intentionally NOT
-- part of the index predicate (status is mutable and the negated comparison would not be usable
-- as an index predicate anyway) — it is applied as a residual filter on the index-selected rows.

CREATE INDEX IF NOT EXISTS md_candidate_perweek_demand_idx
    ON MD_Candidate (M_Product_ID, M_Warehouse_ID, DateProjected)
    WHERE IsActive = 'Y'
      AND MD_Candidate_Type = 'DEMAND'
      AND MD_Candidate_BusinessCase = 'SHIPMENT';

CREATE INDEX IF NOT EXISTS md_candidate_perweek_supply_idx
    ON MD_Candidate (M_Product_ID, M_Warehouse_ID, DateProjected)
    WHERE IsActive = 'Y'
      AND MD_Candidate_Type = 'SUPPLY'
      AND MD_Candidate_BusinessCase = 'PURCHASE';
