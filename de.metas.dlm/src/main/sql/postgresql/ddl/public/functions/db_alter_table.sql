
  
CREATE OR REPLACE FUNCTION public.db_alter_table(
    p_table_name text,
    p_ddl text)
  RETURNS void AS
$BODY$
declare
	i int;
	j int;
	v record;
	viewFullName text;
	viewFullNames text[];
	viewtexts text[];
	dropviews text[];
	command text;
begin

	-- Fetch dependent views
	i := 0;
	for v in (select view_schema, view_name, depth FROM public.db_dependent_views(p_table_name) order by depth desc)
	loop
		viewFullName := '"'||v.view_schema||'".'||v.view_name;
		if (viewFullNames @> array[viewFullName]) then
			-- raise notice ' skip view % because it was already detected as a dependency', v.relname;
			continue;
		end if;
		i := i + 1;
		viewtexts[i] := (select view_definition from information_schema.views where views.table_schema=v.view_schema AND views.table_name=v.view_name);
		viewFullNames[i] := viewFullName;
		raise notice '    Found dependent view: %', viewFullNames[i];
	end loop;
	
	if i > 0 then
			for j in 1 .. i loop
				raise notice '    Dropping view %', viewFullNames[j];
				command := 'drop view if exists ' || viewFullNames[j];
				execute command;
				dropviews[j] := viewFullNames[j];
			end loop;
	end if;

	raise notice 'Executing the given DDL statement ''%''', p_ddl;
	execute p_ddl;
			
	if i > 0 then
		for j in reverse i .. 1 loop
			if lower(dropviews[j]) = '"dlm".'||lower(p_table_name) 
			then
				/* This special view amounts to "select * from p_tablename.." but in viewtexts[j], all columns are explicitly enumerated. 
				   So recreating the view with viewtexts[j] won't work in case we just dropped one of those columns 
				 */
				command := 'SELECT dlm.reset_dlm_view(''' || p_table_name || ''');';
			else
				command := 'create or replace view ' || dropviews[j] || ' as ' || viewtexts[j] ||';';
			end if;
			
			raise notice 'Creating view %', dropviews[j];
			execute command;
		end loop;
	end if;

end;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

COMMENT ON FUNCTION public.db_alter_table(text, text) IS
'Drops all views that directly or indirectly depend on the given table (1st param), then executes the given DDL (2nd param) and finnal restores the views
This function is similar to the old public.altercolumn() function, but is more generic.
';
