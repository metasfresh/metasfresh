
CREATE OR REPLACE FUNCTION dlm.create_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
	v_trigger_select_sql text;
BEGIN
	v_trigger_select_sql = '
SELECT v.*
FROM dlm.triggers v 
	JOIN public.AD_Table t ON lower(t.TableName)=lower(v.table_name) 
			AND t.IsDLM=''Y'' /* ignore not-DLM tables because they might now even have e.g. DLMLevel columns */
		JOIN AD_Column c ON c.AD_Table_ID=t.AD_Table_ID AND lower(c.ColumnName)=lower(v.column_name)
WHERE c.IsDLMPartitionBoundary=''N''
	AND  lower(v.foreign_table_name) = lower('''|| p_table_name ||''')';

	/* Iterate the dlm.triggers view and create triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE v_trigger_select_sql
	LOOP
		EXECUTE v_trigger_view_row.create_dlm_triggerfunction_ddl;
		EXECUTE v_trigger_view_row.create_dlm_trigger_ddl;
	
		RAISE NOTICE 'create_dlm_triggers - %: Created dlm trigger and trigger-function analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.create_dlm_triggers(text) IS 
'Uses the view dlm.triggers to iterate "incoming" FK constraints for the given p_table_name table (ignore case) and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. 
See the view dlm.triggers for more details.
Note that if the respective AD_Column is flagged with IsDLMPartitionBoundary=''Y'', then that column is skipped.
See gh #489.';

