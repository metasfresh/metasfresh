DROP TABLE IF EXISTS "de_metas_acct".product_costs_recreate_progress
;

-- One row per commit-batch of a cost-recompute run, so an aborted run can be resumed without
-- redoing the batches that already finished.
--   products    -> the batch's M_Product_IDs; numeric[] is the type the recompute functions in
--                  this module already pass product id sets around as (product_costs_recreate,
--                  product_costs_recreate_from_date)
--   finished_at -> NULL while the batch is still running
--   error       -> text, not varchar(n): it holds SQLERRM plus context, and a length limit would
--                  make the very INSERT that records a failure fail on an over-long message
--
-- The four run-identity columns record the parameters the batch was produced under, because
-- batch_no alone is NOT a stable name for a set of products: product_costs_recreate_all_from_date
-- recomputes it on every call purely from the caller's own p_ProductsPerCommit. Without them a
-- p_Resume='Y' call that passes a different products-per-commit - or one that runs for a different
-- accounting schema / cost element / start date and meets the DONE rows a previously COMPLETED run
-- left behind - would read "batch 3 is DONE" and skip a DIFFERENT set of products. Those products
-- would never be rewound, never staged, and the run would report success. The resume compares these
-- columns against the current call's parameters and refuses on any difference.
--   Nullable on purpose: rows written before these columns existed carry no identity at all, and a
--   resume has to refuse those too rather than trust them.
CREATE TABLE "de_metas_acct".product_costs_recreate_progress
(
    batch_no            numeric(10)                            NOT NULL,
    status              varchar(20)                            NOT NULL,
    products            numeric[]                              NOT NULL,
    started_at          timestamp WITH TIME ZONE DEFAULT NOW() NOT NULL,
    finished_at         timestamp WITH TIME ZONE,
    error               text,
    c_acctschema_id     numeric(10),
    m_costelement_id    numeric(10),
    startdateacct       timestamp WITH TIME ZONE,
    products_per_commit numeric(10)
)
;

COMMENT ON TABLE "de_metas_acct".product_costs_recreate_progress IS
    'Per-batch progress of a cost-recompute run, so an aborted run can resume instead of restarting'
;
