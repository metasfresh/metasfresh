CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
BEGIN
	BEGIN
		/* using smallint, see https://www.postgresql.org/docs/9.5/static/datatype-numeric.html */
		/* add without default because we can't touch every preexisting record */
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ADD COLUMN DLM_Level smallint;'; 
		/* *now* set default 0, so that new records are automatically inserted as "operational" */
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ALTER COLUMN DLM_Level SET DEFAULT 0;';
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Level to table %', p_table_name, p_table_name;
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name;
    END;

	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ADD COLUMN DLM_Partition_ID numeric(10,0);';
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Partition_ID to table %', p_table_name, p_table_name;
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Partition_ID already exists in %. Nothing do to', p_table_name;
    END;

	/* Generally, we would like to create indices concurrently because this DB-function might be executed on large tables and during production. 
	   See https://www.postgresql.org/docs/9.5/static/sql-createindex.html#SQL-CREATEINDEX-CONCURRENTLY.
	   However, this doesn't work: "ERROR: CREATE INDEX CONCURRENTLY cannot be executed from a function or multi-command string"
	 */
	/* Non-partial indices; they are large and grow with the table, but so does everything else, and at most times the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	 */
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Level ON ' || p_table_name || ' (DLM_Level)';   
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Partition_ID ON ' || p_table_name || ' (DLM_Partition_ID)';   
	RAISE NOTICE 'add_table_to_dlm - %: Created indices %_DLM_Level and %_DLM_Partition_ID', p_table_name, p_table_name, p_table_name;
	
	PERFORM dlm.reset_dlm_view(p_table_name);
	
	RAISE NOTICE 'add_table_to_dlm - %: Created view dlm.%', p_table_name, p_table_name;

	/* Create triggers and trigger functions for each FK constraint.
	 */
	PERFORM dlm.create_dlm_triggers(p_table_name);
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE public.' || p_table_name || ';'; 
	RAISE NOTICE 'Called ANALYZE public.%', p_table_name;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.add_table_to_dlm(text) IS 'gh #235, #489: DLMs the given table:
* Adds a DLM_Level and DLM_Partition_ID column to the table.
* Creates a view named <tablename> in the dlm schema that selects * from the table, but has a where-clause to make it select only records with DLM_Level <= current_setting(''metasfresh.DLM_Level'')
* Creates an index for the new DLM_Level column
* invokes dlm.create_dlm_triggers for the given p_tablename
* Does an analyze on the table
';