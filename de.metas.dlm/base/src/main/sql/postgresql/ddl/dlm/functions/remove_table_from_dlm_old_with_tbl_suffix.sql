
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
