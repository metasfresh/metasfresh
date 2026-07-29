-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/table/accounting_docs_to_repost_staging.sql
-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/table/product_costs_recreate_progress.sql
-- Scaffolding only: two brand-new tables in the de_metas_acct schema. The real queue table
-- "de_metas_acct".accounting_docs_to_repost is deliberately NOT touched -- it keeps its exact
-- current shape so the accounting-server poller is unaffected.

DROP TABLE IF EXISTS "de_metas_acct".accounting_docs_to_repost_staging
;

-- Holding area for the documents a cost-recompute run will hand to the accounting server.
-- Same payload columns as "de_metas_acct".accounting_docs_to_repost (identical types, so the
-- final release is a plain INSERT ... SELECT into the real queue), plus the ordering and
-- batching columns the run needs while it is still collecting:
--   dateacct, tablename_prio -> the release order (together with record_id, which the payload
--                               already carries), taken from accountable_docs_and_lines_v
--   batch_no                 -> which commit-batch of the run staged the row
-- seqno carries no DEFAULT here on purpose: the real queue's seqno is assigned explicitly at
-- release time (row_number() over the release order), never by consuming the queue's sequence
-- for rows that are still staged.
CREATE TABLE "de_metas_acct".accounting_docs_to_repost_staging
(
    seqno                   numeric(10),
    --
    tablename               varchar(255)                       NOT NULL,
    record_id               numeric(10)                        NOT NULL,
    ad_client_id            numeric(10)              DEFAULT 1000000 NOT NULL,
    force                   char(1)                  DEFAULT 'N'     NOT NULL,
    on_error_notify_user_id numeric(10),
    --
    selection_id            varchar(60),
    created                 time WITH TIME ZONE      DEFAULT NOW()   NOT NULL,
    description             varchar(2000),
    --
    dateacct                timestamp WITH TIME ZONE           NOT NULL,
    tablename_prio          numeric(10)                        NOT NULL,
    batch_no                numeric(10)                        NOT NULL
)
;

COMMENT ON TABLE "de_metas_acct".accounting_docs_to_repost_staging IS
    'Documents staged by a cost-recompute run; released into accounting_docs_to_repost in document-date order when the run is done'
;

CREATE INDEX accounting_docs_to_repost_staging_batch_no ON "de_metas_acct".accounting_docs_to_repost_staging (batch_no)
;

CREATE INDEX accounting_docs_to_repost_staging_dateacct_prio_record ON "de_metas_acct".accounting_docs_to_repost_staging (dateacct, tablename_prio, record_id)
;

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
