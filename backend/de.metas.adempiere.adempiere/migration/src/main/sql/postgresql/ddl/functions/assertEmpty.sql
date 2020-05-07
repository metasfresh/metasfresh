drop function if exists assertEmpty(p_TableNameOrSql varchar, p_ErrorMsg text);

create or replace function assertEmpty(p_TableNameOrSql varchar, p_ErrorMsg text default null)
returns void
as
$BODY$
declare
	v_sql text;
	v_Count integer;
	v_ErrorMsg text;
begin
	--
	-- Build the test SQL
	if (p_TableNameOrSql ilike 'select%') then
		v_sql := 'select count(1) from ('||p_TableNameOrSql||') t';
	else
		v_sql := 'select count(1) from '||p_TableNameOrSql||' t';
	end if;
	raise notice 'Asserting no results for SQL: %', v_sql;

	if (p_ErrorMsg is null or length(p_ErrorMsg) = 0) then
		v_ErrorMsg := 'empty result';
	else
		v_ErrorMsg := p_ErrorMsg;
	end if;

	--
	-- Count the records
	execute v_sql into v_Count;

	if (v_Count = 0) then
		raise notice 'Asumption OK: "%"', v_ErrorMsg;
		return;
	end if;

	raise exception 'Asumption failed: "%" but found % records', v_ErrorMsg, v_Count;
	
end;
$BODY$
LANGUAGE plpgsql
COST 100;
