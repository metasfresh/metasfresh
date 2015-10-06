-- http://dewiki908/mediawiki/index.php/03168:_Referenznummer_soll_l%C3%A4nger_sein_%282012082010000102%29
DROP FUNCTION IF EXISTS db_dependents(character varying, character varying, integer); -- old version
DROP FUNCTION IF EXISTS db_dependents(regclass);
CREATE OR REPLACE FUNCTION db_dependents(p_tablename regclass)
RETURNS TABLE
(
	view_name regclass
	, depth integer
	, path regclass[]
)
AS
$BODY$
BEGIN
	RETURN QUERY
		WITH RECURSIVE views_list AS (
			SELECT
				c.oid::REGCLASS AS view_name
				, 0 as depth
				, ARRAY[c.oid::REGCLASS] as path
			FROM pg_class c
			WHERE c.oid::regclass = p_tablename
			--
			UNION ALL
			SELECT DISTINCT
				r.ev_class::REGCLASS AS view_name
				, views_list.depth + 1 as depth
				, views_list.path || (r.ev_class::REGCLASS) as path
			FROM pg_depend d
			INNER JOIN pg_rewrite r ON (r.oid = d.objid)
			INNER JOIN views_list ON (views_list.view_name = d.refobjid)
			WHERE d.refobjsubid != 0
		)
		SELECT * FROM views_list v
		where
			-- Exclude depth=0 which is actually the table name we were querying about
			v.depth > 0
		;
END; 
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100
ROWS 1000;

ALTER FUNCTION db_dependents(p_tablename regclass) OWNER TO adempiere;

























-- Function: altercolumn(name, name, name, character varying, character varying)

-- DROP FUNCTION altercolumn(name, name, name, character varying, character varying);

CREATE OR REPLACE FUNCTION altercolumn(tablename name, columnname name, datatype name, nullclause character varying, defaultclause character varying)
RETURNS void AS
$BODY$
declare
	command text;
	viewtext text[];
	viewname name[];
	dropviews name[];
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
			raise notice 'Changing datatype for %.%: ''%'' to ''%''', tablename, columnname, sqltype, datatype;
			
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

			raise notice '    Actual datatype change: to ''%''', datatype;
			command := 'alter table ' || lower(tablename) || ' alter column ' || lower(columnname) || ' type ' || lower(datatype);
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
		raise notice 'Changing default clause for %.%: ''%''', tablename, columnname, defaultclause;
		--
		if lower(defaultclause) = 'null' then
			command := 'alter table ' || lower(tablename) || ' alter column ' || lower(columnname) || ' drop default ';
		else
			command := 'alter table ' || lower(tablename) || ' alter column ' || lower(columnname) || ' set default ''' || defaultclause || '''';
		end if;
		execute command;
	end if;
   
	if nullclause is not null then
		raise notice 'Changing null clause for %.%: ''%''', tablename, columnname, nullclause;
		--
		if lower(nullclause) = 'not null' then
			command := 'alter table ' || lower(tablename) || ' alter column ' || lower(columnname) || ' set not null';
			execute command;
		elsif lower(nullclause) = 'null' then
			command := 'alter table ' || lower(tablename) || ' alter column ' || lower(columnname) || ' drop not null';
			execute command;
		end if;
	end if;
end;
$BODY$
LANGUAGE plpgsql VOLATILE COST 100;
ALTER FUNCTION altercolumn(name, name, name, character varying, character varying) OWNER TO adempiere;
