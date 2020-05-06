DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Root(IN record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	docstatus character(2),
	printname character varying(60),
	c_order_id integer
	)
AS
$$	
SELECT
	r.AD_Org_ID,
	r.DocStatus,
	r.PrintName,
	r.C_Order_ID::int
FROM report.RV_C_Order_MFGWarehouse_Report_Header r
WHERE 
		 r.C_Order_MFGWarehouse_Report_ID = $1
LIMIT 1

$$
LANGUAGE sql STABLE;