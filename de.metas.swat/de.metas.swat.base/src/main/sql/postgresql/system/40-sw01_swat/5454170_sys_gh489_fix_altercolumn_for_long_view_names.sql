-- Function: altercolumn(name, name, name, character varying, character varying)

-- DROP FUNCTION altercolumn(name, name, name, character varying, character varying);

CREATE OR REPLACE FUNCTION altercolumn(tablename name, columnname name, datatype name, nullclause character varying, defaultclause character varying)
RETURNS void AS
$BODY$
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
			for v in (select view_name, depth FROM db_dependents('public.'||tablename::regclass) order by depth desc)
			loop
				if (viewname @> array[v.view_name::text]) then
					-- raise notice '        skip view % because it was already detected as a dependency', v.relname;
					continue;
				end if;
				i := i + 1;
				viewtext[i] := pg_get_viewdef(v.view_name::oid);
				viewname[i] := v.view_name::text;
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
$BODY$
LANGUAGE plpgsql VOLATILE COST 100;
COMMENT ON FUNCTION altercolumn(name, name, name, character varying, character varying) IS
'Performs DDL changes on tables that may also be part of a view. Assumes that the given table is always in the "public" schema';
