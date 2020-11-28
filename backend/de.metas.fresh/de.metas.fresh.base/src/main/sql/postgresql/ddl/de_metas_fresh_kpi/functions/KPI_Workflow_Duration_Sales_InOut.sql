--DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Workflow_Duration_Sales_InOut (IN startdate DATE, IN enddate DATE, IN laststartdate DATE, in lastenddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Workflow_Duration_Sales_InOut (IN startdate DATE, IN enddate DATE, IN laststartdate DATE, in lastenddate DATE)
RETURNS TABLE 
(
table_id numeric,
record_id numeric,
created timestamp with time zone,
duration interval,
duration_perline numeric(10,2),
difference_duration numeric(10,2),
difference_duration_perline numeric(10,2)
)
AS
$$
SELECT 
table_id,
record_id,
created,
duration,
(select extract (second from duration_perline))::numeric(10,2) AS duration_perline,
(select extract (second from (a.average_duration - (select average_duration from de_metas_fresh_kpi.KPI_Workflow_Duration_Sales_InOut_Sub(coalesce($3,$1-($2-$1)),coalesce($4,$2-($2-$1))) limit 1 ))))::numeric(10,2) AS difference_duration,
(select extract (second from (a.average_duration_perline - (select average_duration_perline from de_metas_fresh_kpi.KPI_Workflow_Duration_Sales_InOut_Sub(coalesce($3,$1-($2-$1)),coalesce($4,$2-($2-$1))) limit 1 ))))::numeric(10,2) as difference_duration_perline
FROM de_metas_fresh_kpi.KPI_Workflow_Duration_Sales_InOut_Sub($1,$2) a
ORDER BY duration_perline
$$
LANGUAGE sql STABLE;