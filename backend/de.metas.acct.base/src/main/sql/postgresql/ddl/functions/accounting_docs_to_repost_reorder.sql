DROP FUNCTION IF EXISTS "de_metas_acct".accounting_docs_to_repost_reorder();

CREATE OR REPLACE FUNCTION "de_metas_acct".accounting_docs_to_repost_reorder()
    RETURNS void
AS
$BODY$
DECLARE
    rowcount      integer;
BEGIN
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

    GET DIAGNOSTICS rowcount = ROW_COUNT;
	raise notice 'Reordered % rows', rowcount;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;


