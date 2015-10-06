DROP FUNCTION IF EXISTS report.fresh_Get_Predecessor_Period (IN Source_Period_ID numeric);

CREATE FUNCTION report.fresh_Get_Predecessor_Period (IN Source_Period_ID numeric) RETURNS numeric AS
$BODY$
BEGIN
	RETURN report.Get_Predecessor_Period (Source_Period_ID);
END;
$BODY$
LANGUAGE plpgsql STABLE;

ALTER FUNCTION report.fresh_Get_Predecessor_Period (IN Source_Period_ID numeric) OWNER TO adempiere;
