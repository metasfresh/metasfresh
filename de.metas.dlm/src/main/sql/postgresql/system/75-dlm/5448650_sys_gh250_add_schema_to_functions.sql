
CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
    _index_view_row dlm.indices;
BEGIN
	EXECUTE 'ALTER TABLE ' || p_table_name || ' RENAME TO ' || p_table_name || '_tbl;';
	RAISE NOTICE 'Renamed table % to %', p_table_name, p_table_name||'_tbl';
	
	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Level smallint;'; /* using smallint, see https://www.postgresql.org/docs/9.1/static/datatype-numeric.html */
		RAISE NOTICE 'Added column DLM_Level to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
	/* 
	   non-partial index; we know that postgresql could pick this one, but it's large and grows with the table
	   EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (COALESCE(DLM_Level,0::smallint))'; 
	*/
	
	/* 
	   partial index; we *want* postgresql to actually pick this one, because it's small and doesn't grow with the table, as long as we manage to limit the number of "production" records
	   we can't have current_setting('metasfresh.DLM_Level') in the index predicate (a.k.a. where-clause) because it's not an immutable function.
	   Also see http://stackoverflow.com/a/26031289/1012103
	*/
	EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (COALESCE(DLM_Level,0::smallint)) WHERE COALESCE(DLM_Level,0::smallint) = 0::smallint;'; 
	RAISE NOTICE 'Created index %_DLM_Level', p_table_name;
	
	/*
		DLM_Level <= current_setting(''metasfresh.DLM_Level'')::smallint didn't work. 
		Even with the parameter beeing 0, the planner didn't know to use the partial index.
		Note that if the views where is changed, the partial indexe's predicate needs to be synched. 
		Again, also see http://stackoverflow.com/a/26031289/1012103
	*/
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level,0::smallint) = 0::smallint;';
	RAISE NOTICE 'Created view dlm.%', p_table_name;

	FOR _index_view_row IN 
		EXECUTE 'SELECT * FROM dlm.indices v WHERE lower(v.table_name) = lower('''|| p_table_name||'_tbl'')'
	LOOP
		EXECUTE 'ALTER INDEX ' || _index_view_row.index_name || ' RENAME TO ' ||  _index_view_row.index_name || '_dlm_full'; /* rename the existing index. we want to keep it */
		EXECUTE _index_view_row.new_index_create_ddl; /* add the "partial" voersion of the existing index */
		EXECUTE 'ALTER INDEX ' || _index_view_row.index_name || ' RENAME TO ' ||  _index_view_row.index_name || '_dlm_partial'; /* rename partial index */
		
		RAISE NOTICE 'Renamed pre-existing index % to ..._dlm_full and added a ..._dlm_partial pendant', _index_view_row.index_name;
	END LOOP;
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE ' || p_table_name || '_tbl;'; 
	RAISE NOTICE 'Called ANALYZE %', p_table_name || '_tbl';
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.add_table_to_dlm(text) IS '#235: DLMs the given table:
* Adds a DLM_Level column to the table.
* Renames the table to "<tablename>_tbl" and creates a view names <tablename> that select from the table, but has a where-clause to make it select only DLM_Level=0
* Creates a partial index for the new DLM_Level column
* Creates a new index named "<indexname>_dlm_partial" for each non-unique index. That partial index is like the original one, but with an additional predicate (where-clause) on DLM_Level=0.
  Caveat: won''t deal well with existing ORed predicates (TODO: fix)
* Renames all existing non-unique indixed to "<indexname>_dlm_full"
* Does an analyze on the table
';

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

