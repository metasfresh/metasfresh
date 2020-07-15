CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
    -- v_index_view_row dlm.indices;
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'ALTER TABLE ' || p_table_name || ' RENAME TO ' || p_table_name || '_tbl;';
	RAISE NOTICE 'Renamed table % to %', p_table_name, p_table_name||'_tbl';
	
	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Level smallint;'; /* using smallint, see https://www.postgresql.org/docs/9.1/static/datatype-numeric.html */
		RAISE NOTICE 'Added column DLM_Level to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
		BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Partition_ID numeric(10,0);';
		RAISE NOTICE 'Added column DLM_Partition_ID to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Partition_ID already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
	
	/* 
	   partial index; we *wanted* postgresql to actually pick this one, because it's small and doesn't grow with the table (as long as we manage to limit the number of "production" records).
	   But we can't have current_setting('metasfresh.DLM_Level') in the index predicate (a.k.a. where-clause) because current_setting() is not an immutable function.
	   Also see http://stackoverflow.com/a/26031289/1012103
	EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (COALESCE(DLM_Level,0::smallint)) WHERE COALESCE(DLM_Level,0::smallint) = 0::smallint;'; 
	RAISE NOTICE 'Created index %_DLM_Level', p_table_name;
	*/
	
	/* 
	   non-partial index; it's large and grows with the table, but so does everything else, and at most times the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	*/
	EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (DLM_Level)';   
	RAISE NOTICE 'Created index %_DLM_Level', p_table_name;
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
	RAISE NOTICE 'Created view dlm.%', p_table_name;

	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.create_dlm_triggerfunction_ddl;
		EXECUTE v_trigger_view_row.create_dlm_trigger_ddl;
	
		RAISE NOTICE 'Created dlm trigger and trigger-function analog to FK constraint %', v_trigger_view_row.constraint_name;
	END LOOP;
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE ' || p_table_name || '_tbl;'; 
	RAISE NOTICE 'Called ANALYZE %', p_table_name || '_tbl';
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.add_table_to_dlm(text) IS 'gh #235, #489: DLMs the given table:
* Adds a DLM_Level and DLM_Partition_ID column to the table.
* Renames the table to "<tablename>_tbl" and creates a view named <tablename> that selects * from the table, but has a where-clause to make it select only records with DLM_Level <= current_setting(''metasfresh.DLM_Level'')
* Creates an index for the new DLM_Level column
* iterates "incoming" FK constraints for the table and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. See the view dlm.triggers for more details.
* Does an analyze on the table
';


CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Level;';
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Partition_ID;';
		RAISE NOTICE 'Dropped columns DLM_Level and DLM_Partition_ID from table % (if they existed)', p_table_name;
		
	ELSE
		RAISE NOTICE 'Retained columns DLM_Level and DLM_Partition_ID of table %', p_table_name;
	END IF;

	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.drop_dlm_trigger_ddl;
	
		RAISE NOTICE 'Dropped dlm trigger analog to FK constraint %', v_trigger_view_row.constraint_name;
	END LOOP;
	
	EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl RENAME TO ' || p_table_name || ';';
	RAISE NOTICE 'Renamed table % back to % ', p_table_name||'_Tbl', p_table_name;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS 'gh #235, #489: Un-DLMs the given table:
* drops the view and removes the "_tbl" suffix from the table name
* drops partial indices
* drops the tiggers and triggerfunctions that were created using the dlm.triggers view.
* optionally drops the DLM_Level and DLM_Partition_ID column, if told so explicitly with the p_retain_dlm_column parameter set to false.';





DROP FUNCTION IF EXISTS dlm.get_dlm_records(text, numeric(10,0));
CREATE OR REPLACE FUNCTION dlm.get_dlm_records(p_Table_Name text, p_DLM_Partition_ID numeric(10,0))
	RETURNS TABLE(TableName text, DLM_Partition_ID numeric(10,0), Record_ID numeric(10,0)) AS
$BODY$
BEGIN
	RETURN QUERY EXECUTE 'SELECT '||p_Table_Name||', '||p_DLM_Partition_ID||', '||p_Table_Name||'_ID AS Record_ID FROM '||p_Table_Name||' WHERE DLM_Partition_ID='||p_DLM_Partition_ID||';';
END;	
$BODY$
	LANGUAGE plpgsql STABLE;
