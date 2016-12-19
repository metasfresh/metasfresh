/**
 * NOTE: This view affects following Jasper documents: Receipt, Shipment, Sales Invoice, ShippedInOuts (Rüstliste)
 * please do Not change if you can't make sure they all still work.
 */

-- View: Report.fresh_InOutLine_PI_V

-- DROP VIEW Report.fresh_InOutLine_PI_V;

CREATE OR REPLACE VIEW Report.fresh_InOutLine_PI_V AS 
SELECT DISTINCT 
	COALESCE ( pi.name, pifb.name ) AS name, -- Packing instruction name
	iol.M_InOutLine_ID,
	COALESCE ( pi.M_HU_PI_Item_Product_ID, pifb.M_HU_PI_Item_Product_ID ) AS M_HU_PI_Item_Product_ID
FROM 	
	M_InOutLine iol
	/** Workaround: customer doesn't use verdichtung which means there are no HUs assigned to the In Out Lines. 
	 * Therefore Sales In Outs where changed to reference the Packing instruction in their lines. 
	 * NOTE: Receipt lines still work the old way. there's a fallback for this 
	 */
	LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
	/** Fallback: Keep original way for Receipt in outs and for the case that customer starts using verdichtung.
	 * The original way gets the packing instruction directly from the assigned HUs
	 */
	LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'M_InOutLine' ) ))
		AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y'
	LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
	LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
WHERE
	iol.isActive = 'Y' AND
	COALESCE ( pi.name, pifb.name ) != 'VirtualPI'
;


COMMENT ON VIEW Report.fresh_InOutLine_PI_V IS 'Lists all InOutline_IDs with their Packing instruction name';
