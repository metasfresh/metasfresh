-- Character Varying
CREATE AGGREGATE first_agg (character varying) (
	SFUNC = first_item,
	STYPE = character varying
);

ALTER AGGREGATE first_agg (character varying) OWNER TO adempiere;

COMMENT ON AGGREGATE first_agg (character varying) IS 
	'Returns the first value that is not null. Should not be used without order by statement';

-- Numeric
CREATE AGGREGATE first_agg (numeric) (
	SFUNC = first_item,
	STYPE = numeric
);
ALTER AGGREGATE first_agg (numeric) OWNER TO adempiere;
COMMENT ON AGGREGATE first_agg (numeric) IS 
	'Returns the first value that is not null. Should not be used without order by statement';