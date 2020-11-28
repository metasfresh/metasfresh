DROP FUNCTION IF EXISTS report.Get_Successor_Period (IN Source_Period_ID numeric);

CREATE OR REPLACE FUNCTION report.Get_Successor_Period (IN Source_Period_ID numeric) RETURNS numeric AS
$BODY$
SELECT
	COALESCE( tp1.C_Period_ID, tp2.C_Period_ID )
FROM
	C_Period sp
	LEFT OUTER JOIN C_Period tp1 ON sp.C_Year_ID = tp1.C_Year_ID AND tp1.PeriodNo = sp.PeriodNo + 1
	LEFT OUTER JOIN C_Year sy ON sp.C_Year_ID = sy.C_Year_ID
	LEFT OUTER JOIN C_Year ty ON sy.C_Calendar_ID = ty.C_Calendar_ID AND ty.FiscalYear::Integer = sy.FiscalYear::Integer + 1
	LEFT OUTER JOIN C_Period tp2 ON tp2.C_Period_ID = ( SELECT First_Agg(C_Period_ID::text ORDER BY PeriodNo)::numeric FROM C_Period WHERE C_Year_ID = ty.C_Year_ID )
WHERE
	sp.C_Period_ID = $1
$BODY$
LANGUAGE sql STABLE;
ALTER FUNCTION report.Get_Successor_Period (IN Source_Period_ID numeric) OWNER TO adempiere;