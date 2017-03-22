
CREATE OR REPLACE FUNCTION dlm.get_dlm_coalesce_level()
  RETURNS smallint AS
$BODY$
	select dlm.get_dlm_setting('metasfresh.DLM_Coalesce_Level', 2::smallint);
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_coalesce_level() IS 'gh #489: Invokes SELECT current_setting(''metasfresh.DLM_Coalesce_Level'') and returns the result. 
If no setting for metasfresh.DLM_Coalesce_Level is defined, then it sets it to 2 for future invocations and also returns 2.

Note 1: This function is called from both java and also heavily used by the views that are created by the function dlm.reset_dlm_view(p_table_name text).
Note 2: If you change the setting name or this function''s name, please also keep it in sync with the constant in de.metas.dlm.connection.DLMConnectionUtils.'
;
