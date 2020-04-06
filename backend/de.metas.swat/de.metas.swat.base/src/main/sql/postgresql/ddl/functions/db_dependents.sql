-- http://dewiki908/mediawiki/index.php/03168:_Referenznummer_soll_l%C3%A4nger_sein_%282012082010000102%29
DROP FUNCTION IF EXISTS db_dependents(regclass);
CREATE OR REPLACE FUNCTION db_dependents(p_tablename regclass)
RETURNS TABLE
(
	view_name regclass
	, depth integer
	, path regclass[]
)
AS
$BODY$
BEGIN
	RETURN QUERY
		WITH RECURSIVE views_list AS (
			SELECT
				c.oid::REGCLASS AS view_name
				, 0 as depth
				, ARRAY[c.oid::REGCLASS] as path
			FROM pg_class c
			WHERE c.oid::regclass = p_tablename
			--
			UNION ALL
			SELECT DISTINCT
				r.ev_class::REGCLASS AS view_name
				, views_list.depth + 1 as depth
				, views_list.path || (r.ev_class::REGCLASS) as path
			FROM pg_depend d
			INNER JOIN pg_rewrite r ON (r.oid = d.objid)
			INNER JOIN views_list ON (views_list.view_name = d.refobjid)
			WHERE d.refobjsubid != 0
		)
		SELECT * FROM views_list v
		where
			-- Exclude depth=0 which is actually the table name we were querying about
			v.depth > 0
		;
END; 
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;

ALTER FUNCTION db_dependents(p_tablename regclass) OWNER TO adempiere;

