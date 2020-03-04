DROP FUNCTION IF EXISTS ProductsTopRevenue( numeric, numeric, date,  date, numeric);

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
	Rang integer,
	ProductValue character varying,
	Name character varying,
	Revenue numeric,
	QtyOnHandStock numeric,
	SalesPercentProduct numeric
)
AS
$$

SELECT 
	ROW_NUMBER () OVER (ORDER BY  t.Revenue DESC NULLS LAST, t.ProductValue) :: integer as Rang,
	t.ProductValue,
	t.Name,
	t.Revenue,
	t.QtyOnHandStock,
	round((case when totals.totalRevenueAmt != 0 then (t.Revenue * 100) / totals.totalRevenueAmt else 0 end),2) AS SalesPercentProduct
	
	
FROM (
		SELECT 
			 p.value as ProductValue,
			 p.Name as Name,
			 getProductRevenue(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo) as Revenue,
			 getProductCurrentStock(p.M_Product_ID, p_AD_Client_ID, p_AD_Org_ID) as QtyOnHandStock
		FROM M_Product p
		
	) t,
	(
		SELECT COALESCE(getTotalRevenue(p_AD_Client_ID, p_AD_Org_ID, p_DateFrom, p_DateTo),0) as totalRevenueAmt
	) totals
ORDER BY Rang 
LIMIT (CASE WHEN p_Limit > 0 then p_Limit ELSE 999999999999999 END)

$$
LANGUAGE sql STABLE;