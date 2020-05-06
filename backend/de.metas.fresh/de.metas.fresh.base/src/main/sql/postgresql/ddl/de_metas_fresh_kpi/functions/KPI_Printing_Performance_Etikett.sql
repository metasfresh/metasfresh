DROP FUNCTION IF EXISTS KPI_Printing_Performance_Etikett_Function (IN StartDate Date, IN EndDate Date);


DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Printing_Performance_Etikett_Function (IN StartDate Date, IN EndDate Date);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Printing_Performance_Etikett_Function (IN StartDate Date, IN EndDate Date)
RETURNS TABLE (

	TimeAverage double precision
	)
	
	AS
$BODY$
SELECT
 	-- the difference in seconds
	avg 
	(
		
			 extract(epoch from (ept.PrintJobInstructionUpdated::timestamp
			with time zone - ept.starttime::timestamp with time
			zone))

	)  as  TimeAverage
   FROM RV_Etikett_PrintingTime ept
   

  WHERE 

	ept.starttime::date >= $1 ::date and ept.starttime ::date <= $2::date


$BODY$

LANGUAGE sql STABLE