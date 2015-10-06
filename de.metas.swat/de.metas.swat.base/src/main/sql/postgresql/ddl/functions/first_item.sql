
-- Character Varying
CREATE FUNCTION first_item (first_item character varying, second_item character varying)
	RETURNS character varying AS 
	$BODY$
		SELECT COALESCE( $1, $2 )
	$BODY$
	LANGUAGE sql VOLATILE
	COST 100;

ALTER FUNCTION first_item (first_item character varying, second_item character varying) OWNER TO adempiere;

COMMENT ON FUNCTION first_item(first_item character varying, second_item character varying) IS 
	'Returns the first of the two arguments. It is needed as a state transition function for the aggregation function first_agg (Coalesce can not be used as a state transition function)';

-- Numeric
CREATE FUNCTION first_item (first_item numeric, second_item numeric)
	RETURNS numeric AS 
	$BODY$
		SELECT COALESCE( $1, $2 )
	$BODY$
	LANGUAGE sql IMMUTABLE
	COST 100;
ALTER FUNCTION first_item (first_item character varying, second_item character varying) OWNER TO adempiere;
COMMENT ON FUNCTION first_item(first_item character varying, second_item character varying) IS 
	'Returns the first of the two arguments. It is needed as a state transition function for the aggregation function first_agg (Coalesce can not be used as a state transition function)';