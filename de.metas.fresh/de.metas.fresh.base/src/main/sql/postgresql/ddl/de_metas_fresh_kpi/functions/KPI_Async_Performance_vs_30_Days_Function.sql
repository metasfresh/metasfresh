DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Async_Performance_vs_30_Days_Function (IN CurrentDate date) ;


CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Async_Performance_vs_30_Days_Function (IN CurrentDate date) 
RETURNS TABLE 
(
	CurrentPerformance numeric,
	AVGPerformance numeric
)
AS
$$


SELECT



COALESCE(x.currentperformance, 0), SUM(y.dayAvg) / 30 AS avgperformance
FROM 

(

SELECT SUM(lastdurationmillis)/(GREATEST(COUNT(C_Queue_WorkPackage_ID),1)) AS currentPerformance
from C_Queue_WorkPackage

WHERE date_trunc('day',  laststarttime) = $1
) AS x,
(
SELECT
SUM(lastdurationmillis)/(GREATEST(COUNT(C_Queue_WorkPackage_ID),1)) AS dayAvg
from C_Queue_WorkPackage

WHERE date_trunc('day',  laststarttime) >=  ((date ($1) - interval '1 day') - interval '29 days')
	AND date_trunc('day',  laststarttime) <$1

--AND EXTRACT(DOW FROM  laststarttime) NOT IN(0,6)
--AND  NOT EXISTS (SELECT Date1 from C_NonBusinessDay where date1 = date_trunc('day',  laststarttime) )



GROUP BY date_trunc('day',  laststarttime)

ORDER BY date_trunc('day',  laststarttime)

)y


GROUP BY x.currentPerformance


$$
LANGUAGE sql STABLE;

