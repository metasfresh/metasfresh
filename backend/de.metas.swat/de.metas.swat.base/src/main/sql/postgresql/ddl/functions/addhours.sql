-- Function: addhours(timestamp with time zone, numeric)

-- DROP FUNCTION addhours(timestamp with time zone, numeric);

CREATE OR REPLACE FUNCTION addhours(datetime timestamp with time zone, hours numeric)
  RETURNS timestamp with time zone AS
$BODY$

BEGIN

	if datetime is null or hours is null then

		return null;

	end if;



	return datetime::timestamp with time zone + (SELECT hours::text||':00:00')::time ;

END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION addhours(timestamp with time zone, numeric)
  OWNER TO adempiere;
