DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder()
;

CREATE OR REPLACE FUNCTION "de_metas_acct".accounting_docs_to_repost_reorder()
    RETURNS void
AS
$BODY$
DECLARE
    rowcount integer;
BEGIN
    WITH docs AS (SELECT t.*,
                         ROW_NUMBER() OVER (
                             ORDER BY --
                                 t.dateacct,
                                 t.tablename_prio,
                                 LEAST(t.record_id, t.reversal_id),
                                 t.record_id
                             ) AS new_seqNo
                  FROM (SELECT DISTINCT --
                                        TRUNC(doc.dateacct, 'Q') AS dateacct,
                                        doc.tablename_prio,
                                        doc.tablename,
                                        doc.record_id,
                                        doc.reversal_id
                        FROM "de_metas_acct".accounting_docs_to_repost r
                                 INNER JOIN "de_metas_acct".accountable_docs_and_lines_v doc ON doc.tablename = r.tablename AND doc.record_id = r.record_id) t)
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


