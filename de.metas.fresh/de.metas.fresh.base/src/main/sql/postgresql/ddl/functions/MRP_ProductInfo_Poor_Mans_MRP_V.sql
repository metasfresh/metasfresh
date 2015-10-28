
CREATE OR REPLACE FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(
		IN M_Product_ID numeric, 
		IN M_AttributesetInstance_ID numeric) 
	RETURNS TABLE (
	  pp_product_bom_id numeric(10,0),
	  b_m_product_id numeric(10,0), -- the end-product we look for
	  b_p_value character varying(40),
	  b_p_name character varying(255),
	  b_m_attributesetinstance_id numeric(10,0),
	  b_m_attributesetgroupnames character varying[],
	  bl_m_product_id numeric(10,0), -- the component or packaging
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
		b.*,
		(bl_Factor_BOM * bl_Factor_b_To_bl * bl_Factor_bl_To_Product) AS Factor_Recursive,
		1 as search_depth,
		ARRAY[PP_Product_Bom_ID] as path, 
		false as cycle 
	FROM "de.metas.fresh".PP_Product_Bom_And_Component b
	WHERE b_M_Product_ID=$1 AND GenerateHUStorageASIKey(b_M_AttributeSetInstance_ID,'')=GenerateHUStorageASIKey($2, '')
UNION
	SELECT
		b.*,
		(b.bl_Factor_BOM * b.bl_Factor_b_To_bl * b.bl_Factor_bl_To_Product) * Factor_Recursive,
		search_depth+1,
		(b.PP_Product_Bom_ID|| path)::numeric(10,0)[], -- example "{1000002,2002017,2002387}"
		b.PP_Product_Bom_ID = ANY(path)
	FROM "de.metas.fresh".PP_Product_Bom_And_Component b, PP_Product_Bom_Recursive r
	WHERE true 
		AND b.b_M_Product_ID=r.bl_M_Product_ID
		AND GenerateHUStorageASIKey(b.b_M_AttributeSetInstance_ID,'')=GenerateHUStorageASIKey(r.bl_M_AttributeSetInstance_ID,'')
		AND NOT cycle
)
SELECT * FROM PP_Product_Bom_Recursive;

$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric) IS
'Returns BOM related information about the raw materials of the given M_Product_ID and M_AttributesetInstance_ID. M_AttributesetInstance_ID may also be null in order not to filter by M_AttributesetInstance_ID'
;