DROP FUNCTION IF EXISTS KPI_Projected_VS_Actual_Production_Line (IN PP_Order_ID numeric);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Projected_VS_Actual_Production_Line (IN PP_Order_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Projected_VS_Actual_Production_Line (IN PP_Order_ID numeric)
RETURNS TABLE
(
Machine character varying,
ProjectedTime numeric,
ActualTime numeric
)
AS
$$
SELECT
x.Machine,
x.ProjectedTime,
x.ActualTime
FROM
(
SELECT
s.name as Machine,
SUM(n.Duration) as ProjectedTime,
SUM(n.DurationReal) as ActualTime
FROM
PP_Order_Node n
JOIN S_Resource s ON n.S_Resource_ID = s.S_Resource_ID
JOIN PP_Order_Workflow wf ON n.PP_Order_Workflow_ID = wf.PP_Order_Workflow_ID
JOIN PP_Order o ON wf.PP_Order_ID = o.PP_Order_ID
WHERE o.PP_Order_ID = $1
GROUP BY s.name
)x
$$
LANGUAGE sql STABLE;