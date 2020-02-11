DROP FUNCTION IF EXISTS ProductsTopRevenue( numeric, numeric, date,  date, integer);

CREATE OR REPLACE FUNCTION ProductsTopRevenue 
( 
     p_AD_Client_ID numeric, 
     p_AD_Org_ID numeric ,
	 p_DateFrom date, 
	 p_DateTo date, 
	 p_Limit numeric
)

RETURNS TABLE 
(
	Rang bigint,
	ProductValue character varying,
	Description character varying,
	ProductRevenue numeric,
	QtyOnHandStock numeric,
	SalesPercentProduct numeric
)
AS
$$

SELECT 
	ROW_NUMBER () OVER (ORDER BY  t.ProductRevenue DESC NULLS LAST, t.ProductValue) as Rang,
	t.ProductValue,
	t.ProductName as Description,
	t.ProductRevenue,
	t.QtyOnHandStock,
	(case when totals.totalRevenueAmt != 0 then (t.ProductRevenue * 100) / totals.totalRevenueAmt else 0 end) AS SalesPercentProduct
	
	
FROM (
		SELECT 
			 p.value as ProductValue,
			 p.Name as ProductName,
			 getProductRevenue(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo) as ProductRevenue,
			 getProductCurrentStock(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID) as QtyOnHandStock
		FROM M_Product p
		
	) t,
	(
		SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo),0) as totalRevenueAmt
	) totals
	
LIMIT (CASE WHEN p_Limit > 0 then p_Limit ELSE 999999999999999 END)

$$
LANGUAGE sql STABLE;