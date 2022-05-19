
CREATE OR REPLACE FUNCTION report.fresh_qty_statistics_report_kg_week
(
    IN C_Year_ID numeric,
    IN week integer,
    IN issotrx character varying,
    IN C_Activity_ID numeric,
    IN M_Product_ID numeric,
    IN M_Product_Category_ID numeric,
    IN M_AttributeSetInstance_ID numeric,
    IN convert_to_kg character varying,
    IN ad_org_id numeric,
    IN AD_Language Character Varying (6)
)
    RETURNS SETOF report.fresh_qty_statistics_report_kg_week AS
$$

with statistics AS
    (select * from report.fresh_statistics_kg_week ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10))

SELECT
    *, 1 AS UnionOrder
FROM
    statistics
UNION ALL
SELECT
    pc_name, null AS P_name, null AS P_value, UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    SUM( Week1Sum ) AS Week1Sum, SUM( Week2Sum ) AS Week2Sum, SUM( Week3Sum ) AS Week3Sum,
    SUM( Week4Sum ) AS Week4Sum, SUM( Week5Sum ) AS Week5Sum, SUM( Week6Sum ) AS Week6Sum,
    SUM( Week7Sum ) AS Week7Sum, SUM( Week8Sum ) AS Week8Sum, SUM( Week9Sum ) AS Week9Sum,
    SUM( Week10Sum ) AS Week10Sum, SUM( Week11Sum ) AS Week11Sum, SUM( Week12Sum ) AS Week12Sum,
    SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code,
    2 AS UnionOrder
FROM
    statistics
GROUP BY
    pc_name, UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code
UNION ALL
SELECT
    null, null, null, UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    SUM( Week1Sum ) AS Week1Sum, SUM( Week2Sum ) AS Week2Sum, SUM( Week3Sum ) AS Week3Sum,
    SUM( Week4Sum ) AS Week4Sum, SUM( Week5Sum ) AS Week5Sum, SUM( Week6Sum ) AS Week6Sum,
    SUM( Week7Sum ) AS Week7Sum, SUM( Week8Sum ) AS Week8Sum, SUM( Week9Sum ) AS Week9Sum,
    SUM( Week10Sum ) AS Week10Sum, SUM( Week11Sum ) AS Week11Sum, SUM( Week12Sum ) AS Week12Sum,
    SUM( TotalSum ) AS TotalSum, SUM( TotalAmt ) AS TotalAmt,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code,
    3 AS UnionOrder
FROM
    statistics
GROUP BY
    UOMSymbol,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code
UNION ALL
SELECT
    null, null, null, null,
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    null, null, null, null, null, null, null, null, null, null, null, null,
    null, SUM( TotalAmt ) AS TotalAmt,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code,
    4 AS UnionOrder
FROM
    statistics
GROUP BY
    Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9, Col10, Col11, Col12,
    StartDate, EndDate,
    param_Activity, param_product, param_Product_Category, Param_Attributes, ad_org_id, iso_code
HAVING
        count(DISTINCT UOMSymbol) > 1
ORDER BY
    pc_name NULLS LAST, UnionOrder, p_name
$$
    LANGUAGE sql STABLE;