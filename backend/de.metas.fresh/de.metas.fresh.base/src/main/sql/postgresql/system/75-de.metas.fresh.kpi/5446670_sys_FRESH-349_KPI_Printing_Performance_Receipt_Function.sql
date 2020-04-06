DROP FUNCTION IF EXISTS KPI_Printing_Performance_Receipt_Function (IN StartDate Date, IN EndDate Date);

CREATE OR REPLACE FUNCTION KPI_Printing_Performance_Receipt_Function (IN StartDate Date, IN EndDate Date)
RETURNS TABLE (

	TimeAverage double precision
	)
	
	AS
$BODY$
SELECT
 	-- the difference in seconds
	avg 
	(
		
			 extract(epoch from (pji.Updated::timestamp
			with time zone - pji.created::timestamp with time
			zone)) 

	) as  TimeAverage
   FROM c_print_job_instructions pji
   JOIN c_print_job_line pjl ON pjl.c_print_job_id = pji.c_print_job_id
   JOIN c_printing_queue pq ON pq.c_printing_queue_id = pjl.c_printing_queue_id
   JOIN ad_archive a ON a.ad_archive_id = pq.ad_archive_id

   JOIN m_inout io ON io.m_inout_id = a.record_id AND a.ad_table_id = get_table_id('M_InOut')


  WHERE 
  true AND 
  -- condition to only check the printed entries. (D = Done)
  --pji.status = 'D'::bpchar AND
  io.ISSOTrx = 'N'

  and pji.created::date >= $1::date and pji.created::date <= $2::date

$BODY$

LANGUAGE sql STABLE