

CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';

	/* drop the triggers first, because they depends on the DLM_Level column (important in case of p_retain_dlm_column=false). */
 	PERFORM dlm.drop_dlm_triggers(p_table_name);
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Level;';
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Partition_ID;';
		RAISE NOTICE 'remove_table_from_dlm - %: Dropped columns DLM_Level and DLM_Partition_ID from table % (if they existed)', p_table_name, p_table_name;
		
	ELSE
		RAISE NOTICE 'remove_table_from_dlm - %: Retained columns DLM_Level and DLM_Partition_ID of table %', p_table_name, p_table_name;
	END IF;
	
--	EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl RENAME TO ' || p_table_name || ';';
--	RAISE NOTICE 'remove_table_from_dlm - %: Renamed table % back to % ', p_table_name, p_table_name||'_Tbl', p_table_name;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS 'gh #235, #489: Un-DLMs the given table:
* drops the view for the given table name
* drops partial indices
* invokes dlm.drop_dlm_triggers with the given table name.
* optionally drops the DLM_Level and DLM_Partition_ID column, if told so explicitly with the p_retain_dlm_column parameter set to false.';

?CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
    -- v_index_view_row dlm.indices;
	v_trigger_view_row dlm.triggers;
BEGIN

	BEGIN
		/* using smallint, see https://www.postgresql.org/docs/9.5/static/datatype-numeric.html */
		/* default 0, so new records are automatically "operational" */
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Level smallint DEFAULT 0;'; 
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Level to table %', p_table_name, p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Partition_ID numeric(10,0);';
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Partition_ID to table %', p_table_name, p_table_name||'_tbl';
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
	RAISE NOTICE 'add_table_to_dlm - %: Created indices %_DLM_Level and %_DLM_Partition_ID', p_table_name, p_table_name, p_table_name;
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
	RAISE NOTICE 'add_table_to_dlm - %: Created view dlm.%', p_table_name, p_table_name;

	/* Create triggers and trigger functions for each FK constraint.
	 */
	PERFORM dlm.create_dlm_triggers(p_table_name);
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE ' || p_table_name || '_tbl;'; 
	RAISE NOTICE 'Called ANALYZE %', p_table_name || '_tbl';
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

