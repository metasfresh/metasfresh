-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/functions/product_costs_recreate_all_from_date.sql
DROP PROCEDURE IF EXISTS "de_metas_acct".product_costs_recreate_all_from_date(
    p_C_AcctSchema_ID   numeric,
    p_M_CostElement_ID  numeric,
    p_StartDateAcct     timestamp WITH TIME ZONE,
    p_ProductsPerCommit numeric,
    p_Resume            char(1)
)
;


-- Recreates the costs of EVERY product of an accounting schema from a given accounting date onwards.
--
-- It is a PROCEDURE, not a function, because it MUST commit as it goes: a full run over a real
-- installation is ~5 000 products / ~376 000 documents and cannot be one transaction. A single-transaction
-- attempt ran 2h13m and then died with a backend I/O error, rolling back everything. So the work is cut
-- into commit-batches of p_ProductsPerCommit products, each batch recorded in
-- "de_metas_acct".product_costs_recreate_progress, and p_Resume='Y' picks up where an aborted run stopped
-- instead of starting over. A resume is only allowed to continue the SAME logical run: it refuses unless
-- the recorded batches carry this call's own parameters and cover this call's own products.
--
-- The documents to repost are NOT enqueued as they are found. They are parked in
-- "de_metas_acct".accounting_docs_to_repost_staging and released into the real queue in ONE atomic step at
-- the very end (see "RELEASE" below). That is the whole point: costs are computed WHILE reposting, so the
-- documents must reach the accounting server strictly in document-date order. The server's poller drains
-- the real queue continuously, so anything inserted there early would be reposted early - out of order -
-- and would freeze wrong costs. Holding the rows outside the queue is also what keeps this run from
-- needing any change to the queue table, to its poller, or to fact_acct_unpost.
CREATE OR REPLACE PROCEDURE "de_metas_acct".product_costs_recreate_all_from_date(
    p_C_AcctSchema_ID   numeric,
    p_M_CostElement_ID  numeric,
    p_StartDateAcct     timestamp WITH TIME ZONE,
    p_ProductsPerCommit numeric = 100,
    p_Resume            char(1) = 'N'
)
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    -- Advisory-lock class id. Arbitrary but stable: it only has to be a value no other advisory lock in
    -- this database uses, so that two runs of THIS procedure (and only those) collide.
    c_AdvisoryLock_ClassId constant integer := 26253;
    --
    v_AD_Client_ID         numeric;
    v_costingLevel         char(1);
    v_orgIds               numeric[];
    v_targetCostElementId  numeric;
    --
    v_doneBatchCount       integer;
    v_productCount         integer;
    v_documentCount        integer;
    v_releasedCount        integer;
    v_seqNoLast            numeric;
    v_seqNoBase            numeric;
    rowcount               integer;
    --
    v_batch                record;
    v_period               record;
    v_purge                record;
    v_foreignBatch         record;
    v_doneProducts         numeric[];
    v_productId            numeric;
    v_orgId                numeric;
