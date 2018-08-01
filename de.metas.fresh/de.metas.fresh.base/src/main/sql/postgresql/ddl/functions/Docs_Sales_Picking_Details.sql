DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE
(
	Line Numeric (10,0),
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	qtyDelivered Numeric,
	qtyToDeliver Numeric,
	UOMSymbol Character Varying (10),
	StdPrecision Numeric (10,0),
	QtyPattern text,
	Description Character Varying,
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	upc character varying(30)
)
AS
$$

SELECT
	ol.line,
	COALESCE(pt.Name, p.name) AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	ol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	ss.QtyDelivered, 
	ss.QtyToDeliver,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.stdPrecision,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern,
	ol.Description,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
	p.upc
FROM
	C_OrderLine ol
	INNER JOIN C_Order o 			ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp			ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT ss.C_OrderLine_ID, ss.QtyDelivered, ss.QtyToDeliver
		FROM 	M_ShipmentSchedule ss
			INNER JOIN C_OrderLine ol ON ol.C_OrderLine_ID = ss.C_OrderLine_ID AND ol.isActive = 'Y'
		WHERE ol.C_Order_ID = $1 AND ss.isActive = 'Y'
	) ss ON ol.C_OrderLine_ID = ss.C_OrderLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN
	(
		SELECT String_Agg( DISTINCT name, E'\n' ORDER BY name ) AS Name, C_OrderLine_ID
		FROM
			(
				SELECT DISTINCT
					-- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
					COALESCE ( pifb.name, pi.name ) AS name,
					ol.C_OrderLine_ID
				FROM
					C_OrderLine ol
					-- Get PI directly from OrderLine (1 to 1) 
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON ol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
					-- Get PI from HU assignments (1 to n)
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'C_OrderLine' ) ))
						AND asgn.Record_ID = ol.C_OrderLine_ID AND asgn.isActive = 'Y'
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
					--
					LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
				WHERE
					piv.M_HU_PI_Version_ID != 101
					AND ol.C_Order_ID = $1 AND ol.isActive = 'Y'
			) x
		GROUP BY C_OrderLine_ID
	) pi ON ol.C_OrderLine_ID = pi.C_OrderLine_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	
	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'	
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = ol.C_UOM_ID 
		AND conv.C_UOM_To_ID = ol.Price_UOM_ID
		AND ol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( at.ai_value, ', ' ORDER BY Length(at.ai_value), at.ai_value ) AS Attributes, at.M_AttributeSetInstance_ID FROM Report.fresh_Attributes at
		JOIN C_OrderLine ol ON at.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID AND ol.isActive = 'Y'
		WHERE	at.at_value IN ('1000002', '1000001', '1000030', '1000015') -- Label, Herkunft, Aktionen, Marke (ADR)
			AND ol.C_Order_ID = $1
		GROUP BY	at.M_AttributeSetInstance_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY
	line

$$
LANGUAGE sql STABLE
;