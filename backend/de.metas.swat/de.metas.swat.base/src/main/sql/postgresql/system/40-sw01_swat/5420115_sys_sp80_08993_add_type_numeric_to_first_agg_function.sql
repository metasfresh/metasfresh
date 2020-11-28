--
-- First Agg for Character Varying

-- first item - function to be used as a state transition function in aggregate (Coalesce is not allowed as a state transition function in aggregate)
CREATE OR REPLACE FUNCTION first_item (first_item character varying, second_item character varying)
	RETURNS character varying AS 
	$BODY$
		SELECT COALESCE( $1, $2 )
	$BODY$
	LANGUAGE sql IMMUTABLE
	COST 100;
ALTER FUNCTION first_item (first_item character varying, second_item character varying) OWNER TO adempiere;

COMMENT ON FUNCTION first_item(first_item character varying, second_item character varying) IS 
	'Returns the first of the two arguments. It is needed as a state transition function for the aggregation function first_agg (Coalesce can not be used as a state transition function)';

-- First Agg
DROP AGGREGATE IF EXISTS first_agg (character varying);
CREATE AGGREGATE first_agg (character varying) (
	SFUNC = first_item,
	STYPE = character varying
);
ALTER AGGREGATE first_agg (character varying) OWNER TO adempiere;
COMMENT ON AGGREGATE first_agg (character varying) IS 
	'Returns the first value that is not null. Should not be used without order by statement';

	
--
-- First Agg for numeric


-- First item - function to be used in aggregate (Coalesce is not allowed as in aggregate)
CREATE OR REPLACE FUNCTION first_item (first_item numeric, second_item numeric)
	RETURNS numeric AS 
	$BODY$
		SELECT COALESCE( $1, $2 )
	$BODY$
	LANGUAGE sql IMMUTABLE
	COST 100;
	
ALTER FUNCTION first_item (first_item numeric, second_item numeric) OWNER TO adempiere;

COMMENT ON FUNCTION first_item(first_item numeric, second_item numeric) IS 
	'Returns the first of the two arguments. It is needed as a state transition function for the aggregation function first_agg (Coalesce can not be used as a state transition function)';

-- First Agg
DROP AGGREGATE IF EXISTS first_agg (numeric);
CREATE AGGREGATE first_agg (numeric) (
	SFUNC = first_item,
	STYPE = numeric
);
ALTER AGGREGATE first_agg (numeric) OWNER TO adempiere;
COMMENT ON AGGREGATE first_agg (numeric) IS 
	'Returns the first value that is not null. Should not be used without order by statement';


