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
			raise notice '        skip view % because it was already detected as a dependency', viewFullName;
			continue;
		end if;
		if ( lower(viewFullName) = '"dlm".' || lower(p_table_name)) then
			raise notice '        skip view % because it is the DLM view which we are actually going to drop', viewFullName;
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

	raise notice 'recreating dlm-view dlm.%', p_table_name;
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