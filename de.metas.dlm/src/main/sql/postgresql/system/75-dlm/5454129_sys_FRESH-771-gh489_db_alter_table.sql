
  
CREATE OR REPLACE FUNCTION public.db_alter_table(
    p_table_name text,
    p_ddl text)
  RETURNS void AS
$BODY$
declare
	i int;
	j int;
	v record;
	viewFullName text[];
	viewtext text[];
	dropviews text[];
	command text;
begin

	-- Fetch dependent views
	i := 0;
	for v in (select view_schema, view_name, depth FROM public.db_dependent_views(p_table_name) order by depth desc)
	loop
		if (viewFullName @> array[v.view_name]) then
			-- raise notice ' skip view % because it was already detected as a dependency', v.relname;
			continue;
		end if;
		i := i + 1;
		viewtext[i] := (select view_definition from information_schema.views where views.table_schema=v.view_schema AND views.table_name=v.view_name);
		viewFullName[i] := '"'||v.view_schema||'".'||v.view_name;
		raise notice '    Found dependent view: %', viewFullName[i];
	end loop;
	
	if i > 0 then
			for j in 1 .. i loop
				raise notice '    Dropping view %', viewFullName[j];
				command := 'drop view if exists ' || viewFullName[j];
				execute command;
				dropviews[j] := viewFullName[j];
			end loop;
	end if;

	raise notice 'Executing the given DDL statement ''%''', p_ddl;
	execute p_ddl;
			
	if i > 0 then
		for j in reverse i .. 1 loop
			if lower(dropviews[j]) = '"dlm".'||lower(p_table_name) 
			then
				/* this special view amounts to "select * from p_tablename.." but in viewtext[j], all columns are explicitly enumerated. So recreating the view with viewtext[j] won't work in case we jsut dropped on of those columns */
				command := 'SELECT dlm.reset_dlm_view(''' || p_table_name || ''');';
			else
				command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j] ||';';
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
