
CREATE OR REPLACE FUNCTION table_record_reference_retrieve_distinct_ids(
	p_tablename character varying,
	p_id_columnname character varying)
RETURNS SETOF numeric AS
$BODY$
DECLARE 
	searchsql text := '';
	var_match numeric;
BEGIN
	searchsql := 'WITH RECURSIVE t(n) AS (
	    SELECT MIN('||p_id_columnname||') FROM '||p_tablename||'
	  UNION
	    SELECT (
		SELECT '||p_id_columnname||'
		FROM '||p_tablename||'
		WHERE '||p_id_columnname||' > n 
		ORDER BY '||p_id_columnname||'
		LIMIT 1)
	    FROM t WHERE n IS NOT NULL
	)
	SELECT n FROM t WHERE COALESCE(n,0)!=0;';

	FOR var_match IN EXECUTE(searchsql) LOOP /*i'm sure this can be done better, but don't have the time to look it up*/
		RETURN NEXT var_match;
	END LOOP;
END;
$BODY$
LANGUAGE plpgsql STABLE;
COMMENT ON FUNCTION table_record_reference_retrieve_distinct_ids(character varying, character varying)
IS 'This function is a fast alternative to SELECT DISTINCT(<p_id_columnname>) FROM <p_tablename> WHERE COALESCE(<p_id_columnname>,0)!=0
E.g. on a large DB, getting the different AD_Table_IDs from AD_ChangeLog took > 60 seconds with "SELECT DISTINCT" and 50ms with this function.

Usage example: select table_record_reference_retrieve_distinct_ids(''AD_ChangeLog'', ''AD_Table_ID'');

Many thanks to http://zogovic.com/post/44856908222/optimizing-postgresql-query-for-distinct-values

Issue https://github.com/metasfresh/metasfresh/issues/3389';
