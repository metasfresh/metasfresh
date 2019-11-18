
CREATE OR REPLACE FUNCTION public.altercolumn(
	tablename name,
	columnname name,
	datatype name,
	nullclause character varying,
	defaultclause character varying)
    RETURNS void
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$
declare
	command text;
	viewtext text[];
	viewname text[];
	dropviews text[];
	i int;
	j int;
	v record;
	sqltype       text;
	sqltype_short text;
	typename name;
begin
	if datatype is not null then
		select pg_type.typname, format_type(pg_type.oid, pg_attribute.atttypmod)
			into typename, sqltype
			from pg_class, pg_attribute, pg_type
			where relname = lower(tablename)
			and relkind = 'r'
			and pg_class.oid = pg_attribute.attrelid
			and attname = lower(columnname)
			and atttypid = pg_type.oid;
			sqltype_short := sqltype;
			
		if typename = 'numeric' then
			sqltype_short := replace(sqltype, ',0', '');
		elsif strpos(sqltype,'character varying') = 1 then
			sqltype_short := replace(sqltype, 'character varying', 'varchar');
		elsif sqltype = 'timestamp without time zone' then
			sqltype_short := 'timestamp';
		end if;
		
		if lower(datatype) <> sqltype and lower(datatype) <> sqltype_short then
			raise notice 'Changing datatype for %.%: ''%'' to ''%''', 'public.'||tablename, columnname, sqltype, datatype;
			
			-- Fetch dependent views
			i := 0;
			
	-- use the "new" db_dependent_views view instead of teh old db_dependents view
	-- trigger for the change:
	--   db_dependents returns materialized views as if they were normal views; that fails when we try to drop them
	--   db_dependent_views does not return them at all; 
	--   TODO (maybe, later, if needed): extend db_dependent_views to return materialized views, too, but make sure (=>new return-column) that the caller has a change to identify them and deal with them property.
			--for v in (select view_name as full_view_name, depth FROM db_dependents('public.'||tablename::regclass) order by depth desc)
			for v in (select distinct '"'||view_schema||'".'||view_name as full_view_name, depth FROM db_dependent_views(tablename) order by depth desc)
			loop
				if (viewname @> array[v.full_view_name::text]) then
					-- raise notice '        skip view % because it was already detected as a dependency', v.relname;
					continue;
				end if;
				i := i + 1;
				viewtext[i] := pg_get_viewdef(v.full_view_name);
				viewname[i] := v.full_view_name::text;
				raise notice '    Found dependent: %', viewname[i];
			end loop;
			
			if i > 0 then
				begin
					for j in 1 .. i loop
						command := 'drop view if exists ' || viewname[j];
						raise notice 'Going to execute command %', command;
						--raise notice 'View-DDL: %',viewtext[j];
						execute command;
						dropviews[j] := viewname[j];
					end loop;
				exception
					when others then
						RAISE INFO 'Exception dropping view:';
						RAISE INFO 'Error Name:%',SQLERRM;
						RAISE INFO 'Error State:%', SQLSTATE;
						RAISE INFO 'Will attempt to restore what we dropped so far';
						i := array_upper(dropviews, 1);
						if i > 0 then
							for j in reverse i .. 1 loop
								raise notice '    Creating(recovery) view %', viewname[j];
								command := 'create or replace view ' || viewname[j] || ' as ' || viewtext[j];
								execute command;
							end loop;
						end if;
						raise exception 'Failed to drop dependent view: % (SQL: %)', viewname[j], viewtext[j];
				end;
			end if;

			raise notice '    Actual datatype change: to ''%''', datatype;
			command := 'alter table public.' || lower(tablename) || ' alter column ' || lower(columnname) || ' type ' || lower(datatype);
			execute command;
			
			i := array_upper(dropviews, 1);
			if i > 0 then
				for j in reverse i .. 1 loop
					raise notice '    Creating view %', dropviews[j];
					command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j];
					execute command;
				end loop;
			end if;
        end if;
	end if;
   
	if defaultclause is not null then
		raise notice 'Changing default clause for %.%: ''%''', 'public.'||tablename, columnname, defaultclause;
		--
		if lower(defaultclause) = 'null' then
			command := 'alter table public.' || lower(tablename) || ' alter column ' || lower(columnname) || ' drop default ';
		else
			command := 'alter table public.' || lower(tablename) || ' alter column ' || lower(columnname) || ' set default ''' || defaultclause || '''';
		end if;
		execute command;
	end if;
   
	if nullclause is not null then
		raise notice 'Changing null clause for %.%: ''%''', 'public.'||tablename, columnname, nullclause;
		--
		if lower(nullclause) = 'not null' then
			command := 'alter table public.' || lower(tablename) || ' alter column ' || lower(columnname) || ' set not null';
			execute command;
		elsif lower(nullclause) = 'null' then
			command := 'alter table public.' || lower(tablename) || ' alter column ' || lower(columnname) || ' drop not null';
			execute command;
		end if;
	end if;
end;
$BODY$;
COMMENT ON FUNCTION public.altercolumn(name, name, name, character varying, character varying)
    IS 'Performs DDL changes on tables that may also be part of a view. Assumes that the given table is always in the "public" schema';
