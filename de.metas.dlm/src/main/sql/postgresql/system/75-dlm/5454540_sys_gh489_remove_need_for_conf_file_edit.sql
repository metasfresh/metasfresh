
CREATE OR REPLACE FUNCTION dlm.get_dlm_setting(IN p_setting text, IN p_default smallint)
  RETURNS smallint AS
$BODY$
DECLARE 
	v_result smallint;
BEGIN
	select current_setting(p_setting)::smallint INTO v_result;
	RETURN v_result;
EXCEPTION 
	WHEN undefined_object /* SQLSTATE '42704' */
	THEN 
		RAISE NOTICE '% is not yet set. Setting it to % for now', p_setting, p_default;
		PERFORM set_config(p_setting, p_default::text, false);
		RETURN p_default;
END;	
$BODY$
  LANGUAGE plpgsql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_setting(text, smallint) IS 'gh #489: Gets the given setting using current_setting(). 
If that setting is not defined (SQLSTATE ''42704'') then it sets it using set_config() and returns the given default value.'
;
----------------------
CREATE OR REPLACE FUNCTION dlm.get_dlm_level()
  RETURNS smallint AS
$BODY$
	select dlm.get_dlm_setting('metasfresh.DLM_Level', 1::smallint);
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_level() IS 'gh #489: Invokes SELECT current_setting(''metasfresh.DLM_Level'') and returns the result.
If no setting for metasfresh.DLM_Level is defined, then it sets it to 1 for future invocations and also returns 1.

Note 1: This function is called from both java and also heavily used by the views that are created by the function dlm.reset_dlm_view(p_table_name text).
Note 2: If you change the setting name or this function''s name, please also keep it in sync with the constant in de.metas.dlm.connection.DLMConnectionUtils.'
;
----------------------
CREATE OR REPLACE FUNCTION dlm.get_dlm_coalesce_level()
  RETURNS smallint AS
$BODY$
	select dlm.get_dlm_setting('metasfresh.DLM_Coalesce_Level', 1::smallint);
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_coalesce_level() IS 'gh #489: Invokes SELECT current_setting(''metasfresh.DLM_Level'') and returns the result.
If no setting for metasfresh.DLM_Level is defined, then it sets it to 1 for future invocations and also returns 1.

Note 1: This function is called from both java and also heavily used by the views that are created by the function dlm.reset_dlm_view(p_table_name text).
Note 2: If you change the setting name or this function''s name, please also keep it in sync with the constant in de.metas.dlm.connection.DLMConnectionUtils.'
;
----------------------
CREATE OR REPLACE FUNCTION dlm.reset_dlm_view(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	i int;
	j int;
	v record;
	viewFullName text;
	viewFullNames text[];
	viewtexts text[];
	dropviews text[];
	command text;
BEGIN

	RAISE NOTICE 'reset_dlm_view - %: Going to (re-)create view dlm.%', p_table_name, p_table_name;
-- Fetch dependent views
	i := 0;
--	for v in (select view_name, depth FROM db_dependents(p_table_name::regclass) order by depth desc)
	for v in (select view_schema, view_name, depth FROM public.db_dependent_views(p_table_name) order by depth desc)
	loop
		viewFullName := '"'||v.view_schema||'".'||v.view_name;
		if (viewFullNames @> array[viewFullName]) then
			-- raise notice '        skip view % because it was already detected as a dependency', v.relname;
			continue;
		end if;
		if ( lower(viewFullName) = '"dlm".' || lower(p_table_name)) then
			-- raise notice '        skip view % because it is the DLM view which we are actually going to drop', v.relname;
			continue;
		end if;
		i := i + 1;
		viewtexts[i] := (select view_definition from information_schema.views where views.table_schema=v.view_schema AND views.table_name=v.view_name);
		viewFullNames[i] := viewFullName;
		raise notice '    Found dependent: %', viewFullNames[i];
	end loop;
	
	if i > 0 then
		for j in 1 .. i loop
			raise notice '    Dropping view %', viewFullNames[j];
			command := 'drop view if exists ' || viewFullNames[j];
			execute command;
			dropviews[j] := viewFullNames[j];
		end loop;
	end if;

	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name || ';';
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM public.' || p_table_name || ' WHERE COALESCE(DLM_Level, dlm.get_dlm_coalesce_level()) <= dlm.get_dlm_level();';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';

	if i > 0 then
		for j in reverse i .. 1 loop
			raise notice '    Creating view %', dropviews[j];
			command := 'create or replace view ' || dropviews[j] || ' as ' || viewtexts[j];
			execute command;
		end loop;
	end if;

	RAISE NOTICE 'reset_dlm_view - %: Created view dlm.%', p_table_name, p_table_name;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.reset_dlm_view(text) IS 'gh #235, #489: Creates or drops and recreates for the given table a DLM view in the in the "dlm" schema. A lot of code is borrowed from the function public.altercolumn()';

--DO $$ 
--BEGIN
select dlm.reset_dlm_view(tableName) from AD_Table where IsDLM='Y';
--EXCEPTION WHEN OTHERS THEN RAISE NOTICE 'DDL failed which is OK if there';
--END $$;
--DO $$ 


