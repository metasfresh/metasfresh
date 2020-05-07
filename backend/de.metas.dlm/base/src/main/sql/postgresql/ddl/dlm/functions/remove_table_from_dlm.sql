
CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
	i int;
	j int;
	v record;
	viewFullName text;
	viewFullNames text[];
	viewtexts text[];
	dropviews text[];
	command text;
BEGIN

	-- Fetch views that depend on p_table_name
	i := 0;
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

	-- drop the views we just fetched
	if i > 0 then
		for j in 1 .. i loop
			raise notice '    Dropping view %', viewFullNames[j];
			command := 'DROP VIEW IF EXISTS ' || viewFullNames[j];
			execute command;
			dropviews[j] := viewFullNames[j];
		end loop;
	end if;
	
	-- actually drop the view
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	
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

	-- restore the dependent views. they shall now depend on the *table* that also has the name p_table_name
	i := array_upper(dropviews, 1);
	if i > 0 then
		for j in reverse i .. 1 loop
			raise notice '    Creating view %', dropviews[j];
			
			-- when creating the view, we need to make sure, that any references to the view we just destroyed are replaced with references to the remaining table.
			-- the flags mean: i => case insensitive; g => replace all occurences
			command := regexp_replace('CREATE VIEW ' || dropviews[j] || ' AS ' || viewtexts[j], '"?dlm"?\.'||p_table_name, 'public.'||p_table_name, 'ig');
			execute command;
		end loop;
	end if;
	
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
