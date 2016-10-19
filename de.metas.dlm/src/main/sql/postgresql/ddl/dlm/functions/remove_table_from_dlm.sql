
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
