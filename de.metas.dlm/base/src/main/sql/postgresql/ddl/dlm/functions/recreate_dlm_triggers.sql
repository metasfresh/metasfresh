CREATE OR REPLACE FUNCTION dlm.recreate_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
BEGIN
	PERFORM dlm.drop_dlm_triggers(p_table_name);
	PERFORM dlm.create_dlm_triggers(p_table_name);
END 
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.recreate_dlm_triggers(text) IS 'gh #235, #489: 
Calls both drop_dlm_triggers and create_dlm_triggers with the given p_table_name. 
Useful to to just update preexisting triggers and triggerfunctions without touching the indices.

Note: in order to recreate the triggers for all DLM tables, it''s better to run

SELECT TableName, dlm.drop_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM=''Y'';
commit;
SELECT TableName, dlm.create_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM=''Y'';

to avoid locking problems.
';
