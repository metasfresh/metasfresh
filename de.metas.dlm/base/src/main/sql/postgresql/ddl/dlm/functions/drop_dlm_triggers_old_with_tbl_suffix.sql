CREATE OR REPLACE FUNCTION dlm.drop_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
BEGIN
	/* Iterate the dlm.triggers view and drop the triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.drop_dlm_trigger_ddl;
	
		RAISE NOTICE 'drop_dlm_triggers - %: Dropped dlm trigger analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.create_dlm_triggers(text) IS 
'Uses the view dlm.triggers to drop the tiggers and triggerfunctions that were created using the dlm.triggers view.
See gh #489.'
;