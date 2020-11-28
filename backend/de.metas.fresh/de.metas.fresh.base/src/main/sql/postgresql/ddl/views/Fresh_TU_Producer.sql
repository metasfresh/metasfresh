-- View: report.fresh_tu_producer

-- DROP VIEW report.fresh_tu_producer;

CREATE OR REPLACE VIEW report.fresh_tu_producer AS
SELECT DISTINCT 
	hu.M_HU_ID, bp.C_BPartner_ID, bom_line_Count
FROM
	M_HU hu 
	
	/** Search for a PP_Order that is assigned to this HU */ 
	INNER JOIN M_HU_Assignment asgn ON asgn.M_HU_ID = hu.M_HU_ID
		AND asgn.AD_Table_ID = (SELECT get_table_id( 'PP_Order' ))
	INNER JOIN PP_Order mo ON asgn.Record_ID = mo.PP_Order_ID
	
	/** Get components */
	LEFT OUTER JOIN PP_Order_BOM mob ON mo.PP_Order_ID = mob.PP_Order_ID
	LEFT OUTER JOIN PP_Order_BOMLine mobl ON mob.PP_Order_BOM_ID = mobl.PP_Order_BOM_ID
		AND componentType IN ('CO', 'VA')
		
	/** Get number of components */
	LEFT OUTER JOIN (
		SELECT count(0) AS bom_line_Count , PP_Order_BOM_ID
		FROM PP_Order_BOMLine 
		WHERE componentType IN ('CO', 'VA')
		GROUP BY PP_Order_BOM_ID
	) moblc ON mob.PP_Order_BOM_ID = moblc.PP_Order_BOM_ID

	/** Get Component HUs */ 
	/* This join does not work currently, because the HU Assignments are not created but there is a workaround
	LEFT OUTER JOIN M_HU_Assignment bom_a ON mobl.PP_Order_BOMLine_ID = bom_a.Record_ID
		AND bom_a.AD_Table_ID = get_table_id( 'PP_Order_BOMLine' )
	*/ 
	-- Workaround start
	LEFT OUTER JOIN M_HU_Trx_Line hutl ON mobl.PP_Order_BOMLine_ID = hutl.Record_ID 
		AND hutl.AD_Table_ID = (SELECT get_table_id( 'PP_Order_BOMLine' ))
	LEFT OUTER JOIN M_HU_Trx_Line bom_a ON hutl.Parent_HU_Trx_line_ID = bom_a.M_HU_Trx_line_ID
	-- Workaround end
	LEFT OUTER JOIN M_HU c_hu ON bom_a.M_HU_ID = c_hu.M_HU_ID 
	
	/** Get Producer Attribute */ 
	LEFT OUTER JOIN M_HU_Attribute c_hua ON c_hu.M_HU_ID = c_hua.M_HU_ID
		AND c_hua.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = 'SubProducerBPartner')) -- Producer
	LEFT OUTER JOIN C_BPartner bp ON c_hua.valuenumber = bp.C_BPartner_ID;
COMMENT ON VIEW report.fresh_tu_producer IS 'This View returns the C_BPartner_ID of the Producer for all HUs that are assigined to a PP_Order (which are Top Level HUs, only) by finding their Component HU and retrieving the Producer Attribute from it';
