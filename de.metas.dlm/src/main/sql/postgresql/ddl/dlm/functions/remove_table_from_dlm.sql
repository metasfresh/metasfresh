
CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
	i int;
	j int;
	v record;
	viewname text[];
	viewtext text[];
	dropviews text[];
	command text;
BEGIN

	-- Fetch views that depend on p_table_name
	i := 0;
	for v in (select view_name, depth FROM db_dependents('public.'||p_table_name::regclass) order by depth desc)
	loop
		if (viewname @> array[v.view_name::text]) then
			-- raise notice '        skip view % because it was already detected as a dependency', v.relname;
			continue;
		end if;
		if (lower(v.view_name::text) = lower(p_table_name)) then
			-- raise notice '        skip view % because it is the DLM view which we are actually going to drop', v.relname;
			continue;
		end if;
		i := i + 1;
		viewtext[i] := pg_get_viewdef(v.view_name::oid);
		viewname[i] := v.view_name::text;
		raise notice '    Found dependent: %', viewname[i];
	end loop;

	-- drop the views we just fetched
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
	
	-- actually drop the view
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	
	-- restore the dependent view. the shall now depend on the *table* that also has the name p_table_name
	i := array_upper(dropviews, 1);
	if i > 0 then
		for j in reverse i .. 1 loop
			raise notice '    Creating view %', dropviews[j];
			command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j];
			execute command;
		end loop;
	end if;
	
	
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';

	/* drop the triggers first, because they depends on the DLM_Level column (important in case of p_retain_dlm_column=false). */
 	PERFORM dlm.drop_dlm_triggers(p_table_name);
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE public.' || p_table_name || ' DROP COLUMN IF EXISTS DLM_Level;';
		EXECUTE 'ALTER TABLE public.' || p_table_name || ' DROP COLUMN IF EXISTS DLM_Partition_ID;';
		RAISE NOTICE 'remove_table_from_dlm - %: Dropped columns DLM_Level and DLM_Partition_ID from table % (if they existed)', p_table_name, p_table_name;
		
	ELSE
		RAISE NOTICE 'remove_table_from_dlm - %: Retained columns DLM_Level and DLM_Partition_ID of table %', p_table_name, p_table_name;
	END IF;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS 'gh #235, #489: Un-DLMs the given table:
* drops the view for the given table name. 
    * Note that to achieve this we drop all views that depend on the given table name (i.e. the view wihich we want to get rid of) and then recreate them. The code for this is borrowed from the public.altercolumn function. 
	  When those depdendent views are recreated, they depend on the table which has the same name.
* drops partial indices.
* invokes dlm.drop_dlm_triggers with the given table name.
* optionally drops the DLM_Level and DLM_Partition_ID column, if told so explicitly with the p_retain_dlm_column parameter set to false.';
