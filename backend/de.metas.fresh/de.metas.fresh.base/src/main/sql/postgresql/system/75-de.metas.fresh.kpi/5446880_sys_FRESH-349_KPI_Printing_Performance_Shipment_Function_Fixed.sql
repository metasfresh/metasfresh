DROP FUNCTION IF EXISTS KPI_Printing_Performance_Shipment_Function (IN StartDate Date, IN EndDate Date);

CREATE OR REPLACE FUNCTION KPI_Printing_Performance_Shipment_Function (IN StartDate Date, IN EndDate Date)
RETURNS TABLE (

	TimeAverage double precision
	)
	
	AS
$BODY$
SELECT
 	-- the difference in seconds
	avg 
	(
		
			 extract(epoch from (iopt.PrintJobInstructionUpdated::timestamp
			with time zone - iopt.starttime::timestamp with time
			zone))

	)  as  TimeAverage
	FROM RV_InOut_PrintingTime iopt
	JOIN m_inout io ON io.m_inout_id = iopt.M_InOut_ID


  WHERE 
  true AND 
  -- condition to only check the printed entries. (D = Done)
  --pji.status = 'D'::bpchar AND
  io.ISSOTrx = 'Y'

  and iopt.starttime::date >= $1 ::date and iopt.starttime ::date <= $2::date

$BODY$

LANGUAGE sql STABLE