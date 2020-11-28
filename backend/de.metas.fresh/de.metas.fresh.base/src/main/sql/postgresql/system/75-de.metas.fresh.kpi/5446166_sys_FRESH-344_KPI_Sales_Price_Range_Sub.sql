DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Sales_Price_Range_Sub (IN startdate DATE, IN enddate DATE);
CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Sales_Price_Range_Sub (IN startdate DATE, IN enddate DATE)
RETURNS TABLE
(
	m_product_id numeric,
	product_value character varying,
	product_name character varying,
	max_price numeric,
	min_price numeric,
	averagePrice numeric
)
AS
$$
SELECT m_product_id, value, name, max(priceactual) as max_price, min(priceactual) as min_price, currentCostPrice
FROM
(
SELECT 
p.m_product_id,p.value, p.name, 
CASE WHEN 
	i.c_currency_id != (select c_currency_id from c_currency where iso_code='CHF')
	THEN
	currencyconvert(il.priceactual, i.c_currency_id, (select c_currency_id from c_currency where iso_code='CHF'), $2, null,i.ad_client_id,i.ad_org_id)
	ELSE
	il.priceactual
END as priceactual,
c.currentCostPrice
FROM
M_Product p

INNER JOIN C_InvoiceLine il ON p.M_Product_ID = il.M_Product_ID 
INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
INNER JOIN M_Cost c ON p.M_Product_ID = c.M_Product_ID AND c.M_CostElement_ID = (SELECT M_CostElement_ID FROM M_CostElement WHERE costingmethod='I') --'A'

WHERE 
	i.issotrx='Y' AND 
	il.priceactual>0 AND i.DocStatus IN ('CO','CL') AND
	i.DateInvoiced >= $1 AND i.DateInvoiced <= $2 
)i
GROUP BY m_product_id,value, name, currentCostPrice
$$
LANGUAGE sql STABLE;
