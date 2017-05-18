DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN record_id numeric, IN ad_table_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_POS_Root(IN record_id numeric, IN ad_table_id numeric)
RETURNS TABLE 
	(
	ad_org_id numeric,
	c_order_id integer,
	c_orderline_id integer,
	displayhu text
	)
AS
$$
SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InOutLine iol
			INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND rsa.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule rs1 ON rs1.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
												AND rs1.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND rs1.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
				AND rs1.C_OrderLine_ID = ol.C_OrderLine_ID AND iol.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
	C_OrderLine ol
WHERE
	ol.C_OrderLine_ID = $1 AND ol.isActive = 'Y' AND get_Table_ID('C_OrderLine') = $2

UNION
(SELECT
	ol.ad_org_id,
	ol.C_Order_ID::int,
	ol.C_OrderLine_ID::int,
	CASE
		WHEN
		EXISTS(
			SELECT 0
			FROM M_InOutLine iol
			INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID AND rsa.isActive = 'Y'
			INNER JOIN M_ReceiptSchedule rs1 ON rs1.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
												AND rs1.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND rs1.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
				AND rs1.C_OrderLine_ID = ol.C_OrderLine_ID AND iol.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu
FROM
M_ReceiptSchedule rs
JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_id AND ol.isActive = 'Y'	
WHERE
	rs.M_ReceiptSchedule_ID = $1 AND rs.isActive = 'Y' AND get_Table_ID('M_ReceiptSchedule') = $2

)
$$
LANGUAGE sql STABLE;