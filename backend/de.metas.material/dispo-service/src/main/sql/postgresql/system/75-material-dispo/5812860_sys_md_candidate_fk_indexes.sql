-- Add missing FK-supporting indexes for MD_Candidate.
--
-- Every detail table has a NO ACTION FK to md_candidate, and MaterialDispo deletes/retracts
-- candidates row-by-row -- so each parent-row delete fires a referential-integrity probe
-- "SELECT 1 FROM <detail> WHERE md_candidate_id = $1" per referencing table. Two detail tables
-- and the parent self-FK lack a usable (non-partial) index on that column, so the probe
-- seq-scans the whole referencing table per deleted row -- pathological on large instances
-- (both normal per-candidate retraction and bulk removals).
--
-- The sibling detail tables (dist_detail / demand_detail / prod_detail / qtydetails) already
-- carry this index; md_candidate_transaction_detail and md_candidate_purchase_detail were
-- missed. md_candidate_parent_id is covered only by a STOCK-partial index (for the ATP virtual
-- column), which the general RI/delete probe cannot use -- add a NOT-NULL partial that serves it
-- while staying small (the self-FK probe never matches NULL).
--
-- Plain CREATE INDEX (the migration runner applies scripts inside one transaction, so
-- CREATE INDEX CONCURRENTLY is not usable). IF NOT EXISTS keeps it a no-op on any instance that
-- already added the index manually.

CREATE INDEX IF NOT EXISTS md_candidate_transaction_detail_md_candidate_id
    ON md_candidate_transaction_detail (md_candidate_id);

CREATE INDEX IF NOT EXISTS md_candidate_purchase_detail_md_candidate_id
    ON md_candidate_purchase_detail (md_candidate_id);

-- sibling convention is <table>_<column> (= md_candidate_md_candidate_parent_id), but that
-- name is taken by the existing STOCK-partial ATP index; disambiguate with a _notnull suffix.
CREATE INDEX IF NOT EXISTS md_candidate_md_candidate_parent_id_notnull
    ON md_candidate (md_candidate_parent_id)
    WHERE md_candidate_parent_id IS NOT NULL;
