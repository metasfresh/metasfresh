CREATE OR REPLACE FUNCTION dlm.reset_dlm_view(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	viewtext text[];
	viewname name[];
BEGIN

-- Fetch dependent views
			i := 0;
			for v in (select view_name, depth FROM db_dependents(tablename::regclass) order by depth desc)
			loop
				if (viewname @> array[v.view_name::name]) then
					-- raise notice '        skip view % because it was already detected as a dependency', v.relname;
					continue;
				end if;
				i := i + 1;
				viewtext[i] := pg_get_viewdef(v.view_name::oid);
				viewname[i] := v.view_name::name;
				raise notice '    Found dependent: %', viewname[i];
			end loop;
	
		if i > 0 then
				begin
					for j in 1 .. i loop
						raise notice '    Dropping view %', viewname[j];
						command := 'drop view if exists ' || viewname[j];
						execute command;
						dropviews[j] := viewname[j];
					end loop;
				exception
					when others then
						i := array_upper(dropviews, 1);
						if i > 0 then
							for j in reverse i .. 1 loop
								raise notice '    Creating(recovery) view %', dropviews[j];
								command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j];
								execute command;
							end loop;
						end if;
						raise exception 'Failed to recreate dependent view: % (SQL: %)', dropviews[j], viewtext[j];
				end;
			end if;
	
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name || ';';
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || ' WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
	
	if i > 0 then
				for j in reverse i .. 1 loop
					raise notice '    Creating view %', dropviews[j];
					command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j];
					execute command;
				end loop;
			end if;
	
	
	RAISE NOTICE 'add_table_to_dlm - %: Created view dlm.%', p_table_name, p_table_name;
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.reset_dlm_view(text) IS 'gh #235, #489: Creates or drops and recreates for the given table a DLM view in the in the "dlm" schema.';