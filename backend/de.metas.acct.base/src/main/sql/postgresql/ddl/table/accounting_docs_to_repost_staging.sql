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
