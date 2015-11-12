-- View: report.halbfabrikat_product_origin

-- DROP VIEW report.halbfabrikat_product_origin;

CREATE OR REPLACE VIEW report.halbfabrikat_product_origin AS 

SELECT DISTINCT
	tu.M_HU_ID, 
	TRIM( substring( p.name, 1, 4 ) || ' ' || COALESCE( substring( av.name, 1, 3 ), '' ) ) AS Product_Origin
FROM
	M_HU tu
	/** Get component HUs and their "Herkunft" attribute */
	INNER JOIN M_HU_Assignment hf_a ON hf_a.M_HU_ID = tu.M_HU_ID
		AND hf_a.AD_Table_ID = get_table_id( 'PP_Order' )
	INNER JOIN PP_Order mo ON hf_a.Record_ID = mo.PP_Order_ID
	LEFT OUTER JOIN PP_Order_BOM mob ON mo.PP_Order_ID = mob.PP_Order_ID
	LEFT OUTER JOIN PP_Order_BOMLine mobl ON mob.PP_Order_BOM_ID = mobl.PP_Order_BOM_ID
		AND componentType IN ('CO', 'VA')
	/* This join does not work currently, because the HU Assignments are not created but there is a workaround
	LEFT OUTER JOIN M_HU_Assignment bom_a ON mobl.PP_Order_BOMLine_ID = bom_a.Record_ID
		AND bom_a.AD_Table_ID = get_table_id( 'PP_Order_BOMLine' )
	*/ 
	-- Workaround start
	LEFT OUTER JOIN M_HU_Trx_Line hutl ON mobl.PP_Order_BOMLine_ID = hutl.Record_ID 
		AND hutl.AD_Table_ID = get_table_id( 'PP_Order_BOMLine' )
	LEFT OUTER JOIN M_HU_Trx_Line bom_a ON hutl.Parent_HU_Trx_line_ID = bom_a.M_HU_Trx_line_ID
	-- Workaround end
	LEFT OUTER JOIN M_HU c_hu ON bom_a.M_HU_ID = c_hu.M_HU_ID 

	LEFT OUTER JOIN M_HU_Storage c_hus ON c_hu.M_HU_ID = c_hus.M_HU_ID
	LEFT OUTER JOIN M_Product p ON c_hus.M_Product_ID = p.M_Product_ID
	LEFT OUTER JOIN M_HU_Attribute c_hua ON c_hu.M_HU_ID = c_hua.M_HU_ID
		AND c_hua.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = '1000001')) --Herkunft
	LEFT OUTER JOIN M_AttributeValue av ON c_hua.value = av.Value AND c_hua.M_Attribute_ID = av.M_Attribute_ID
;


COMMENT ON VIEW report.halbfabrikat_product_origin IS 'the view tries to find HUs that contain components of an HU that contains a manufactured Product. The component HUs are used to retrieve the component products and the "Herkunft"-Attribute. According to the requirements of task 07909, the first 4 characters of the product and the first 3 characters of the origin ("Herkunft"-attribute) are returned in one string.';

