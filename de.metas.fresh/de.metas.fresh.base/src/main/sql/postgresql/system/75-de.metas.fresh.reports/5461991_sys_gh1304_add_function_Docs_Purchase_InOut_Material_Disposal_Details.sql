--DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Details ( IN Record_ID numeric, IN AD_Language Character Varying (6))
RETURNS TABLE 
(
	name character varying,
	attributes text,
	huqty numeric,
	huname text,
	qty numeric,
	uomsymbol character varying(10),
	StdPrecision numeric(10,0)
)
AS
$$
SELECT 
	COALESCE(pt.name, p.name) AS name,
	a.attributes,
	SUM(iol.QtyEnteredTU) AS HUQty,
	pifb.name AS HUName,
	SUM(il.qtyinternaluse) AS qty,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.StdPrecision
	
FROM M_Inventory i

INNER JOIN M_InventoryLine il ON i.M_Inventory_ID = il.M_Inventory_ID
INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID AND iol.isActive = 'Y'
--INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'

INNER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
INNER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
LEFT OUTER JOIN C_UOM_Trl uomt	ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

-- Packing Instruction
LEFT JOIN M_HU_PI_Item_Product pifb ON il.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID

-- Attributes
LEFT OUTER JOIN
(
	SELECT	/** Jasper Servlet runs under linux, jasper client under windows (mostly). both have different fonts therefore, when
		  * having more than 2 lines, the field is too short to display all lines in the windows font to avoid this I add an extra
		  * line as soon as the attributes string has more than 15 characters (which is still very likely to fit in two lines)
		  */
		CASE WHEN Length(Attributes) > 15 THEN Attributes || E'\n' ELSE Attributes END AS Attributes, M_AttributeSetInstance_ID
	FROM	(
			SELECT 	String_agg ( att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, length(att.ai_value), att.ai_value) AS Attributes,
			att.M_AttributeSetInstance_ID
			FROM 	Report.fresh_Attributes att
			INNER JOIN M_InventoryLine il ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND il.isActive = 'Y'
			WHERE 	-- Label, Herkunft, Aktionen, Marke (ADR), HU_BestBeforeDate, MHD, M_Material_Tracking_ID
				att.at_Value IN ('1000002', '1000001', '1000030', '1000015', 'HU_BestBeforeDate', '1000021', 'M_Material_Tracking_ID')
				/* currently those flags are set to be correct for purchase invoices. we need something
				 * more flexible for all kinds of documents
				 * att.at_IsAttrDocumentRelevant = 'Y' */
				  AND il.M_Inventory_ID = $1
			GROUP BY	att.M_AttributeSetInstance_ID
		) x
) a ON il.M_AttributeSetInstance_ID = a.M_AttributeSetInstance_ID

WHERE i.M_Inventory_ID = $1 AND i.isActive = 'Y'

GROUP BY 
	p.name, pt.name,
	a.attributes,
	pifb.Name,
	uomt.UOMSymbol, uom.UOMSymbol,uom.StdPrecision
$$
LANGUAGE sql STABLE
;	