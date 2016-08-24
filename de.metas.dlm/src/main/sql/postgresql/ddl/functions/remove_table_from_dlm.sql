
CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE _index_view_row dlm.indices;
BEGIN
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP DLM_Level;';
		RAISE NOTICE 'Dropped column %.DLM_Level, if it existed ', p_table_name;
	ELSE
		RAISE NOTICE 'Retained column %.DLM_Level ', p_table_name;
	END IF;
		
	FOR _index_view_row IN 
		EXECUTE 'SELECT * FROM dlm.indices v WHERE lower(v.table_name) = lower('''|| p_table_name||'_tbl'') AND v.index_name LIKE ''%_dlm_partial'''
	LOOP
		EXECUTE 'DROP INDEX IF EXISTS ' || _index_view_row.index_name;
		RAISE NOTICE 'Dropped partial index %', _index_view_row.index_name;
	END LOOP;

--	RAISE NOTICE 'will run %', 'SELECT * FROM dlm.indices v WHERE lower(v.table_name) = lower('''|| p_table_name||'_tbl'') AND v.index_name LIKE ''%_dlm_full''';
	FOR _index_view_row IN 
		EXECUTE 'SELECT * FROM dlm.indices v WHERE lower(v.table_name) = lower('''|| p_table_name||'_tbl'') AND v.index_name LIKE ''%_dlm_full'''
	LOOP
		/* rename the full index brack to its former name without the "_dlm_full" suffix */
		EXECUTE 'ALTER INDEX ' || _index_view_row.index_name || ' RENAME TO ' || REGEXP_REPLACE(_index_view_row.index_name,'_dlm_full$', ''); 
		RAISE NOTICE 'Renamed full index % back to %', _index_view_row.index_name, REGEXP_REPLACE(_index_view_row.index_name,'_dlm_full$', ''); 
	END LOOP;

	EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl RENAME TO ' || p_table_name || ';';
	RAISE NOTICE 'Renamed table % back to % ', p_table_name||'_Tbl', p_table_name;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS '#235: Un-DLMs the given table:
* drops the view and removes the "_tbl" suffix from the table name
* drops partial indices
* renames the original "full" indices back to the lod name (i.e. removes the "_dlm_full" suffix)
* optionally drops the DLM column, if told so explicitly with the p_retain_dlm_column parameter set to false.';
