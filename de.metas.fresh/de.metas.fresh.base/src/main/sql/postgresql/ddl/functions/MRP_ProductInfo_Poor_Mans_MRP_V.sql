CREATE OR REPLACE FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(
		IN M_Product_ID numeric, 
		IN M_AttributesetInstance_ID numeric) 
	RETURNS TABLE (
	  pp_product_bom_id numeric(10,0),
	  b_m_product_id numeric(10,0),
	  b_p_value character varying(40),
	  b_p_name character varying(255),
	  b_m_attributesetinstance_id numeric(10,0),
	  b_m_attributesetgroupnames character varying[],
	  bl_m_product_id numeric(10,0),
	  bl_p_value character varying(40),
	  bl_p_name character varying(255),
	  bl_m_attributesetinstance_id numeric(10,0),
	  qtybatch numeric,
	  qtybom numeric,
	  ispurchased character(1),
	  bl_factor_bom numeric,
	  bl_factor_b_to_bl numeric,
	  bl_factor_bl_to_product numeric,
	  factor_recursive numeric,
	  search_depth integer,
	  path numeric(10,0)[],
	  cycle boolean
	) AS
$BODY$
WITH 
RECURSIVE PP_Product_Bom_Recursive AS (

	-- Begin at the upmost bom
	SELECT 
		*,
		(bl_Factor_BOM * bl_Factor_b_To_bl * bl_Factor_bl_To_Product) AS Factor_Recursive,
		1 as search_depth,
		ARRAY[PP_Product_Bom_ID] as path, 
		false as cycle 
	FROM "de.metas.fresh".PP_Product_Bom_And_Component
	WHERE b_M_Product_ID=$1 AND b_M_AttributeSetInstance_ID=COALESCE($2, b_M_AttributeSetInstance_ID)

	UNION ALL
	-- TBH I don't yet fully understand what I'm doing here :-$, but it works
	SELECT 
		r.PP_Product_Bom_ID, 
		b.b_M_Product_ID, b.b_p_Value, b.b_p_Name,
		b.b_M_AttributeSetInstance_ID,
		"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID)),
		r.bl_M_Product_ID, r.bl_p_Value, r.bl_p_Name,
		r.bl_M_AttributeSetInstance_ID,
		r.QtyBatch, r.QtyBom, r.IsPurchased, r.bl_Factor_BOM, r.bl_Factor_b_To_bl, r.bl_Factor_bl_To_Product,

		(b.bl_Factor_BOM * b.bl_Factor_b_To_bl * b.bl_Factor_bl_To_Product) * Factor_Recursive,
		search_depth+1,
		(b.PP_Product_Bom_ID|| path)::numeric(10,0)[], -- example "{1000002,2002017,2002387}"
		b.PP_Product_Bom_ID = ANY(path)
	FROM 
		"de.metas.fresh".PP_Product_Bom_And_Component b, 
		PP_Product_Bom_Recursive r -- this is the recursive relf-reference
	WHERE true
		-- select the BOM which has a line-product that is the recursive result's BOM ("main") product
		AND b.bl_M_Product_ID=r.b_M_Product_ID 
		AND COALESCE(GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID),'')=COALESCE(GenerateHUStorageASIKey(r.bl_M_AttributeSetInstance_ID),'')
		-- just a precaution to avoid too deep searches (perf)
		AND r.search_depth < 6
		AND NOT cycle
	)
SELECT *
FROM PP_Product_Bom_Recursive
WHERE true 
--	AND b_M_Product_ID=2000070 -- AB Apero Gemüse mit Sauce 350g
--	AND bl_p_M_Product_ID=2000816 -- P001678_Karotten Stäbli Halbfabrikat
--	AND bl_p_M_Product_ID=2001416 -- P000367 Karotten gross gewaschen
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric) IS
'Returns BOM related information about the raw materials of the given M_Product_ID and M_AttributesetInstance_ID. M_AttributesetInstance_ID may also be null in order not to filter by M_AttributesetInstance_ID'
;