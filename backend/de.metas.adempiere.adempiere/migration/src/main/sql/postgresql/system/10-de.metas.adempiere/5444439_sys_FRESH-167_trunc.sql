-- Function: trunc(timestamp with time zone, character varying)

-- DROP FUNCTION trunc(timestamp with time zone, character varying);

CREATE OR REPLACE FUNCTION trunc(
    datetime timestamp with time zone,
    format character varying)
  RETURNS date AS
$BODY$
BEGIN
	IF datetime is null THEN
		RETURN NULL;
	ELSIF format = 'Q' THEN
		RETURN CAST(DATE_Trunc('quarter',datetime) as DATE);
	ELSIF format = 'Y' or format = 'YEAR' THEN
		RETURN CAST(DATE_Trunc('year',datetime) as DATE);
	ELSIF format = 'MM' or format = 'MONTH' THEN
		RETURN CAST(DATE_Trunc('month',datetime) as DATE);
	ELSIF format = 'WW' THEN
		RETURN CAST(DATE_Trunc('week',datetime) as DATE);
	ELSIF format = 'DD' THEN
		RETURN CAST(DATE_Trunc('day',datetime) as DATE);
	ELSIF format = 'DY' THEN
		RETURN CAST(DATE_Trunc('day',datetime) as DATE);
	ELSE
		RETURN CAST(datetime AS DATE);
	END IF;
END;
$BODY$
LANGUAGE plpgsql IMMUTABLE
COST 100;
