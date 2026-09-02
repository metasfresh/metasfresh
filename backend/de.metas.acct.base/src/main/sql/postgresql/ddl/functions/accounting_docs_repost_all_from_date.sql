DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_repost_all_from_date(
    p_C_AcctSchema_ID numeric,
    p_StartDateAcct   timestamp WITH TIME ZONE
)
;


-- Reposts EVERY accountable, product-bearing, non-invoice document of an accounting schema from a given
-- accounting date onwards, WITHOUT recomputing anything. Returns the number of documents enqueued.
--
-- This is the counterpart of "de_metas_acct".product_costs_recreate_all_from_date, and the difference is the
-- whole point: the recompute driver DELETES the cost details in range so that reposting rebuilds them, while
-- this function deletes nothing at all. It exists for the situation where the cost details are already
-- correct and only the general ledger is not - the canonical case being a change of the accounting schema's
-- costing method. The amount a document posts is filtered to the cost element that is accountable under the
-- schema's current costing configuration (de.metas.costing.CostDetailCreateResultsList#getAmtAndQtyToPost),
-- so after such a change the existing Fact_Acct rows are valued by the OLD method and every document has to
-- be reposted to be valued by the new one. The cost details themselves must survive that untouched:
-- de.metas.costing.methods.CostingMethodHandlerTemplate#createOrUpdateCost returns the EXISTING cost details
-- when they exist (it only refreshes their DateAcct) and computes nothing, so reposting re-values the GL from
-- exactly the costs that are already there.
--
-- Consequently, and unlike the recompute driver:
--   * ordering is NOT a correctness contract here - nothing is computed, so nothing depends on the sequence.
--     Documents are still enqueued in accounting-date order because it costs nothing and keeps the GL rows
--     produced in a sensible order;
--   * there is no staging table, no progress table and no resume. There is nothing to hold documents back
--     for: the enqueue is a single INSERT in a single transaction, so the run either happened or it did not;
--   * it does not delete Fact_Acct rows either - the queue-driven repost does that itself. Verified:
--     de.metas.acct.api.impl.PostingService#postNow calls doc.post(force, /*repost*/ true) with that second
--     argument hardcoded, and org.compiere.acct.Doc#post does `if (repost) { deleteAcct(); }` before saving
--     the new facts.
--
-- REJECTED ALTERNATIVE: looping "de_metas_acct".fact_acct_unpost() over the documents. It is the sanctioned
-- way to enqueue a repost (see the rule deviation below), but it cannot do this job:
--   * it sets Processing='Y' on every document it touches and, at its default p_Force:='N', RAISEs when a
--     document is already Processing='Y' - so a single such document anywhere in the range aborts the loop,
--     and on a real installation that range holds hundreds of thousands of documents. Passing p_Force:='Y'
--     does not fix it: it only trades the abort for overwriting whatever process owns that lock;
--   * it DELETEs every Fact_Acct row of every document up front - work the repost redoes anyway (see above),
--     and it leaves the ledger empty for as long as the queue takes to drain;
--   * it enqueues one document per call as it goes, with the SeqNo left to the queue sequence, so there is no
--     way to reserve a contiguous, deliberately ordered block.
--
-- KNOWN, DELIBERATE RULE DEVIATION - documented here rather than hidden:
--   `docs/coding-rules/production-database-support.md` and `backend/de.metas.acct.base/CLAUDE.md` both state:
--   never INSERT directly into Accounting_Docs_To_Repost - always use the "de_metas_acct".fact_acct_unpost*
--   functions. The INSERT below breaks that rule knowingly, on exactly the same grounds as the recompute
--   driver's release step. The rule exists because fact_acct_unpost bundles three effects with the enqueue,
--   and here two of them are provably redundant and the third is done differently:
--     * deleting the stale Fact_Acct rows - redundant, the repost does it itself (PostingService#postNow ->
--       Doc#post -> deleteAcct(), see above);
--     * resetting Posted='N' so the document can be locked again - redundant, because
--       de.metas.acct.doc.SqlAcctDocLockService#lock only adds the `AND Posted='N'` condition when repost is
--       FALSE, so a document left at Posted='Y' still locks and reposts;
--     * checking that the period is open - NOT redundant, and therefore replaced by the explicit
--       assert_period_open sweep in (c) below, which is strictly stronger: it covers the whole range up front
--       instead of one document at a time.
--   The rule itself is deliberately NOT amended - that is not this function's call to make.
CREATE OR REPLACE FUNCTION "de_metas_acct".accounting_docs_repost_all_from_date(
    p_C_AcctSchema_ID numeric,
    p_StartDateAcct   timestamp WITH TIME ZONE
)
    RETURNS integer
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    -- Same advisory-lock class id as "de_metas_acct".product_costs_recreate_all_from_date, on purpose - see (a).
    c_AdvisoryLock_ClassId constant integer := 26253;
    --
    v_AD_Client_ID         numeric;
    v_documentCount        integer;
    v_seqNoLast            numeric;
    v_seqNoBase            numeric;
    v_period               record;
BEGIN
    IF (p_StartDateAcct IS NULL) THEN
        RAISE EXCEPTION 'p_StartDateAcct shall be set';
    END IF;

    SELECT cas.ad_client_id
    INTO v_AD_Client_ID
    FROM c_acctschema cas
    WHERE cas.c_acctschema_id = p_C_AcctSchema_ID;
    IF (v_AD_Client_ID IS NULL) THEN
        RAISE EXCEPTION 'C_AcctSchema_ID % not found', p_C_AcctSchema_ID;
    END IF;

    --
    --
    -- (a) NEVER WHILE A COST RECOMPUTE IS RUNNING
    --
    --
    -- Deliberately the SAME lock class and key as "de_metas_acct".product_costs_recreate_all_from_date, so the
    -- two exclude each other rather than merely each other's own kind. Overlapping them is precisely the
    -- damaging case: while a recompute run is in flight its documents are parked in the staging table exactly
    -- so that nothing reaches the poller until every product has been rewound. Documents this function pushes
    -- straight onto the queue in the meantime would be reposted against half-rewound costs, and the recompute
    -- would then reuse the wrong cost details it finds - silently, and for good.
    -- The lock is taken at SESSION level (like the driver's), not transaction level, because the two must be
    -- comparable: the driver commits per batch and therefore cannot hold a transaction-level lock at all.
    IF NOT pg_try_advisory_lock(c_AdvisoryLock_ClassId, p_C_AcctSchema_ID::integer) THEN
        RAISE EXCEPTION 'Another cost recompute or repost run is already in progress for C_AcctSchema_ID=%', p_C_AcctSchema_ID;
    END IF;

    -- Everything after the lock is wrapped, so that a failure releases it. Without this, the very failure this
    -- function is most likely to hit - assert_period_open refusing a closed period in (c) - would leave the
    -- session holding lock (26253, C_AcctSchema_ID) for as long as it lives, and the operator's next attempt
    -- FROM ANOTHER SESSION, or a cost recompute run started to investigate, would be refused with a message
    -- about a run that is not actually in progress. A plain function may do this; the recompute driver may not,
    -- because PL/pgSQL forbids transaction control inside a block that has an EXCEPTION handler.
    BEGIN
        --
        --
        -- (b) THE DOCUMENTS TO REPOST
        --
        --
        -- Same predicate as "de_metas_acct".product_costs_recreate_all_from_date's document set, so that this
        -- function reposts exactly what a recompute over the same range would have reposted - no more, no less.
        -- Invoices are excluded, so reposting never touches VAT.
        -- Materialised rather than read twice: (c) sweeps the periods and (d) enqueues the documents, and they
        -- must see the SAME set. Reading the view again in (d) would enqueue a document that posted in between
        -- without its period ever having been checked.
        --
        DROP TABLE IF EXISTS tmp_adr_docs;
        CREATE TEMPORARY TABLE tmp_adr_docs AS
        SELECT DISTINCT doc.tablename,
                        doc.record_id,
                        doc.tablename_prio,
                        doc.dateacct,
                        doc.docbasetype,
                        doc.ad_client_id,
                        doc.ad_org_id
        FROM "de_metas_acct".accountable_docs_and_lines_v doc
        WHERE doc.m_product_id IS NOT NULL
          AND doc.tablename <> 'C_Invoice'
          AND doc.dateacct >= p_StartDateAcct
          AND doc.ad_client_id = v_AD_Client_ID;

        --
        --
        -- (c) ALL PERIODS IN RANGE MUST BE OPEN - CHECKED BEFORE ANYTHING IS ENQUEUED
        --
        --
        -- Not a courtesy, and not optional for a repost: org.compiere.acct.Doc#post rejects an ALREADY-POSTED
        -- document whose period is closed with @PeriodClosed@ even when reposting, and every document this
        -- function enqueues is already posted. Without this sweep the run would look like a success - the rows
        -- are on the queue - and then fail document by document in the accounting server, one AD_Issue at a
        -- time, with the ledger left half re-valued.
        --
        FOR v_period IN (SELECT DISTINCT t.dateacct, t.docbasetype, t.ad_client_id, t.ad_org_id
                         FROM tmp_adr_docs t
                         ORDER BY t.dateacct, t.docbasetype, t.ad_client_id, t.ad_org_id)
            LOOP
                PERFORM "de_metas_acct".assert_period_open(
                        p_DateAcct := v_period.dateacct,
                        p_DocBaseType := v_period.docbasetype,
                        p_AD_Client_ID := v_period.ad_client_id,
                        p_AD_Org_ID := v_period.ad_org_id);
            END LOOP;

        --
        --
        -- (d) ENQUEUE, ONE ROW PER DOCUMENT, IN ACCOUNTING-DATE ORDER
        --
        --
        -- One row per DOCUMENT, not per line: accountable_docs_and_lines_v yields a row per product line, and a
        -- document enqueued twice is reposted twice.
        --
        DROP TABLE IF EXISTS tmp_adr_repost_docs;
        CREATE TEMPORARY TABLE tmp_adr_repost_docs AS
        SELECT d.tablename,
               d.record_id,
               MIN(d.tablename_prio) AS tablename_prio,
               MIN(d.dateacct)       AS dateacct,
               MIN(d.ad_client_id)   AS ad_client_id
        FROM tmp_adr_docs d
        GROUP BY d.tablename, d.record_id;

        SELECT COUNT(1) INTO v_documentCount FROM tmp_adr_repost_docs;

        --
        -- SeqNo is assigned EXPLICITLY via row_number() over the enqueue order, out of a block reserved by
        -- advancing the queue's own sequence in a single statement. It is never left to the column's nextval
        -- default: the order in which INSERT ... SELECT evaluates rows - and therefore the order in which it
        -- consumes a sequence - is not guaranteed by an ORDER BY in the SELECT. Reserving the block first is
        -- what keeps these rows from colliding with whatever another producer enqueues in the meantime.
        --
        IF (v_documentCount > 0) THEN
            SELECT setval('"de_metas_acct".accounting_docs_to_repost_seqno',
                          nextval('"de_metas_acct".accounting_docs_to_repost_seqno') + v_documentCount - 1)
            INTO v_seqNoLast;
            v_seqNoBase := v_seqNoLast - v_documentCount;

            INSERT INTO "de_metas_acct".accounting_docs_to_repost
            (seqno, tablename, record_id, ad_client_id, description)
            SELECT v_seqNoBase + ROW_NUMBER() OVER (ORDER BY d.dateacct, d.tablename_prio, d.record_id),
                   d.tablename,
                   d.record_id,
                   d.ad_client_id,
                   'accounting_docs_repost_all_from_date'
            FROM tmp_adr_repost_docs d;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            PERFORM pg_advisory_unlock(c_AdvisoryLock_ClassId, p_C_AcctSchema_ID::integer);
            RAISE;
    END;

    PERFORM pg_advisory_unlock(c_AdvisoryLock_ClassId, p_C_AcctSchema_ID::integer);

    RAISE NOTICE 'accounting_docs_repost_all_from_date: C_AcctSchema_ID=%, StartDateAcct=% => % document(s) enqueued for reposting',
        p_C_AcctSchema_ID, p_StartDateAcct, v_documentCount;

    RETURN v_documentCount;
END;
$BODY$
;

COMMENT ON FUNCTION "de_metas_acct".accounting_docs_repost_all_from_date(numeric, timestamp WITH TIME ZONE) IS
    'Enqueues every accountable non-invoice document of an accounting schema from a date onwards for reposting, in accounting-date order, without recomputing or deleting any cost detail'
;
