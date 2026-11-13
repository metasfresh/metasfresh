DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder()
;

DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder(
    p_DateAcct_Trunc varchar
)
;

DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder(
    p_DateAcct_Trunc varchar,
    p_AD_Client_ID   numeric
)
;

-- p_AD_Client_ID: when set, only queue rows of documents of this client are reordered; rows of other clients keep their SeqNo.
CREATE OR REPLACE FUNCTION "de_metas_acct".accounting_docs_to_repost_reorder(
    p_DateAcct_Trunc varchar = 'DD',
    p_AD_Client_ID   numeric = NULL
)
    RETURNS void
AS
$BODY$
DECLARE
    rowcount integer;
BEGIN
    RAISE NOTICE 'accounting_docs_to_repost_reorder: p_DateAcct_Trunc=%, p_AD_Client_ID=%', p_DateAcct_Trunc, p_AD_Client_ID;

    -- The callers insert many rows into the queue in the same transaction and call this function right away.
    -- Without fresh statistics the planner can assume an (almost) empty queue and pick a very slow nested loop.
    -- Note: ANALYZE holds a ShareUpdateExclusiveLock on the queue until the caller's transaction ends. It does not block
    -- INSERT/SELECT/DELETE on the queue, but it blocks autovacuum of the queue and a second concurrent run of this function.
    ANALYZE "de_metas_acct".accounting_docs_to_repost;

    WITH docs AS (SELECT t.*,
                         ROW_NUMBER() OVER (
                             ORDER BY --
                                 t.dateacct,
                                 t.tablename_prio,
                                 LEAST(t.record_id, t.reversal_id),
                                 t.record_id
                             ) AS new_seqNo
                  FROM (SELECT DISTINCT --
                                        TRUNC(doc.dateacct, p_DateAcct_Trunc) AS dateacct,
                                        doc.tablename_prio,
                                        doc.tablename,
                                        doc.record_id,
                                        doc.reversal_id
                        FROM "de_metas_acct".accounting_docs_to_repost r
                                 INNER JOIN "de_metas_acct".accountable_docs_and_lines_v doc ON doc.tablename = r.tablename AND doc.record_id = r.record_id
                        WHERE p_AD_Client_ID IS NULL
                           OR doc.ad_client_id = p_AD_Client_ID) t)
    UPDATE "de_metas_acct".accounting_docs_to_repost t
    SET seqNo=docs.new_seqNo
    FROM docs
    WHERE t.tablename = docs.tablename
      AND t.record_id = docs.record_id;

    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'accounting_docs_to_repost: Reordered % rows by their document date', rowcount;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;


