
-- adding some comments, and returning 0 instead of NULL in cate of no ASI. 
CREATE OR REPLACE VIEW "de.metas.fresh".PP_Product_Bom_And_Component AS
SELECT 
	-- bom (header) stuff
	b.PP_Product_Bom_ID, 
	
	-- the BOM-product, i.e. the "end-result"
	b.M_Product_ID AS b_M_Product_ID, b_p.Value as b_p_Value, b_p.Name as b_p_Name,
	
	--  the "end-result's" ASI; don't return null but 0 if there is no ASI, because we'll want to use '=' with it
	COALESCE(b.M_AttributeSetInstance_ID, 0)::numeric(10,0) AS b_M_AttributeSetInstance_ID,
	
	"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.M_AttributeSetInstance_ID)) AS b_M_AttributeSetGroupNames,
	
	-- bomline stuff, i.e. the components or packing material
	bl_p.M_Product_ID AS bl_M_Product_ID, bl_p.Value as bl_p_Value, bl_p.Name as bl_p_Name,
	bl.M_AttributeSetInstance_ID AS bl_M_AttributeSetInstance_ID,
	bl.QtyBatch, bl.QtyBom,
	bl_p.IsPurchased,
	
	-- factors to calculate the required qty of the package/component product (including UOM conversion!) for a given qty of the BOM product
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN bl.QtyBatch/100
		ELSE bl.QtyBom
	END AS bl_Factor_BOM,
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN COALESCE(conv_b_bl.MultiplyRate,1)
		ELSE 1
	END AS bl_Factor_b_To_bl,
	conv_bl_p.MultiplyRate AS bl_Factor_bl_To_Product

FROM PP_Product_Bom b
	JOIN M_Product b_p ON b_p.M_Product_ID=b.M_Product_ID
	JOIN PP_Product_BomLine bl ON bl.PP_Product_Bom_ID=b.PP_Product_Bom_ID
			JOIN M_Product bl_p ON bl_p.M_Product_ID=bl.M_Product_ID
	
	-- UOM conversion from the bom's UOM (e.g. Stk) to the UOM of the bom-line (e.g. Kg)
	LEFT JOIN C_UOM_Conversion_V conv_b_bl ON conv_b_bl.M_Product_ID=b.M_Product_ID
		AND conv_b_bl.C_UOM_From_ID=b.C_UOM_ID
		AND conv_b_bl.C_UOM_To_ID=bl.C_UOM_ID
		
	-- UOM conversion from the bom-line's UOM (e.g. mm) to the UOM of the bom-line's product (e.g. Rolle)
	JOIN C_UOM_Conversion_V conv_bl_p ON conv_bl_p.M_Product_ID=bl.M_Product_ID
		AND conv_bl_p.C_UOM_From_ID=COALESCE(bl.C_UOM_ID, bl_p.C_UOM_ID) -- cover the case that bomline-uom is empty
		AND conv_bl_p.C_UOM_To_ID=bl_p.C_UOM_ID
WHERE true
	AND b.IsActive='Y' AND bl.IsActive='Y' AND bl_p.IsActive='Y'
	AND bl.ComponentType IN ('CO','PK') -- only components and packing materials

--	AND b_p.Value='P000787'-- "AB Alicesalat 250g";"Alicesalat, endproduct", Stk
--	AND bl_p.Value='P000328' --Frisee, purchased, raw matrerial, kg
;

COMMENT ON VIEW "de.metas.fresh".PP_Product_Bom_And_Component IS 'task 08682: added this view in the attempt to make the view MRP_ProductInfo_Poor_Mans_MRP_V more "tidy". 
But note that the view is also used in the view M_Product_ID_M_AttributeSetInstance_ID_V.'
;


--
-- overhauling and simplifying the function's recursive SQL.
--
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

-----------------------
-- create the table to hold the data in future
DROP SEQUENCE IF EXISTS "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP_seq;
CREATE SEQUENCE "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP_seq
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 2147483647
  START 1000000
  CACHE 1;

GRANT ALL ON TABLE "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP_seq TO adempiere;
GRANT ALL ON TABLE "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP_seq TO zabbix;

DROP TABLE IF EXISTS "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp CASCADE;
CREATE TABLE "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp
(
  x_mrp_productinfo_detail_poor_mans_mrp_id bigint NOT NULL,
  dategeneral date,
  m_product_root_id integer,
  m_attributesetinstance_root_id integer,
  m_product_mrp_id numeric,
  asikey_mrp text,
  fresh_qtymrp numeric,
  CONSTRAINT x_MRP_ProductInfo_Detail_Poor_Mans_MRP_pk PRIMARY KEY (x_mrp_productinfo_detail_poor_mans_mrp_id )
)
WITH (
  OIDS=FALSE
);

-- Index: "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp_tuple
DROP INDEX IF EXISTS "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp_tuple;
CREATE INDEX x_mrp_productinfo_detail_poor_mans_mrp_dategeneral
  ON "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp
  USING btree
  (dategeneral);

-------------------------
-- the function to fill it
--DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateFrom date, IN M_Product_ID numeric, IN M_AttributesetInstance_ID numeric) 
	RETURNS VOID AS
$BODY$

	DELETE FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP 
	WHERE true
		AND DateGeneral::date = $1
		AND M_Product_Root_ID = $2
		AND M_AttributesetInstance_Root_ID = $3
	;
	
	INSERT INTO "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP
	(
		X_MRP_ProductInfo_Detail_Poor_Mans_MRP_ID,
		DateGeneral, 
		M_Product_Root_ID, 
		M_AttributesetInstance_Root_ID,
		M_Product_MRP_ID, 
		ASIKey_MRP, 
		Fresh_QtyMRP
	)
	SELECT
		nextval('"de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp_seq'),
		$1 AS DateGeneral,
		$2 AS M_Product_Root_ID,
		$3 AS M_AttributesetInstance_Root_ID,
		COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID) as M_Product_MRP_ID, 
		GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),'') as ASIKey_MRP,
		CEIL(mv2.QtyOrdered_Sale_OnDate * COALESCE(v_exact.Factor_Recursive, v_fallback.Factor_Recursive)) AS Fresh_QtyMRP

	FROM X_MRP_ProductInfo_Detail_MV mv2
			-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID)
			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2, $3) v_exact
				ON v_exact.IsPurchased='Y'

			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2, null) v_fallback 
				ON v_exact.bl_m_Product_ID IS NULL -- it is important to only join v_fallback, if there was no v_exact to be found. Otherwise we would join one v_fallback for each bomline-product 
					AND GenerateHUStorageASIKey(v_fallback.b_M_AttributeSetInstance_ID,'')=''
					AND v_fallback.IsPurchased='Y'
	WHERE true
			AND COALESCE(v_exact.IsPurchased,v_fallback.IsPurchased)='Y'
			AND mv2.DateGeneral::date = $1 AND mv2.M_Product_ID = $2 AND mv2.ASIKey = GenerateHUStorageASIKey($3,'')
$BODY$
LANGUAGE sql VOLATILE;
