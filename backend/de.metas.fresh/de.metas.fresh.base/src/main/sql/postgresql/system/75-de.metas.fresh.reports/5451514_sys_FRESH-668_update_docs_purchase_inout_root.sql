DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Root(IN record_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_orderline_id integer,
	displayhu text,
	c_order_id integer,
	movementdate date
	)
AS
$$	
SELECT
	ol.AD_Org_ID,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM C_OrderLine ol1
			INNER JOIN M_Product p ON ol1.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = ((SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID' AND isActive = 'Y'))
				AND ol.C_Order_ID = ol1.C_Order_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	ol.C_Order_ID::int,
	(
		SELECT	MAX(io.movementdate)::date AS Movementdate
		FROM	M_ReceiptSchedule rs
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID AND rsa.isActive = 'Y'
			INNER JOIN M_InOutLine siol ON rsa.M_InOutLine_ID = siol.M_InOutLine_ID AND siol.isActive = 'Y'
			INNER JOIN M_InOut io ON siol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
		WHERE 	rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND ol.C_OrderLine_ID = rs.C_OrderLine_ID AND rs.isActive = 'Y'
	) AS MovementDate
FROM
	-- All In Out Lines directly linked to the order
	M_InOutLine iol
	INNER JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND rsa.isActive = 'Y'
	INNER JOIN M_ReceiptSchedule rs ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
		AND rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND rs.isActive = 'Y'
	INNER JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
WHERE
	iol.M_InOut_ID = $1 AND iol.isActive = 'Y'
LIMIT 1
$$
LANGUAGE sql STABLE;
