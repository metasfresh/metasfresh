/*
 "if_min" aggregate.
 Takes 2 parameters:
 * element - the element to be used for checking the minimum
 * value - value to be retained when the element is minimum
 */

drop aggregate if exists if_min(anyelement, anyelement);
drop function if exists if_min_sfunc(anyarray, anyelement, anyelement);
drop function if exists if_min_finalfunc(agg_state anyarray);

create function if_min_sfunc(agg_state anyarray, element anyelement, value anyelement)
returns anyarray
immutable
language plpgsql
as $$
declare
	curr_min element%type;
	curr_result value%type;
begin
	curr_min := least(agg_state[1], element);

	-- Consider the given "value" as current result if the given "element" is minimum
	if(curr_min = element)
	then
		curr_result := value;
	else
		curr_result := agg_state[2];
	end if;

	return array[curr_min, curr_result];
end;
$$;

create function if_min_finalfunc(agg_state anyarray)
returns anyelement
immutable
language plpgsql
as $$
begin
	return agg_state[2];
end;
$$;

create aggregate if_min(anyelement, anyelement)
(
    sfunc = if_min_sfunc,
    stype = anyarray,
    finalfunc = if_min_finalfunc
);
COMMENT ON AGGREGATE if_min(anyelement, anyelement) IS 'Retains the second argument in case the first argument is the minimum.';


-- select if_min_sfunc(array[now()+0, null], now()-1, now()+100);

