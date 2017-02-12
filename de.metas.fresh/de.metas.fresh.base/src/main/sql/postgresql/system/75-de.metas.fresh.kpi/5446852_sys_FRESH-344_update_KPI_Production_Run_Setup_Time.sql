DROP FUNCTION IF EXISTS KPI_Production_Run_Setup_Time (IN PP_Order_ID numeric);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Production_Run_Setup_Time (IN PP_Order_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Production_Run_Setup_Time (IN PP_Order_ID numeric)
RETURNS TABLE
(
Machine character varying,
ProjectedSetupTime numeric,
ActualSetupTime numeric
)
AS
$$
SELECT
x.Machine,
x.ProjectedSetupTime,
x.ActualSetupTime
FROM
(
SELECT
s.name as Machine,
SUM(n.SetupTime) as ProjectedSetupTime,
SUM(n.SetupTimeReal) as ActualSetupTime
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