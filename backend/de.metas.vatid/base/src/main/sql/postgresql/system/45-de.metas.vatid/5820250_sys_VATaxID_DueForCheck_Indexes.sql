-- VAT-ID online check, nightly sweep: supporting indexes for the "records due for a VAT-ID check"
-- query on C_BPartner and C_BPartner_Location. One index per table.
--
-- That query selects the active, current-client records of one organisation that carry a VAT-ID,
-- narrowed by a staleness disjunction, and orders them by VATaxIDLastAttemptedAt ascending with
-- never-attempted records first, then by the record's own id. Before these indexes existed, no index
-- covered any part of it and every nightly run sequentially scanned both tables and sorted the result.
--
-- Column order: the ordering columns lead, so the index can serve the ORDER BY directly and the run's
-- early exit (it stops once its per-run budget is spent) does not have to sort the whole candidate set
-- first. AD_Org_ID is last on purpose -- it is low-cardinality and discriminates little.
--
-- NULLS FIRST is spelled out because the query orders never-attempted records first, while Postgres's
-- default for ASC is NULLS LAST; an index with the default ordering would not match the ORDER BY.
--
-- The partial predicate is VATaxID IS NOT NULL and nothing more, matching the query's own VAT-ID
-- filter exactly. A predicate the query's WHERE clause does not imply would make the index unusable
-- for it.

CREATE INDEX IF NOT EXISTS C_BPartner_VATaxID_DueForCheck_idx
    ON C_BPartner (VATaxIDLastAttemptedAt ASC NULLS FIRST, C_BPartner_ID, AD_Org_ID)
    WHERE VATaxID IS NOT NULL;

CREATE INDEX IF NOT EXISTS C_BPartner_Location_VATaxID_DueForCheck_idx
    ON C_BPartner_Location (VATaxIDLastAttemptedAt ASC NULLS FIRST, C_BPartner_Location_ID, AD_Org_ID)
    WHERE VATaxID IS NOT NULL;