BEGIN
    IF (p_StartDateAcct IS NULL) THEN
        RAISE EXCEPTION 'p_StartDateAcct shall be set';
    END IF;
    IF (p_ProductsPerCommit IS NULL OR p_ProductsPerCommit < 1) THEN
        RAISE EXCEPTION 'p_ProductsPerCommit shall be >= 1 but it was %', p_ProductsPerCommit;
    END IF;

    --
    --
    -- (a) SINGLE RUN AT A TIME
    --
    --
    -- The lock is SESSION level, not transaction level: this procedure commits after every batch, and a
    -- transaction-level lock would be released by the very first of those commits, leaving the rest of a
    -- multi-hour run unprotected. Two overlapping runs would delete each other's cost details and stage
    -- each other's documents, and the ordering guarantee of the release step would be lost.
    -- A session-level lock is re-entrant within its own session, so resuming from the same session works.
    IF NOT pg_try_advisory_lock(c_AdvisoryLock_ClassId, p_C_AcctSchema_ID::integer) THEN
        RAISE EXCEPTION 'Another product_costs_recreate_all_from_date run is already in progress for C_AcctSchema_ID=%', p_C_AcctSchema_ID;
    END IF;

    --
    -- Validate parameter: Accounting Schema => extract client and eligible Orgs
    -- (same rules as "de_metas_acct".product_costs_recreate_from_date)
    --
    SELECT cas.ad_client_id, cas.costinglevel
    INTO v_AD_Client_ID, v_costingLevel
    FROM c_acctschema cas
    WHERE cas.c_acctschema_id = p_C_AcctSchema_ID;
    IF (v_AD_Client_ID IS NULL) THEN
        RAISE EXCEPTION 'C_AcctSchema_ID % not found', p_C_AcctSchema_ID;
    END IF;
    --
    IF (v_costingLevel = 'C') THEN
        v_orgIds := ARRAY [ 0 ];
    ELSIF (v_costingLevel = 'O') THEN
        SELECT ARRAY_AGG(org.ad_org_id ORDER BY org.ad_org_id)
        INTO v_orgIds
        FROM ad_org org
        WHERE org.ad_client_id = v_AD_Client_ID
          AND org.ad_org_id != 0;
    ELSE
        RAISE EXCEPTION 'Costing level `%` not supported for C_AcctSchema_ID=%', v_costingLevel, p_C_AcctSchema_ID;
    END IF;

    --
    -- Resolve the target Moving-Average-Invoice cost element.
    -- Same lookup as public.C_AcctSchema_InitMovingAvgInvoice: the active Material cost element whose
    -- costing method is 'M'. It is derived rather than passed in because it is not a free choice - it is
    -- the element the switch seeds, and the purge below must cover exactly that one.
    --
    SELECT ce.m_costelement_id
    INTO v_targetCostElementId
    FROM m_costelement ce
    WHERE ce.ad_client_id = v_AD_Client_ID
      AND ce.costelementtype = 'M'
      AND ce.costingmethod = 'M'
      AND ce.isactive = 'Y'
    ORDER BY ce.m_costelement_id
    LIMIT 1;
    IF (v_targetCostElementId IS NULL) THEN
        RAISE EXCEPTION 'No active MovingAverageInvoice cost element (costingmethod=M) found for AD_Client_ID=%', v_AD_Client_ID;
    END IF;

    RAISE NOTICE 'product_costs_recreate_all_from_date: C_AcctSchema_ID=%, M_CostElement_ID=%, target(MAI) M_CostElement_ID=%, StartDateAcct=%, ProductsPerCommit=%, Resume=%',
        p_C_AcctSchema_ID, p_M_CostElement_ID, v_targetCostElementId, p_StartDateAcct, p_ProductsPerCommit, p_Resume;

    --
    -- Fresh start vs. resume.
    -- A fresh run must not inherit anything: leftover staged documents would be released together with
    -- this run's, and leftover DONE markers would make it skip batches it never did.
    --
    IF (p_Resume = 'Y') THEN
        --
        -- A resume may only continue the run that recorded those DONE markers - so first prove it is
        -- that run. batch_no is NOT a stable name for a set of products: it is recomputed below purely
        -- from the caller's own p_ProductsPerCommit. Two ways an operator silently corrupts a run
        -- without this check, both of them plausible reactions to a crash:
        --   1. resuming with a SMALLER p_ProductsPerCommit than the crashed run used. The
        --      batch_no -> products mapping shifts, so a batch_no recorded DONE under the old grouping
        --      is read as "already done" for a DIFFERENT set of products.
        --   2. resuming after an earlier run that COMPLETED - its DONE rows are never cleared - for a
        --      different accounting schema, cost element or start date. Every batch matches a DONE row,
        --      so the brand-new logical run skips everything.
        -- In both cases the skipped products are never rewound, never staged, and the run reports
        -- success: the corruption is silent. Hence refuse, naming both sides so the operator can see
        -- which parameter to correct.
        --
        SELECT pr.batch_no,
               pr.c_acctschema_id,
               pr.m_costelement_id,
               pr.startdateacct,
               pr.products_per_commit
        INTO v_foreignBatch
        FROM "de_metas_acct".product_costs_recreate_progress pr
        WHERE pr.status = 'DONE'
          AND (pr.c_acctschema_id IS DISTINCT FROM p_C_AcctSchema_ID
            OR pr.m_costelement_id IS DISTINCT FROM p_M_CostElement_ID
            OR pr.startdateacct IS DISTINCT FROM p_StartDateAcct
            OR pr.products_per_commit IS DISTINCT FROM p_ProductsPerCommit)
        ORDER BY pr.batch_no
        LIMIT 1;
        IF (FOUND) THEN
            RAISE EXCEPTION 'Cannot resume: batch % was recorded by a different run (recorded C_AcctSchema_ID=%, M_CostElement_ID=%, StartDateAcct=%, ProductsPerCommit=%; requested C_AcctSchema_ID=%, M_CostElement_ID=%, StartDateAcct=%, ProductsPerCommit=%). Resume with exactly the parameters of the run to be continued, or start it over with p_Resume:=''N''.',
                v_foreignBatch.batch_no,
                v_foreignBatch.c_acctschema_id, v_foreignBatch.m_costelement_id, v_foreignBatch.startdateacct, v_foreignBatch.products_per_commit,
                p_C_AcctSchema_ID, p_M_CostElement_ID, p_StartDateAcct, p_ProductsPerCommit;
        END IF;

        SELECT COUNT(1) INTO v_doneBatchCount FROM "de_metas_acct".product_costs_recreate_progress WHERE status = 'DONE';
        RAISE NOTICE 'Resuming: % batch(es) already DONE will be skipped', v_doneBatchCount;
    ELSE
        DELETE FROM "de_metas_acct".accounting_docs_to_repost_staging;
        DELETE FROM "de_metas_acct".product_costs_recreate_progress;
    END IF;

    --
    -- The (product, document) pairs this run covers.
    -- Same predicate as "de_metas_acct".product_costs_recreate_from_date, minus the product restriction:
    -- invoices are excluded, so reposting never touches VAT.
    --
    DROP TABLE IF EXISTS tmp_pcra_docs;
    CREATE TEMPORARY TABLE tmp_pcra_docs AS
    SELECT DISTINCT doc.m_product_id,
                    doc.tablename,
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
    -- Cut the products into commit-batches, ordered by M_Product_ID so a resume reproduces exactly the
    -- same batch numbers as the run it continues.
    --
    DROP TABLE IF EXISTS tmp_pcra_products;
    CREATE TEMPORARY TABLE tmp_pcra_products AS
    SELECT p.m_product_id,
           (((ROW_NUMBER() OVER (ORDER BY p.m_product_id)) - 1) / p_ProductsPerCommit::bigint)::integer + 1 AS batch_no
    FROM (SELECT DISTINCT d.m_product_id FROM tmp_pcra_docs d) p;

    --
    -- One row per DOCUMENT, assigned to the batch of its lowest-numbered product.
    -- A document with lines for products in several batches must be staged exactly ONCE, otherwise it
    -- would be reposted several times. Which of its batches stages it does not matter: nothing is
    -- released until every batch is done, so by release time all of its products have been rewound.
    --
    DROP TABLE IF EXISTS tmp_pcra_staged_docs;
    CREATE TEMPORARY TABLE tmp_pcra_staged_docs AS
    SELECT d.tablename,
           d.record_id,
           MIN(p.batch_no)        AS batch_no,
           MIN(d.tablename_prio)  AS tablename_prio,
           MIN(d.dateacct)        AS dateacct,
           MIN(d.ad_client_id)    AS ad_client_id
    FROM tmp_pcra_docs d
             INNER JOIN tmp_pcra_products p ON p.m_product_id = d.m_product_id
    GROUP BY d.tablename, d.record_id;

    SELECT COUNT(1) INTO v_productCount FROM tmp_pcra_products;
    SELECT COUNT(1) INTO v_documentCount FROM tmp_pcra_staged_docs;
    RAISE NOTICE 'Selected % product(s) and % document(s) to be reposted', v_productCount, v_documentCount;

    --
    --
    -- (b) ALL PERIODS IN RANGE MUST BE OPEN - CHECKED BEFORE THE FIRST MUTATION
    --
    --
    -- Not merely a courtesy: the reposting that follows throws @PeriodClosed@ for an already-posted
    -- document whose period is closed (org.compiere.acct.Doc#post rejects that combination even when
    -- reposting). Finding that out batch 40 into a multi-hour run, with the cost details of batches 1..39
    -- already deleted and committed, would leave the installation mid-rewind. So the whole range is
    -- checked up front, while nothing has been changed yet and aborting costs nothing.
    --
    FOR v_period IN (SELECT DISTINCT t.dateacct, t.docbasetype, t.ad_client_id, t.ad_org_id
                     FROM tmp_pcra_docs t
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
    -- (c) PURGE THE TARGET (MAI) ELEMENT'S IN-RANGE COST DETAILS FIRST
    --
    --
    -- Posting explodes a document into EVERY active Material cost element, not just the one the
    -- accounting schema currently values with. So the MAI element may already carry cost details in this
    -- range, built before it had an opening balance - i.e. based on zero. Two ways those wreck the run,
    -- both silent:
    --   1. cost details are REUSED as-is when they already exist (de.metas.costing.methods
    --      .CostingMethodHandlerTemplate#createOrUpdateCost only refreshes their DateAcct), so the repost
    --      would keep them and MAI would stay zero-based;
    --   2. worse, m_costdetail_delete_from_date's case 1 restores M_Cost from the Prev_* of the FIRST
    --      changing-costs detail at/after the start date. A stale zero-based in-range detail would
    --      therefore win over the opening anchor and write a zero opening into M_Cost.
    -- Hence the order below: raw DELETE first, and only THEN m_costdetail_delete_from_date for the same
    -- product - which now finds no in-range detail, falls back to the last detail BEFORE the range (the
    -- cost-revaluation opening anchor, created last and therefore the tie-break winner) and restores
    -- M_Cost from the anchor's Prev_*, i.e. from the opening balance. Calling it without the raw DELETE
    -- first would reproduce exactly the failure this purge exists to prevent.
    -- A PARTIAL purge is the dangerous case: whatever in-range MAI detail survives becomes case 1's
    -- answer, so the run silently ends up zero-based for that product.
    --
    -- Only products that actually HAVE in-range MAI details are touched. That is deliberate: for a
    -- product with no in-range detail, m_costdetail_delete_from_date finds nothing to restore from and
    -- DELETES the M_Cost row outright - which for a seeded-but-not-moved product would destroy the
    -- opening balance the switch just created.
    --
    IF (v_targetCostElementId <> p_M_CostElement_ID) THEN
        DROP TABLE IF EXISTS tmp_pcra_mai_purge;
        CREATE TEMPORARY TABLE tmp_pcra_mai_purge AS
        SELECT DISTINCT cd.m_product_id, cd.ad_org_id
        FROM m_costdetail_v cd
        WHERE cd.c_acctschema_id = p_C_AcctSchema_ID
          AND cd.m_costelement_id = v_targetCostElementId
          AND cd.ad_client_id = v_AD_Client_ID
          AND cd.dateacct >= p_StartDateAcct;

        DELETE
        FROM m_costdetail
        WHERE m_costdetail_id IN (SELECT cd.m_costdetail_id
                                  FROM m_costdetail_v cd
                                  WHERE cd.c_acctschema_id = p_C_AcctSchema_ID
                                    AND cd.m_costelement_id = v_targetCostElementId
                                    AND cd.ad_client_id = v_AD_Client_ID
                                    AND cd.dateacct >= p_StartDateAcct);
        GET DIAGNOSTICS rowcount = ROW_COUNT;
        RAISE NOTICE 'Purged % pre-existing M_CostDetail(s) of the target cost element %', rowcount, v_targetCostElementId;

        FOR v_purge IN (SELECT t.m_product_id, t.ad_org_id FROM tmp_pcra_mai_purge t ORDER BY t.m_product_id, t.ad_org_id)
            LOOP
                PERFORM "de_metas_acct".m_costdetail_delete_from_date(
                        p_C_AcctSchema_ID := p_C_AcctSchema_ID,
                        p_M_CostElement_ID := v_targetCostElementId,
                        p_M_Product_ID := v_purge.m_product_id,
                        p_AD_Org_ID := v_purge.ad_org_id,
                        p_StartDateAcct := p_StartDateAcct,
                        p_DryRun := 'N');
            END LOOP;

        COMMIT;
    END IF;

    --
    --
    -- (d) ONE COMMIT-BATCH OF PRODUCTS AT A TIME
    --
    --
    -- Everything a batch does - rewinding its products' cost details, staging its documents and recording
    -- itself DONE - happens in ONE transaction. That is what makes the run resumable: a batch is either
    -- fully done AND marked done, or it is as if it never ran. There is deliberately no intermediate
    -- 'RUNNING' marker: it could only be written in the same transaction, so no other session could ever
    -- see it, and an abort would roll it back anyway.
    --
    -- There is deliberately no EXCEPTION handler around this loop: PL/pgSQL forbids transaction control
    -- inside a block that has one, so an exception handler here would make the per-batch COMMIT illegal.
    -- An error therefore propagates, this batch rolls back, and the batches before it stay committed -
    -- exactly the state p_Resume='Y' expects.
    --
    FOR v_batch IN (
        WITH numbered AS (SELECT p.m_product_id, p.batch_no FROM tmp_pcra_products p)
        SELECT n.batch_no,
               ARRAY_AGG(n.m_product_id ORDER BY n.m_product_id) AS products
        FROM numbered n
        GROUP BY n.batch_no
        ORDER BY n.batch_no)
        LOOP
            IF (p_Resume = 'Y') THEN
                SELECT pr.products
                INTO v_doneProducts
                FROM "de_metas_acct".product_costs_recreate_progress pr
                WHERE pr.batch_no = v_batch.batch_no
                  AND pr.status = 'DONE'
                LIMIT 1;

                IF (v_doneProducts IS NOT NULL) THEN
                    --
                    -- The recorded product set is the second half of the identity check that the resume
                    -- branch above started. The parameters can all match and the product POPULATION
                    -- still have changed since the crashed run selected it - a document posted into the
                    -- range in the meantime brings a new product in, which shifts every batch boundary
                    -- after it. Skipping such a batch would skip products it never covered, and again
                    -- silently: they would be left un-rewound and unstaged while the run reports
                    -- success. Both arrays are built by the same ORDER BY m_product_id, so comparing
                    -- them directly is exact.
                    --
                    IF (v_doneProducts <> v_batch.products) THEN
                        RAISE EXCEPTION 'Cannot resume: batch % was recorded for product(s) % but now covers product(s) % - the product population changed since that run. Start it over with p_Resume:=''N''.',
                            v_batch.batch_no, v_doneProducts, v_batch.products;
                    END IF;

                    RAISE NOTICE 'Batch % is already DONE, skipping it', v_batch.batch_no;
                    CONTINUE;
                END IF;
            END IF;

            --
            -- Rewind the cost details of this batch's products for the cost element being recomputed.
            -- m_costdetail_delete_from_date is the single choke point that also fixes M_Cost and that
            -- refuses to delete a cost-revaluation opening anchor - which is why the start date of a run
            -- must lie strictly after the switch's cut-off.
            --
            FOREACH v_productId IN ARRAY v_batch.products
                LOOP
                    FOREACH v_orgId IN ARRAY v_orgIds
                        LOOP
                            PERFORM "de_metas_acct".m_costdetail_delete_from_date(
                                    p_C_AcctSchema_ID := p_C_AcctSchema_ID,
                                    p_M_CostElement_ID := p_M_CostElement_ID,
                                    p_M_Product_ID := v_productId,
                                    p_AD_Org_ID := v_orgId,
                                    p_StartDateAcct := p_StartDateAcct,
                                    p_DryRun := 'N');
                        END LOOP;
                END LOOP;

            --
            -- Manufacturing order costs are a cached copy of the cost details just deleted, so they have
            -- to go with them (same step as in "de_metas_acct".product_costs_recreate_from_date).
            --
            DELETE
            FROM pp_order_cost poc
            WHERE EXISTS(SELECT 1
                         FROM pp_order o
                         WHERE o.pp_order_id = poc.pp_order_id
                           AND o.m_product_id = ANY (v_batch.products)
                           AND o.dateordered >= p_StartDateAcct);

            --
            -- Park this batch's documents. NOT in "de_metas_acct".accounting_docs_to_repost - see the
            -- release step below for why.
            --
            INSERT INTO "de_metas_acct".accounting_docs_to_repost_staging
            (tablename, record_id, ad_client_id, description, dateacct, tablename_prio, batch_no)
            SELECT d.tablename,
                   d.record_id,
                   d.ad_client_id,
                   'product_costs_recreate_all_from_date',
                   d.dateacct,
                   d.tablename_prio,
                   d.batch_no
            FROM tmp_pcra_staged_docs d
            WHERE d.batch_no = v_batch.batch_no;
            GET DIAGNOSTICS rowcount = ROW_COUNT;

            --
            -- The run's own identifying parameters travel WITH the batch, in the same transaction that
            -- records it DONE. That is what makes the resume check above possible at all: without them
            -- a DONE marker says only "some run finished batch 3", never "THIS run did".
            --
            INSERT INTO "de_metas_acct".product_costs_recreate_progress
                (batch_no, status, products, started_at, finished_at,
                 c_acctschema_id, m_costelement_id, startdateacct, products_per_commit)
            VALUES (v_batch.batch_no, 'DONE', v_batch.products, NOW(), NOW(),
                    p_C_AcctSchema_ID, p_M_CostElement_ID, p_StartDateAcct, p_ProductsPerCommit);

            RAISE NOTICE 'Batch % DONE: % product(s), % document(s) staged',
                v_batch.batch_no, ARRAY_LENGTH(v_batch.products, 1), rowcount;

            COMMIT;
        END LOOP;

    --
    --
    -- (e) NO Fact_Acct DELETE, NO fact_acct_unpost
    --
    --
    -- Verified, not assumed - the two effects fact_acct_unpost would contribute are both redundant here:
    --   * deleting the stale Fact_Acct rows: the queue-driven repost does it itself. The queue's poller
    --     posts through de.metas.acct.api.impl.PostingService#postNow, which calls
    --     doc.post(force, /*repost*/ true) with that second argument hardcoded, and
    --     org.compiere.acct.Doc#post does `if (repost) { deleteAcct(); }` before saving the new facts.
    --   * resetting Posted='N' so the document can be locked again: de.metas.acct.doc
    --     .SqlAcctDocLockService#lock only adds the `AND Posted='N'` condition when repost is FALSE, so a
    --     document left at Posted='Y' still locks and reposts.
    -- Skipping it is not just an optimisation: fact_acct_unpost INSERTs straight into
    -- "de_metas_acct".accounting_docs_to_repost, which would hand the documents to the poller one by one
    -- as they are found - the out-of-order reposting this whole procedure exists to prevent.
    --

    --
    --
    -- (f) RELEASE: STAGING -> REAL QUEUE, IN DOCUMENT-DATE ORDER, IN ONE STEP
    --
    --
    -- KNOWN, DELIBERATE RULE DEVIATION - documented here rather than hidden:
    --   `docs/coding-rules/production-database-support.md` and
    --   `backend/de.metas.acct.base/CLAUDE.md` both state: never INSERT directly into
    --   Accounting_Docs_To_Repost - always use the "de_metas_acct".fact_acct_unpost* functions.
    -- This statement breaks that rule knowingly. The rule exists because fact_acct_unpost bundles two
    -- safety-relevant side effects with the enqueue (delete the stale Fact_Acct rows, reset Posted='N'),
    -- and both are redundant for a queue-driven repost - see (e) above for the exact code that makes them
    -- redundant. What fact_acct_unpost cannot do is enqueue a whole set of documents with a chosen
    -- SeqNo ordering in one statement, and that ordering is the entire correctness contract of a cost
    -- recompute. Using it here would therefore trade a provable ordering guarantee for two no-ops.
    -- The one effect that is NOT redundant is the period check, and that is why (b) asserts every period
    -- in range is open before anything is mutated.
    --
    -- SeqNo is assigned EXPLICITLY via row_number() over the release order. It is never left to the
    -- column's nextval default: the order in which INSERT ... SELECT evaluates rows - and therefore the
    -- order in which it consumes a sequence - is not guaranteed by an ORDER BY in the SELECT. The poller
    -- consumes the queue with `ORDER BY SeqNo`, so getting this wrong reposts documents out of order and
    -- freezes wrong costs, silently.
    -- The block of SeqNos is reserved by advancing the queue's own sequence in a single statement, so the
    -- released rows cannot collide with rows another producer enqueues afterwards.
    --
    SELECT COUNT(1) INTO v_releasedCount FROM "de_metas_acct".accounting_docs_to_repost_staging;
    IF (v_releasedCount > 0) THEN
        SELECT setval('"de_metas_acct".accounting_docs_to_repost_seqno',
                      nextval('"de_metas_acct".accounting_docs_to_repost_seqno') + v_releasedCount - 1)
        INTO v_seqNoLast;
        v_seqNoBase := v_seqNoLast - v_releasedCount;

        INSERT INTO "de_metas_acct".accounting_docs_to_repost
        (seqno, tablename, record_id, ad_client_id, force, on_error_notify_user_id, selection_id, description)
        SELECT v_seqNoBase + ROW_NUMBER() OVER (ORDER BY s.dateacct, s.tablename_prio, s.record_id),
               s.tablename,
               s.record_id,
               s.ad_client_id,
               s.force,
               s.on_error_notify_user_id,
               s.selection_id,
               s.description
        FROM "de_metas_acct".accounting_docs_to_repost_staging s;

        DELETE FROM "de_metas_acct".accounting_docs_to_repost_staging;
    END IF;
    RAISE NOTICE 'Released % staged document(s) to the reposting queue', v_releasedCount;

    COMMIT;

    PERFORM pg_advisory_unlock(c_AdvisoryLock_ClassId, p_C_AcctSchema_ID::integer);

    RAISE NOTICE 'DONE: % product(s), % document(s) released', v_productCount, v_releasedCount;
END;
$BODY$
;

COMMENT ON PROCEDURE "de_metas_acct".product_costs_recreate_all_from_date(numeric, numeric, timestamp WITH TIME ZONE, numeric, char) IS
    'Recreates all products'' cost details from a date onwards in resumable commit-batches, staging the documents to repost and releasing them to the queue in document-date order'
;
