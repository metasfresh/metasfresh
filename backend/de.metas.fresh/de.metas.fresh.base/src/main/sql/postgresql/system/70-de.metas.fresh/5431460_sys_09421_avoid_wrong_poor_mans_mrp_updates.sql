
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateFrom date, IN M_Product_ID numeric, IN M_AttributesetInstance_ID numeric) 
	RETURNS VOID AS
$BODY$

	DELETE FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP 
	WHERE true
		AND DateGeneral::date = $1
		AND M_Product_Root_ID = $2
		-- deleting by ASIKey, not just by asi-ID, because further down in the where and also 
		-- *inside* MRP_ProductInfo_Poor_Mans_MRP_V(numeric, numeric) we are using the ASI-Key 
		--		AND M_AttributesetInstance_ID = $3
		AND GenerateHUStorageASIKey(M_AttributesetInstance_Root_ID)=GenerateHUStorageASIKey($3,'')
	;
	
	INSERT INTO "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP
	(
		X_MRP_ProductInfo_Detail_Poor_Mans_MRP_ID,
		DateGeneral, 
		M_Product_Root_ID, 
		M_AttributesetInstance_Root_ID,
		M_Product_MRP_ID, 
		ASIKey_MRP, 
		Fresh_QtyMrp
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


DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric);
DROP FUNCTION IF EXISTS "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(IN DateAt date, 
	IN M_Product_ID numeric, 
	IN M_AttributesetInstance_ID numeric)
  RETURNS void AS
$BODY$

    -- Delete existing records from X_MRP_ProductInfo_Detail_MV. 
    -- They will be recreated if there is anything to be recreated
    -- we don't do any ASI related filtering, because there might be an inout line with an ASI that is totally different from the order line,
    -- but still we need to change both the the X_MRP_ProductInfo_Detail_MV records for the inout and the order line
    DELETE FROM X_MRP_ProductInfo_Detail_MV 
    WHERE DateGeneral::Date = $1 AND M_Product_ID = $2 
    ;

	-- Insert new rows.
    INSERT INTO X_MRP_ProductInfo_Detail_MV (
    	m_product_id,
		dategeneral,
		asikey,
		qtyreserved_ondate,
		qtyordered_ondate,
		qtyordered_sale_ondate,
		qtymaterialentnahme,
		fresh_qtyonhand_ondate,
		fresh_qtypromised,
		isfallback,
		groupnames)
    SELECT 
    	m_product_id,
		dategeneral,
		asikey,
		qtyreserved_ondate,
		qtyordered_ondate,
		qtyordered_sale_ondate,
		qtymaterialentnahme,
		fresh_qtyonhand_ondate,
		fresh_qtypromised,
		isfallback,
		groupnames
    FROM X_MRP_ProductInfo_Detail_V($1, $2);

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;

COMMENT ON FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric) IS 'tasks 08681 and 08682: refreshes the table X_MRP_ProductInfo_Detail_MV.
1. deleting existing records
2. calling X_MRP_ProductInfo_Detail_MV so add new records
3. updating fresh_qtymrp using the view "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V
';
