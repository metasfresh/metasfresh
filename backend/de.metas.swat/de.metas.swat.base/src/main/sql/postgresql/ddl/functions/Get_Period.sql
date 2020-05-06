DROP FUNCTION IF EXISTS report.Get_Period( p_C_Calendar_ID numeric, p_Date date );
CREATE OR REPLACE FUNCTION report.Get_Period( p_C_Calendar_ID numeric, p_Date date ) RETURNS numeric AS
$BODY$
SELECT
	C_Period_ID
FROM
	C_Period p 
	INNER JOIN C_Year y ON p.C_Year_ID = y.C_Year_ID
	INNER JOIN C_Calendar c ON y.C_Calendar_ID = c.C_Calendar_ID
WHERE
	c.C_Calendar_ID = $1
	AND p.StartDate <= $2
	AND p.EndDate >= $2
$BODY$
LANGUAGE sql STABLE;