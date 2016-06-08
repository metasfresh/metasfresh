DROP FUNCTION IF EXISTS KPI_Machine_Time_Percentage_Order_Lead_Time (IN PP_Order_ID numeric);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Machine_Time_Percentage_Order_Lead_Time (IN PP_Order_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Machine_Time_Percentage_Order_Lead_Time (IN PP_Order_ID numeric)
RETURNS TABLE 
(
Machine character varying,
Duarion numeric
)
AS
$$
SELECT 
x.Machine, 
x.Duration / Greatest(y.timeTotal, 1) as Duration
FROM
(
SELECT
s.name as Machine,
SUM(n.Duration) as Duration
FROM 
PP_Order_Node n
JOIN S_Resource s ON n.S_Resource_ID = s.S_Resource_ID
JOIN PP_Order_Workflow wf ON n.PP_Order_Workflow_ID = wf.PP_Order_Workflow_ID
JOIN PP_Order o ON wf.PP_Order_ID = o.PP_Order_ID
WHERE o.PP_Order_ID = $1
GROUP BY s.name
)x,
(
SELECT 
SUM(wf.Duration) as timeTotal
FROM 
PP_Order_Workflow wf 
JOIN PP_Order o ON wf.PP_Order_ID = o.PP_Order_ID
) y
$$
LANGUAGE sql STABLE;