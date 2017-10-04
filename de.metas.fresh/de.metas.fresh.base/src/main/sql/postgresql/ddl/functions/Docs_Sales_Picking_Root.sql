
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Root ( IN Record_ID numeric, IN preparationdate_st date, IN preparationdate_end date, IN AD_Org_ID numeric);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Root ( IN Record_ID numeric, IN preparationdate_st date, IN preparationdate_end date, IN AD_Org_ID numeric)
RETURNS TABLE 
(
	AD_User_ID numeric(10,0),
	AD_Org_ID numeric(10,0),
	C_Order_ID integer,
	preparationdate timestamp with time zone,
	displayhu text
)
AS
$$SELECT
	o.ad_user_id,
	o.ad_org_id,
	o.c_order_id::integer,
	o.preparationdate,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_OrderLine ol1
			INNER JOIN M_Product p ON ol1.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol1.AD_Client_ID, ol1.AD_Org_ID)
				AND o.C_Order_ID = ol1.C_Order_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_Order o
	
WHERE
	1=1 
	AND	(CASE WHEN $1 IS NOT NULL THEN o.C_Order_ID = $1 ELSE TRUE END)
	AND (CASE WHEN $2 IS NOT NULL THEN o.preparationdate::date >= $2 ELSE TRUE END)
	AND (CASE WHEN $3 IS NOT NULL THEN o.preparationdate::date <= $3 ELSE TRUE END)
	AND o.ad_org_id = $4
$$
LANGUAGE sql STABLE
;

