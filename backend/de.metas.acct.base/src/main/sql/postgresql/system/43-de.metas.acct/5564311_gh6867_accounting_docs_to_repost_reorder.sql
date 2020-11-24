DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder();

CREATE OR REPLACE FUNCTION "de_metas_acct".accounting_docs_to_repost_reorder()
    RETURNS void
AS
$BODY$
DECLARE
    rowcount integer;
BEGIN
    --
    -- Remove duplicate enqueued documents
    WITH duplicates AS (
        SELECT r.tablename,
               r.record_id,
               count(1)   AS count,
               min(r.oid) AS min_oid
        FROM "de_metas_acct".accounting_docs_to_repost r
        GROUP BY r.tablename,
                 r.record_id
        HAVING count(1) > 1
    )
    DELETE
    FROM "de_metas_acct".accounting_docs_to_repost r
    WHERE exists(SELECT 1
                 FROM duplicates
                 WHERE r.tablename = duplicates.tablename
                   AND r.record_id = duplicates.record_id
                   AND r.oid <> duplicates.min_oid);
    --
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Deleted % duplicate rows', rowcount;


    --
    -- Re-order enqueued documents by DateAcct (and some other fields)
    WITH docs AS (
        SELECT t.*,
               row_number() OVER (
                   ORDER BY t.dateacct,
                       t.tablename_prio,
                       least(t.record_id, t.reversal_id),
                       t.record_id
                   ) AS new_seqNo
        FROM (
                 SELECT DISTINCT doc.dateacct,
                                 doc.tablename_prio,
                                 doc.tablename,
                                 doc.record_id,
                                 doc.reversal_id
                 FROM "de_metas_acct".accounting_docs_to_repost r
                          INNER JOIN "de_metas_acct".accountable_docs_and_lines_v doc ON doc.tablename = r.tablename AND doc.record_id = r.record_id
             ) t
    )
    UPDATE "de_metas_acct".accounting_docs_to_repost t
    SET seqNo=docs.new_seqNo
    FROM docs
    WHERE t.tablename = docs.tablename
      AND t.record_id = docs.record_id;
    --
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Reordered % rows', rowcount;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;


