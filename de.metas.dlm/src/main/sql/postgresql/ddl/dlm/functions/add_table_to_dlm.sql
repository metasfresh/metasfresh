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
		/* using smallint, see https://www.postgresql.org/docs/9.5/static/datatype-numeric.html */
		/* default 0, so new records are automatically "operational" */
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Level smallint DEFAULT 0;'; 
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

	/* Generally, we would like to create indices concurrently because this DB-function might be executed on large tables and during production. 
	   See https://www.postgresql.org/docs/9.5/static/sql-createindex.html#SQL-CREATEINDEX-CONCURRENTLY.
	   However, this doesn't work: "ERROR: CREATE INDEX CONCURRENTLY cannot be executed from a function or multi-command string"
	 */
	/* Non-partial indices; they large and grow with the table, but so does everything else, and at most times the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	 */
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (DLM_Level)';   
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Partition_ID ON ' || p_table_name || '_tbl (DLM_Partition_ID)';   
	RAISE NOTICE 'Created indices %_DLM_Level and %_DLM_Partition_ID', p_table_name, p_table_name;
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
	RAISE NOTICE 'Created view dlm.%', p_table_name;

	/* Iterate the dlm.triggers view and create triggers for each FK constraint.
	 */
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