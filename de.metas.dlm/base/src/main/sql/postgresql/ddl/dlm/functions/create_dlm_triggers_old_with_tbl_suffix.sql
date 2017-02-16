
CREATE OR REPLACE FUNCTION dlm.create_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
BEGIN
	/* Iterate the dlm.triggers view and create triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.create_dlm_triggerfunction_ddl;
		EXECUTE v_trigger_view_row.create_dlm_trigger_ddl;
	
		RAISE NOTICE 'create_dlm_triggers - %: Created dlm trigger and trigger-function analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.create_dlm_triggers(text) IS 
'Uses the view dlm.triggers to iterate "incoming" FK constraints for the table and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. See the view dlm.triggers for more details.
See gh #489.';