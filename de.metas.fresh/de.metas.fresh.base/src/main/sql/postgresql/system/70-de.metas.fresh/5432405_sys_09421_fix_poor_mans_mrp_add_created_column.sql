
ALTER TABLE "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP ADD COLUMN Created TIMESTAMP;

DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateAt date, IN M_Product_ID numeric, IN M_AttributesetInstance_ID numeric) 
	RETURNS VOID AS
$BODY$

	DELETE FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP 
	WHERE true
		AND DateGeneral::date = $1
		AND M_Product_Root_ID = $2
		-- deleting by ASIKey, not just by asi-ID, because further down in the where and also 
		-- *inside* MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric) we are using the ASI-Key 
		--		AND M_AttributesetInstance_ID = $3
		AND GenerateHUStorageASIKey(M_AttributeSetInstance_Root_ID,'')=GenerateHUStorageASIKey($3,'')
	;
	
	INSERT INTO "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP
	(
		X_MRP_ProductInfo_Detail_Poor_Mans_MRP_ID,
		DateGeneral, 
		M_Product_Root_ID, 
		M_AttributesetInstance_Root_ID,
		M_Product_MRP_ID, 
		ASIKey_MRP, 
		Fresh_QtyMrp,
		Created
	)
	SELECT
		nextval('"de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp_seq'),
		$1 AS DateGeneral,
		$2 AS M_Product_Root_ID,
		$3 AS M_AttributesetInstance_Root_ID,
		COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID) as M_Product_MRP_ID, 
		GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),'') as ASIKey_MRP,
		CEIL(mv2.QtyOrdered_Sale_OnDate * COALESCE(v_exact.Factor_Recursive, v_fallback.Factor_Recursive)) AS Fresh_QtyMRP,
		now()
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
