DROP FUNCTION IF EXISTS report.intermediate_product_label(IN M_HU_ID numeric);

CREATE FUNCTION report.intermediate_product_label(IN M_HU_ID numeric) RETURNS TABLE
	(
	created date, 
	value Character Varying, 
	adr Character Varying (60),
	attributes text,
	weight numeric,
	name Character Varying (255)
	)
AS 
$$
SELECT
	tu.created::date,
	val.huvalue,
	av_adr.name AS AdR,
	COALESCE ( tua_ori.origin, '') || COALESCE ( av_adr.name, '') AS Attributes,
	(CASE WHEN val.qty IS NOT NULL THEN tua_gew.valuenumber / val.qty ELSE tua_gew.valuenumber END) AS weight,
	p.name
FROM
	M_HU tu
	/** Get Product */
	INNER JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID AND tus.isActive = 'Y'
	INNER JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

	/** Get AdR Attribute */
	LEFT OUTER JOIN M_HU_Attribute tua_adr ON tu.M_HU_ID = tua_adr.M_HU_ID 
		AND tua_adr.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = '1000015' AND isActive = 'Y')) AND tua_adr.isActive = 'Y' --AdR
	LEFT OUTER JOIN M_AttributeValue av_adr ON tua_adr.value = av_adr.Value AND tua_adr.M_Attribute_ID = av_adr.M_Attribute_ID
		AND av_adr.name NOT ILIKE '%keine%leer%' AND av_adr.isActive = 'Y' -- AdR = "Keine/Leer" means "no AdR" and shall not be displayed
	/** Get weight Attibute */
	LEFT OUTER JOIN M_HU_Attribute tua_gew ON tu.M_HU_ID = tua_gew.M_HU_ID
		AND tua_gew.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = 'WeightNet' AND isActive = 'Y')) AND tua_gew.isActive = 'Y' -- Gewicht Netto
	/** Get Product Origin */
	LEFT OUTER JOIN (
		SELECT
			M_HU_ID,
			String_agg( Product_Origin, ', ' ORDER BY Product_Origin ) AS Origin
		FROM
			(
				SELECT DISTINCT
					tu.M_HU_ID,
					TRIM( substring( p.name, 1, 4 ) || ' ' || COALESCE( substring( av.name, 1, 3 ), '' ) ) AS Product_Origin
				FROM
					M_HU tu
					LEFT OUTER JOIN M_HU_Item lui ON lui.M_HU_Item_ID = tu.M_HU_Item_Parent_ID AND lui.isActive = 'Y'
					LEFT OUTER JOIN M_HU lu ON lu.M_HU_ID = lui.M_HU_ID 
					LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID=lu.M_HU_PI_Version_ID AND piv.isActive = 'Y'
					LEFT OUTER JOIN M_HU thu ON thu.M_HU_ID = COALESCE (lu.M_HU_ID, tu.M_HU_ID)
					/** Get component HUs and their "Herkunft" attribute */
					INNER JOIN M_HU_Assignment hf_a ON hf_a.M_HU_ID = thu.M_HU_ID
						AND hf_a.AD_Table_ID = ((SELECT get_table_id( 'PP_Order' ))) AND hf_a.isActive = 'Y'
					INNER JOIN PP_Order mo ON hf_a.Record_ID = mo.PP_Order_ID AND mo.isActive = 'Y'
					LEFT OUTER JOIN PP_Order_BOM mob ON mo.PP_Order_ID = mob.PP_Order_ID AND mob.isActive = 'Y'
					LEFT OUTER JOIN PP_Order_BOMLine mobl ON mob.PP_Order_BOM_ID = mobl.PP_Order_BOM_ID
						AND componentType IN ('CO', 'VA') AND mobl.isActive = 'Y'
					-- This join does not work currently, because the HU Assignments are not created but there is a workaround
					/*LEFT OUTER JOIN M_HU_Assignment bom_a ON mobl.PP_Order_BOMLine_ID = bom_a.Record_ID
						AND bom_a.AD_Table_ID = ((SELECT get_table_id( 'PP_Order_BOMLine' )))*/
					-- Workaround start
					LEFT OUTER JOIN M_HU_Trx_Line hutl ON mobl.PP_Order_BOMLine_ID = hutl.Record_ID
						AND hutl.AD_Table_ID = ((SELECT get_table_id( 'PP_Order_BOMLine' )))  AND hutl.isActive = 'Y'
					LEFT OUTER JOIN M_HU_Trx_Line bom_a ON hutl.Parent_HU_Trx_line_ID = bom_a.M_HU_Trx_line_ID AND bom_a.isActive = 'Y'
					-- Workaround end
					LEFT OUTER JOIN M_HU c_hu ON bom_a.M_HU_ID = c_hu.M_HU_ID
					LEFT OUTER JOIN M_HU_Storage c_hus ON c_hu.M_HU_ID = c_hus.M_HU_ID AND c_hus.isActive = 'Y'
					LEFT OUTER JOIN M_Product p ON c_hus.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
					LEFT OUTER JOIN M_HU_Attribute c_hua ON c_hu.M_HU_ID = c_hua.M_HU_ID
						AND c_hua.M_Attribute_ID = ((SELECT M_Attribute_ID FROM M_Attribute WHERE value = '1000001' AND isActive = 'Y'))  AND c_hua.isActive = 'Y' --Herkunft
					LEFT OUTER JOIN M_AttributeValue av ON c_hua.value = av.Value AND c_hua.M_Attribute_ID = av.M_Attribute_ID AND av.isActive = 'Y'
			) a
		GROUP BY
			M_HU_ID
	) tua_ori ON tu.M_HU_ID = tua_ori.M_HU_ID
	--get vallues for aggregated HUs if any
	left outer join "de.metas.handlingunits".get_TU_Values_From_Aggregation(tu.M_HU_ID) val on true
WHERE
	tu.M_HU_ID = $1

;

$$
  LANGUAGE sql STABLE;