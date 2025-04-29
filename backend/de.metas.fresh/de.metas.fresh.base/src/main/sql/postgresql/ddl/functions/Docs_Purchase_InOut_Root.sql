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
			FROM c_orderline ol1
			join c_order o ON ol1.c_order_id = o.c_order_id and o.isactive = 'Y'
			join m_inout io2 ON o.c_order_id = io2.c_order_id and io2.isactive = 'Y'
			join m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id and iol2.isactive = 'Y'

			INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
			INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
			WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
				AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID AND ol1.isActive = 'Y'
		)
		THEN 'Y'
		ELSE 'N'
	END as displayhu,
	ol.C_Order_ID::int,
	(
		SELECT	MAX(io.movementdate)::date AS Movementdate
		FROM	M_ReceiptSchedule rs
			INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
			INNER JOIN M_InOutLine siol ON rsa.M_InOutLine_ID = siol.M_InOutLine_ID
			INNER JOIN M_InOut io ON siol.M_InOut_ID = io.M_InOut_ID
		WHERE 	rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' )) AND ol.C_OrderLine_ID = rs.C_OrderLine_ID
	) AS MovementDate
FROM
	-- All In Out Lines directly linked to the order
	M_InOutLine iol
	INNER JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
	INNER JOIN M_ReceiptSchedule rs ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
		AND rs.AD_Table_ID = (SELECT Get_Table_ID( 'C_OrderLine' ))
	INNER JOIN C_OrderLine ol ON rs.C_OrderLine_ID = ol.C_OrderLine_ID
WHERE
	iol.M_InOut_ID = record_id

UNION
DISTINCT

SELECT ol.AD_Org_ID,
       ol.C_OrderLine_ID::int,
       CASE
           WHEN
               EXISTS(SELECT 0
                      FROM c_orderline ol1
                               JOIN c_order o ON ol1.c_order_id = o.c_order_id AND o.isactive = 'Y'
                               JOIN m_inout io2 ON o.c_order_id = io2.c_order_id AND io2.isactive = 'Y'
                               JOIN m_inoutLine iol2 ON io2.m_inout_id = iol2.m_inout_id AND iol2.isactive = 'Y'

                               INNER JOIN M_Product p ON iol2.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
                               INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
                      WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol2.AD_Client_ID, iol2.AD_Org_ID)
                        AND ol.C_OrderLine_ID = ol1.C_OrderLine_ID
                        AND ol1.isActive = 'Y')
               THEN 'Y'
               ELSE 'N'
       END                                             AS displayhu,
       ol.C_Order_ID::int,
       (SELECT MAX(io.movementdate)::date AS Movementdate
        FROM M_InOut io
                 INNER JOIN M_InOutLine siol ON io.m_inout_id = siol.m_inout_id
        WHERE ol.C_OrderLine_ID = siol.C_OrderLine_ID) AS MovementDate
FROM
    M_InOutLine iol
        INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
WHERE iol.M_InOut_ID = record_id

LIMIT 1
$$
LANGUAGE sql STABLE;