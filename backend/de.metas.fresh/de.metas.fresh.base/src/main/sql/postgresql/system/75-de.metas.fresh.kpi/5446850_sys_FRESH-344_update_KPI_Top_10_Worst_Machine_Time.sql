DROP FUNCTION IF EXISTS KPI_Top_10_Worst_Machine_Time (IN DateStart Date, IN DateFinish Date);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Top_10_Worst_Machine_Time (IN DateStart Date, IN DateFinish Date);


CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Top_10_Worst_Machine_Time (IN DateStart Date, IN DateFinish Date)
RETURNS TABLE
(
Machine character varying,
WorstTime numeric
)
AS
$$
SELECT
s.name AS Machine,
MAX(n.Duration) AS WorstTime
FROM
PP_Order_Node n
JOIN PP_Order_Workflow wf ON n.PP_Order_Workflow_ID = wf.PP_Order_Workflow_ID
JOIN PP_Order o ON wf.PP_Order_ID = o.PP_Order_ID
JOIN S_Resource s ON n.S_Resource_ID = s.S_Resource_ID
WHERE
o.DateOrdered >= $1 and o.DAteOrdered <= $2
GROUP BY s.Name
ORDER BY MAX(n.Duration) DESC
LIMIT 10
$$
LANGUAGE sql STABLE;