-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/table/product_costs_recreate_progress.sql

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
CREATE TABLE "de_metas_acct".product_costs_recreate_progress
(
    batch_no    numeric(10)                            NOT NULL,
    status      varchar(20)                            NOT NULL,
    products    numeric[]                              NOT NULL,
    started_at  timestamp WITH TIME ZONE DEFAULT NOW() NOT NULL,
    finished_at timestamp WITH TIME ZONE,
    error       text
)
;

COMMENT ON TABLE "de_metas_acct".product_costs_recreate_progress IS
    'Per-batch progress of a cost-recompute run, so an aborted run can resume instead of restarting'
;
