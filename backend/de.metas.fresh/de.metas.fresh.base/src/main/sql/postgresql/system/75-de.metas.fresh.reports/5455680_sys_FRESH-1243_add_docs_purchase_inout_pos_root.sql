DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN record_id numeric, IN ad_table_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN record_id numeric, IN ad_table_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_order_id integer,
	c_orderline_id integer
	)
AS
$$
SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int
FROM
	C_OrderLine ol
WHERE
	ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y' AND get_Table_ID('C_OrderLine') = $2

UNION
(SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int
FROM
M_ReceiptSchedule rs
INNER JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_id AND ol.isActive = 'Y'	
WHERE
	rs.M_ReceiptSchedule_ID = $1 AND rs.isActive = 'Y' AND get_Table_ID('M_ReceiptSchedule') = $2

)
$$
LANGUAGE sql STABLE;