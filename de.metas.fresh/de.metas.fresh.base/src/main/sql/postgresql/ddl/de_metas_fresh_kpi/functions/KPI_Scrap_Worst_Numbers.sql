DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Scrap_Worst_Numbers (IN DateStart Date, IN DateFinish Date);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Scrap_Worst_Numbers (IN DateStart Date, IN DateFinish Date)
RETURNS TABLE
(
DocumentNo character varying,
ScrapDifference numeric
)
AS
$$
SELECT
x.DocumentNo,
x.ScrapDifference
FROM
(
SELECT
o.DocumentNo,
Greatest((sum(cc.ScrappedQty) - sum(bl.Scrap)), 0) as ScrapDifference
FROM
PP_Order_BOMLine bl
JOIN PP_Order o on bl.PP_Order_ID = o.PP_Order_ID
JOIN PP_Cost_Collector cc on bl.PP_Order_BOMLine_ID = cc.PP_Order_BOMLine_ID
WHERE o.DateOrdered >= $1 and o.DateOrdered <= $2
GROUP BY o.PP_Order_ID
Order by (sum(cc.ScrappedQty) - sum(bl.Scrap)) DESC
)x
Limit 10
$$
LANGUAGE sql STABLE;