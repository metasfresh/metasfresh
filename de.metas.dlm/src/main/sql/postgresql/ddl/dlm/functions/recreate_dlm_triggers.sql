CREATE OR REPLACE FUNCTION dlm.recreate_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
BEGIN
	PERFORM dlm.drop_dlm_triggers(p_table_name);
	PERFORM dlm.create_dlm_triggers(p_table_name);
END 
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.recreate_dlm_triggers(text) IS 'gh #235, #489: 
Calls both drop_dlm_triggers and create_dlm_triggers with the given p_table_name. 
Useful to to just update preexisting triggers and triggerfunctions without touching the indices.';
