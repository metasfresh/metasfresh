CREATE OR REPLACE FUNCTION dlm.disable_dlm_triggers(p_table_name text, p_silent boolean DEFAULT false)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_select_sql text;
	v_trigger_view_row dlm.triggers;
BEGIN
	v_trigger_select_sql = '
SELECT v.*
FROM dlm.triggers v 
	JOIN public.AD_Table t ON lower(t.TableName)=lower(v.table_name) 
			AND t.IsDLM=''Y'' /* ignore not-DLM tables because they might now even have e.g. DLMLevel columns */
		JOIN AD_Column c ON c.AD_Table_ID=t.AD_Table_ID AND lower(c.ColumnName)=lower(v.column_name)
WHERE c.IsDLMPartitionBoundary=''N''
	AND  lower(v.foreign_table_name) = lower('''|| p_table_name ||''')';

	/* Iterate the dlm.triggers view and disable the triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE v_trigger_select_sql
	LOOP
		BEGIN
			EXECUTE v_trigger_view_row.disable_dlm_trigger_ddl;

			IF p_silent = FALSE THEN
				RAISE NOTICE 'disable_dlm_triggers - %: Disabled dlm trigger analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
			END IF;
		EXCEPTION
			WHEN undefined_object /*SQLState 42704*/ THEN RAISE WARNING 'No such trigger, SQL statement % failed. Nothing do to.', v_trigger_view_row.disable_dlm_trigger_ddl;
		END;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.disable_dlm_triggers(text) IS 
'Uses the view dlm.triggers to disable tiggers for the given p_table_name which were created using the dlm.triggers view.
If called with p_silent=false, is invokes "RAISE NOTICE" on each triggers that it enabled.
If if disabling an individual trigger fails, it logs a warning and goes on.
See gh #489.'
;