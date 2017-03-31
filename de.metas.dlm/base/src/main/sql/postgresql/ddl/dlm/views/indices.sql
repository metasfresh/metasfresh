
DROP VIEW IF EXISTS dlm.indices;
CREATE OR REPLACE VIEW dlm.indices AS 
SELECT 
	c_t.relname table_name,
	c_i.relname index_name,
	pg_relation_size(c_i.relname::regclass) AS current_index_size,
	pg_size_pretty(pg_relation_size(c_i.relname::regclass)) AS current_index_size_pretty,
	pg_get_indexdef(indexrelid) || ';' AS current_index_create_ddl,
	'DROP INDEX IF EXISTS ' || c_i.relname || ';' AS index_drop_ddl,
	pg_get_indexdef(indexrelid) ||
	CASE
		/* prepend the "where" and the condition */
		WHEN NOT pg_get_indexdef(indexrelid) ILIKE '% WHERE %' THEN ' WHERE COALESCE(dlm_level, 0::smallint) = 0::smallint'
		
		/* just prepend the condition, there is already a "where"  */
		WHEN pg_get_indexdef(indexrelid) ILIKE '% WHERE %' AND NOT pg_get_indexdef(indexrelid) ILIKE ' WHERE%COALESCE(dlm_level, 0::smallint) = 0::smallint%' THEN ' AND COALESCE(dlm_level, 0::smallint) = 0::smallint' 
		
		/* do nothing */
		WHEN pg_get_indexdef(indexrelid) ILIKE ' WHERE %COALESCE(dlm_level, 0::smallint) = 0::smallint%' THEN ''
	END || ';' AS new_index_create_ddl
FROM pg_index i
JOIN pg_class c_t ON c_t.oid = i.indrelid
JOIN pg_class c_i ON c_i.oid = i.indexrelid
WHERE true 
	AND c_t.relname LIKE '%_tbl'
	AND NOT i.IndIsPrimary
	AND NOT i.indIsUnique
	AND NOT c_i.relname ILIKE '%_dlm_level' /* dont't fiddle with "our" DLM indexes, that's already done elsewhere */
ORDER BY index_name;
COMMENT ON VIEW dlm.indices IS 'This view *was* used by the functions that DLM and un-DLM tables. Currently it seems as if we won''t need it in future';
--SELECT * FROM dlm.indices;